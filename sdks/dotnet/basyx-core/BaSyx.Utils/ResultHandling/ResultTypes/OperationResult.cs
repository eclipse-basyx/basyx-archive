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
using System;
using System.Collections.Generic;
using System.Text;

namespace BaSyx.Utils.ResultHandling.ResultTypes
{
    public class OperationResult : Result
    {
        public OperationResult(bool success) : base(success)
        { }
        public OperationResult(Exception e) : base(e)
        { }
        public OperationResult(bool success, IMessage message) : base(success, message)
        { }
        public OperationResult(bool success, List<IMessage> messages) : base(success, messages)
        { }
    }
}
