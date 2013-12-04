/**
 * 
 */
package com.avm;

import com.avm.net.ConnectionsManager;

/**
 * @author Antonio Vicente Martin
 *
 */
public class AudioTransceiverServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Server started");
		new ConnectionsManager().startListening();
	}

}