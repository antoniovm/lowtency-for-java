/**
 * 
 */
package com.avm.net;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

import com.avm.stream.OutputStreamManager;
import com.avm.util.ByteConverter;

/**
 * @author Antonio Vicente Martin
 *
 */
public class ConnectionsManager implements Runnable{
	
	private static final int DEFAULT_SERVER_PORT = 3333;

	private ServerSocket serverTCP;
	private DatagramSocket serverUDP;
	private ArrayList<OutputStreamManager> incomingConnections;
	
	/**
	 * 
	 */
	public ConnectionsManager() {
		incomingConnections = new ArrayList<>();
		
		try {
			serverTCP = new ServerSocket();
			serverTCP.setReuseAddress(true);
			serverTCP.bind(new InetSocketAddress(DEFAULT_SERVER_PORT));
			serverUDP = new DatagramSocket(DEFAULT_SERVER_PORT);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 *  Creates a new thread to handle new connections
	 */
	public void startListening() {
		new Thread(this).start();
	}
	
	/**
	 *  Waits a new connection, and creates an {@link OutputStreamManager}
	 *  to send stream data to client
	 */
	private void handleNewConnection(){
		byte [] remoteUDPPortData = new byte [4];
		int bytesRead = 0;
		int udpPort = 0;
			try {
				System.out.println("Waiting for client...");
				Socket s = serverTCP.accept();
				System.out.println("New connection from: " + s.getRemoteSocketAddress());
				
				//Get udp port
				bytesRead = s.getInputStream().read(remoteUDPPortData);
				
				//udpPort = ByteBuffer.wrap(remoteUDPPortData).getInt();
				udpPort = ByteConverter.toIntValue(remoteUDPPortData, 0, true);
				SocketAddress socketAddress = new InetSocketAddress(s.getInetAddress().getHostAddress(), udpPort);
				
				OutputStreamManager osm = new OutputStreamManager(serverUDP,socketAddress);
				incomingConnections.add(osm);
				
				//Starts a new thread to handle the streaming
				new Thread(osm).start();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		System.out.println("Server netifaces: " + NetworkManager.printListString());
		while (true) {
			handleNewConnection();
		}
		
	}
	
}
