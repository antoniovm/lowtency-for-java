/**
 * 
 */
package com.avm.stream;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;

/**
 * @author Antonio Vicente Martin
 *
 */
public class OutputStreamManager implements Runnable{
	
	private byte [] data = {'h','e','l','l','o'};
	private DatagramSocket serverUDP;
	private DatagramPacket packetUDP;
	
	/**
	 * 
	 */
	public OutputStreamManager(DatagramSocket serverUDP,SocketAddress address,byte [] buffer) {
		this.serverUDP = serverUDP;
		try {
			this.packetUDP = new DatagramPacket(data,data.length,address);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		//this.data = buffer;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		//while (true) {
		
			try {
				serverUDP.send(packetUDP);
			} catch (IOException e) {
				e.printStackTrace();
			}
		//}
		
	}

}
