/**
 * 
 */
package com.avm.net;

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
					buffer.append("\n" + networkInterface.getName() + " " + networkInterface.getInetAddresses());
				}
				
			} catch (SocketException e) {
				e.printStackTrace();
			}
		       
		}
		
		/*
		 * Modified few bits and this one is working as desired for getting IPv4 addresses. !inetAddress.isLoopbackAddress() removes all the loopback address. !inetAddress.isLinkLocalAddress() and inetAddress.isSiteLocalAddress()) removes all IPv6 addresses. I hope this will help someone in here.

    	StringBuilder IFCONFIG=new StringBuilder();
	    try {
	        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	            NetworkInterface intf = en.nextElement();
	            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	                InetAddress inetAddress = enumIpAddr.nextElement();
	                if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
                	IFCONFIG.append(inetAddress.getHostAddress().toString()+"\n");
	                }

	            }
	        }
	    } catch (SocketException ex) {
	        Log.e("LOG_TAG", ex.toString());
	    }
	    servers.add(IFCONFIG.toString());
		 */
		
		return buffer.toString();
	}
	
}
