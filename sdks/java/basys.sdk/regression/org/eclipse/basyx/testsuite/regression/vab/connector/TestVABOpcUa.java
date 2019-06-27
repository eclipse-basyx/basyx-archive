package org.eclipse.basyx.testsuite.regression.vab.connector;

import org.eclipse.basyx.aas.backend.connector.opcua.OpcUaConnectorProvider;
import org.eclipse.basyx.vab.backend.gateway.ConnectorProviderMapper;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UByte;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.ULong;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UShort;
import org.junit.Assert;

/**
 * Test VAB using OpcUa protocol. This is an integration test
 * 
 * @author kdorofeev
 *
 */
public class TestVABOpcUa {
    protected ConnectorProviderMapper clientMapper = new ConnectorProviderMapper();

//    @Test
    public void testOpcUaMethodCall() {
        clientMapper.addConnectorProvider("opc.tcp", new OpcUaConnectorProvider());
        try {
            Object methodCallRes = clientMapper.getConnector("opc.tcp://opcua.demo-this.com:51210/UA/SampleServer")
                    .invokeOperation("0:Objects/2:Data/2:Static/2:MethodTest/2:ScalarMethod1",
                            new Object[] { Boolean.valueOf("true"), Byte.valueOf("1"), UByte.valueOf("2"),
                                    Short.valueOf("3"), UShort.valueOf("5"), 8, UInteger.valueOf(13),
                                    Long.valueOf("21"), ULong.valueOf("34"), Float.valueOf("55.0"),
                                    Double.valueOf("89.0") });
            Assert.assertEquals("true 1 2 3 5 8 13 21 34 55.0 89.0 ", methodCallRes);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
//    @Test
    public void testOpcUaReadAndWrite() {
        clientMapper.addConnectorProvider("opc.tcp", new OpcUaConnectorProvider());
        try {
            clientMapper.getConnector("opc.tcp://opcua.demo-this.com:51210/UA/SampleServer")
                    .setModelPropertyValue("0:Objects/2:Data/2:Static/2:AnalogScalar/2:Int32Value", 42);

            Object ret = clientMapper.getConnector("opc.tcp://opcua.demo-this.com:51210/UA/SampleServer")
                    .getModelPropertyValue("0:Objects/2:Data/2:Static/2:AnalogScalar/2:Int32Value");
            Assert.assertEquals("42", ret);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
