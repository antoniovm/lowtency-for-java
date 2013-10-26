/**
 * 
 */
package com.avm.net;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.avm.stream.OutputStreamManager;

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
			serverTCP = new ServerSocket(DEFAULT_SERVER_PORT);
			serverUDP = new DatagramSocket(DEFAULT_SERVER_PORT);
		} catch (IOException e) {
			e.printStackTrace();
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
			try {
				Socket s = serverTCP.accept();
				OutputStreamManager osm = new OutputStreamManager(serverUDP,s.getRemoteSocketAddress(),null);
				incomingConnections.add(osm);
				new Thread(osm).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (true) {
			handleNewConnection();
		}
		
	}
	
}
