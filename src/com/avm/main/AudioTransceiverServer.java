/**
 * 
 */
package com.avm.main;

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
		new ConnectionsManager().startListening();
	}

}