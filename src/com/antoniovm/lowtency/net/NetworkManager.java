/**
 * 
 */
package com.antoniovm.lowtency.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author Antonio Vicente Martin
 * 
 */
public class NetworkManager {

	/**
	 * Returns a list of the network interfaces with their IPs if available
	 * 
	 * @return A string with the interfaces list
	 */
	public static String printListString() {
		StringBuffer buffer = new StringBuffer();
		Enumeration<NetworkInterface> netIfaces = null;
		try {
			netIfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		NetworkInterface netIface = netIfaces.nextElement();
		for (; netIfaces.hasMoreElements(); netIface = netIfaces.nextElement()) {
			try {
				if (!netIface.isLoopback() && netIface.isUp()) {
					buffer.append("\n" + netIface.getName() + ": ");

					Enumeration<InetAddress> enumIpAddr = netIface.getInetAddresses();
					for (; enumIpAddr.hasMoreElements();) {
						InetAddress inetAddress = enumIpAddr.nextElement();

						if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()
								&& inetAddress.isSiteLocalAddress()) {
							buffer.append(inetAddress.getHostAddress() + " ");
						}

					}
				}

			} catch (SocketException e) {
				e.printStackTrace();
			}

		}

		return buffer.toString();
	}

}
