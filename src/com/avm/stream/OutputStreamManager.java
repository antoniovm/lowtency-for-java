/**
 * 
 */
package com.avm.stream;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;

import com.avm.audio.libtest.AudioWaveManager;

/**
 * @author Antonio Vicente Martin
 *
 */
public class OutputStreamManager implements Runnable{
	
	public static final int DEFAULT_BUFFER_SIZE = 512;
	public static final int DEFAULT_SAMPLE_RATE = 44100;
	
	private AudioWaveManager audioWaveManager;
	private DatagramSocket serverUDP;
	private DatagramPacket packetUDP;
	
	/**
	 * 
	 */
	public OutputStreamManager(DatagramSocket serverUDP,SocketAddress address) {
		this.audioWaveManager = new AudioWaveManager(DEFAULT_BUFFER_SIZE, DEFAULT_SAMPLE_RATE);
		
		audioWaveManager.fillBuffer();
		
		this.serverUDP = serverUDP;
		try {
			this.packetUDP = new DatagramPacket(audioWaveManager.getBuffer(),audioWaveManager.getBuffer().length,address);
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
		while (true) {
			//TODO get stream
			//TODO compress stream
			//TODO send commpressed stream
			audioWaveManager.fillBuffer();
			packetUDP.setData(audioWaveManager.getBuffer());
			try {
				serverUDP.send(packetUDP);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Sent UDP ");
		
		}
			
		
	}

}
