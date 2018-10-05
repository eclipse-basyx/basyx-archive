using System;
using System.Text;
using oneM2MClient.Utils.ResultHandling;
using uPLibrary.Networking.M2Mqtt;
using uPLibrary.Networking.M2Mqtt.Messages;
using System.Threading;
using NLog;

namespace oneM2MClient.Client.Bindings
{
    public class MqttBinding : IClient
    {
        public const string requestTopic = "/oneM2M/req";
        public const string responseTopic = "/oneM2M/resp";

        private bool subscribedFlag = false;

        public ClientFactory.Protocol Protocol => ClientFactory.Protocol.Mqtt;

        public string PayLoad { get; set; }

        private ManualResetEvent manualResetEvent;
        private ManualResetEvent connectionClosed;
        private MqttResponse response;
        private string tempRqi;

        private MqttClient mqttClient;

        private string clientId;

        private MqttBindingConfig mqttConfig;


        private static Logger logger = LogManager.GetCurrentClassLogger();

        public MqttBinding(string clientId, BindingConfig mqttConfig)
        {
            this.clientId = clientId;
            if (mqttConfig != null)
                this.mqttConfig = mqttConfig as MqttBindingConfig;
            else
                this.mqttConfig = new MqttBindingConfig("MqttProviderDefault");

            mqttClient = new MqttClient(this.mqttConfig.BrokerIPAddress, this.mqttConfig.BrokerPort, this.mqttConfig.SecureConnection, null, null, MqttSslProtocols.None, null, null);
        }

        public bool IsAlive
        {
            get
            {
                if (mqttClient != null)
                    return mqttClient.IsConnected;
                else
                    return false;
            }
        }

       
        public Result<Response> Send(Request request)
        {
            if (request == null && request.RequestPrimitive != null)
                return null;

            tempRqi = request.GetHashCode().ToString();
            request.RequestIdentifier(tempRqi);

            MqttRequest requestHelper = new MqttRequest(request);
            
            string sMessage = requestHelper.MessageBody.ToString();
            byte[] bMessage = Encoding.UTF8.GetBytes(sMessage);

            mqttClient.MqttMsgPublished -= MqttClient_MqttMsgPublished;
            mqttClient.MqttMsgPublished += MqttClient_MqttMsgPublished;

            ushort msgIdPub = mqttClient.Publish(requestHelper.RequestTopic, bMessage, MqttMsgBase.QOS_LEVEL_EXACTLY_ONCE, false);
            if (!subscribedFlag)
            {
                mqttClient.MqttMsgSubscribed += MqttClient_MqttMsgSubscribed;
                string subTopic = string.Join("/", responseTopic, requestHelper.RequestPrimitive.Fr, requestHelper.CSEName, requestHelper.ContentMIME);
                ushort msgIdSub = mqttClient.Subscribe(new string[] { subTopic }, new byte[] { MqttMsgBase.QOS_LEVEL_EXACTLY_ONCE });
                mqttClient.MqttMsgPublishReceived += MqttClient_MqttMsgPublishReceived;
                subscribedFlag = true;
            }
            manualResetEvent = new ManualResetEvent(false);
            bool success = manualResetEvent.WaitOne(2000);
            if(success && response != null && response.RequestIdentifier == tempRqi)
            {
                var clonedResponse = response.Clone();
                response = null;
                bool error = !string.IsNullOrEmpty(clonedResponse.ErrorMessage) ? true : false;

                if(error)
                    return new Result<Response>(false, clonedResponse, new Message(MessageType.Error, clonedResponse.ErrorMessage));
                else
                    return new Result<Response>(true, clonedResponse);
            }
            else if (response != null && response.RequestIdentifier != tempRqi)
            {
                var clonedResponse = response.Clone();
                response = null;
                return new Result<Response>(false, clonedResponse, new Message(MessageType.Error,  "Response-Id: '" + clonedResponse.RequestIdentifier+"' does not match Request-Id: '"+ tempRqi + "'"));
            }
            else
            {
                return new Result<Response>(false, new Message(MessageType.Error, "Error getting response from host, response is null"));
            }
        }

        private void MqttClient_MqttMsgPublishReceived(object sender, MqttMsgPublishEventArgs e)
        {
            Console.Out.WriteLine("Received = " + Encoding.UTF8.GetString(e.Message) + " on topic " + e.Topic);
            response = new MqttResponse(e);
            if(response.RequestIdentifier == tempRqi)
                manualResetEvent.Set();
        }

        private void MqttClient_MqttMsgSubscribed(object sender, MqttMsgSubscribedEventArgs e)
        {
            Console.Out.WriteLine("Subscribed for id = " + e.MessageId);
        }

        private void MqttClient_MqttMsgPublished(object sender, MqttMsgPublishedEventArgs e)
        {
            Console.Out.WriteLine("MessageId = " + e.MessageId + " Published = " + e.IsPublished);
        }

        public void Start()
        {
            clientId = clientId ?? Guid.NewGuid().ToString();

            try
            {
                byte success = mqttClient.Connect(clientId);
                if (success != 0)
                    throw new Exception("Could not connect to MQTT-Broker");
            }
            catch (Exception e)
            {
                logger.Error(e, "Could not connect MQTT-Broker");
            }
        }

        public void Stop()
        {
            if (mqttClient != null)
            {
                if (mqttClient.IsConnected)
                {
                    connectionClosed = new ManualResetEvent(false);
                    mqttClient.ConnectionClosed += MqttClient_ConnectionClosed;

                    mqttClient.Disconnect();

                    bool success = connectionClosed.WaitOne(1000);

                    if (!success)
                        logger.Error("Could not close MQTT-Client");

                }
                mqttClient = null;
            }
        }

        private void MqttClient_ConnectionClosed(object sender, EventArgs e)
        {
            connectionClosed.Set();
        }
    }
}
