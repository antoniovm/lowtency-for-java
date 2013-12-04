/**
 * 
 */
package com.avm.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author Antonio Vicente Martin
 *
 */
public class NetworkManager {

	
	public static String printListString() {
		StringBuffer buffer = new StringBuffer();
		Enumeration<NetworkInterface> networkInterfaces = null;
		try {
			networkInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		for (NetworkInterface networkInterface = networkInterfaces.nextElement(); networkInterfaces.hasMoreElements();networkInterface = networkInterfaces.nextElement()){
			try {
				if (!networkInterface.isLoopback() && networkInterface.isUp()) {
					buffer.append("\n" + networkInterface.getName()+": ");
					
					for (Enumeration<InetAddress> enumIpAddr = networkInterface.getInetAddresses(); enumIpAddr.hasMoreElements();) {
		                InetAddress inetAddress = enumIpAddr.nextElement();
		                if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
		                	buffer.append(inetAddress.getHostAddress()+" ");
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
