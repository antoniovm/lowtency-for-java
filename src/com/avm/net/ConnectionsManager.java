/**
 * 
 */
package com.avm.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.avm.stream.OutputStreamManager;

/**
 * @author Antonio Vicente Martin
 *
 */
public class ConnectionsManager implements Runnable{

	private ServerSocket serverSocket;
	private ArrayList<OutputStreamManager> incomingConnections;
	
	/**
	 * 
	 */
	public ConnectionsManager() {
		try {
			serverSocket = new ServerSocket(3333);
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
				Socket s = serverSocket.accept();
				OutputStreamManager osm = new OutputStreamManager(s.getOutputStream(), null);
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
