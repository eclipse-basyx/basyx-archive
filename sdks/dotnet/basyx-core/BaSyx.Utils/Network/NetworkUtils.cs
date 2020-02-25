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
using System.Collections.Generic;
using System.Linq;
using System.Net;

namespace BaSyx.Utils.Network
{
    public static class NetworkUtils
    {
        /// <summary>
        /// This method returns the closest source IP address relative to the the target IP address. 
        /// The probality of being able to call the target IP hence increases.
        /// </summary>
        /// <param name="target">Target IP address to call</param>
        /// <param name="sources">Source IP address from where to cal the target</param>
        /// <returns>The closest source IP address to the the target IP address or the Loopback Address</returns>
        public static IPAddress GetClosestIPAddress(IPAddress target, List<IPAddress> sources)
        {
            Dictionary<int, IPAddress> scoredSourceIPAddresses = new Dictionary<int, IPAddress>();
            byte[] targetBytes = target.GetAddressBytes();
            foreach (var source in sources)
            {
                byte[] sourceBytes = source.GetAddressBytes();
                int score = CompareIPByteArray(targetBytes, sourceBytes);

                if(!scoredSourceIPAddresses.ContainsKey(score) && score != 0)
                    scoredSourceIPAddresses.Add(score, source);
            }

            if(scoredSourceIPAddresses.Count > 0)
                return scoredSourceIPAddresses[scoredSourceIPAddresses.Keys.Max()];

            return IPAddress.Loopback;
        }

        private static int CompareIPByteArray(byte[] target, byte[] source)
        {
            if (target.Length != source.Length)
                return 0;

            int score = 0;
            for (int i = 0; i < source.Length; i++)
            {
                if (target[i] == source[i])
                    score++;
                else
                    return score;
            }
            return score;
        }

        public static List<IPAddress> GetAllNetworkIPAddresses()
        {
            IPHostEntry host = Dns.GetHostEntry(Dns.GetHostName());
            List<IPAddress> ipAddresses = new List<IPAddress>();
            foreach (var ip in host.AddressList)
            {
                if (ip.AddressFamily == System.Net.Sockets.AddressFamily.InterNetwork)
                    ipAddresses.Add(ip);
            }
            return ipAddresses;
        }
    }
}
