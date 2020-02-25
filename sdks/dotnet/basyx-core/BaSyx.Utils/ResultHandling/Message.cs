/*******************************************************************************
* Copyright (c) 2020 Robert Bosch GmbH
* Author: Constantin Ziesche (constantin.ziesche@bosch.com)
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License 2.0 which is available at
* http://www.eclipse.org/legal/epl-2.0
*
* SPDX-License-Identifier: EPL-2.0
*******************************************************************************/
using System.Globalization;
using System.Net;
using System.Threading;

namespace BaSyx.Utils.ResultHandling
{
    public class Message : IMessage
    {
        public MessageType MessageType { get; set; }
        public string Text { get; set; }
        public string Code { get; set; }

        public Message(MessageType messageType, string text) : this(messageType, text, null)
        { }
        public Message(MessageType messageType, string text, string code)
        {
            MessageType = messageType;
            Text = text;
            Code = code;
        }


        public override string ToString()
        {
            if(!string.IsNullOrEmpty(Code))
                return string.Format(CultureInfo.CurrentCulture, "{0} | {1} - {2}", MessageType, Code, Text);
            else
                return string.Format(CultureInfo.CurrentCulture, "{0} | {1}", MessageType, Text);
        }
    }

    public class HttpMessage : Message
    {
        public HttpStatusCode HttpStatusCode { get; set; }

        public HttpMessage(MessageType messageType, HttpStatusCode httpStatusCode) : base(messageType, httpStatusCode.ToString(), ((int)httpStatusCode).ToString())
        {
            HttpStatusCode = httpStatusCode;
        }
    }

    public class NotFoundMessage : Message
    {
        public NotFoundMessage() : base(MessageType.Information, "NotFound", "404")
        { }

        public NotFoundMessage(string what) : base(MessageType.Information, what + " not found", "404")
        { }
    }

    public class ConflictMessage : Message
    {
        public ConflictMessage() : base(MessageType.Information, "Conflict", "409")
        { }

        public ConflictMessage(string what) : base(MessageType.Information, what + " already exists", "409")
        { }
    }

    public class EmptyMessage : Message
    {
        public EmptyMessage() : base(MessageType.Information, "Empty")
        { }
    }
}
