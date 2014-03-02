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
	
	public static final int DEFAULT_SAMPLE_BUFFER_SIZE = 8192;
	public static final int DEFAULT_CHANNELS = 1;
	
	// Sample size in bytes
	public static final int DEFAULT_SAMPLE_SIZE = 2;
	public static final int DEFAULT_SAMPLE_RATE = 44100;
	
	private AudioWaveManager audioWaveManager;
	private DatagramSocket serverUDP;
	private DatagramPacket packetUDP;
	
	/**
	 * 
	 */
	public OutputStreamManager(DatagramSocket serverUDP,SocketAddress address) {
		this.audioWaveManager = new AudioWaveManager(DEFAULT_SAMPLE_BUFFER_SIZE, DEFAULT_SAMPLE_SIZE, DEFAULT_CHANNELS,DEFAULT_SAMPLE_RATE);
		
		audioWaveManager.fillBuffer();
		
		this.serverUDP = serverUDP;
		try {
			this.packetUDP = new DatagramPacket(audioWaveManager.getBuffer(),audioWaveManager.getBuffer().length,address);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		// Testing measurements
		measureUDPTime();
		
		// Infinite loop
		handleOutputAudioStream();
			
		
	}
	
	/**
	 * 
	 */
	private void handleOutputAudioStream() {
	
		while (true) {
			//TODO get stream
			//TODO compress stream
			//TODO send commpressed stream
			
			// ------------------Test audio input data--------------------
			audioWaveManager.fillBuffer();
			packetUDP.setData(audioWaveManager.getBuffer());
			// ------------------Test audio input data--------------------
			
			
			try {
				serverUDP.send(packetUDP);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// ------------------Real time audio sync--------------------
			try {
				Thread.sleep((long)(DEFAULT_SAMPLE_BUFFER_SIZE/(double)(DEFAULT_SAMPLE_RATE)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// ------------------Real time audio sync--------------------
		
		}

	}
	
	/**
	 * 
	 */
	private void measureUDPTime() {
		
		
		//Starts a new thread to measure the timing of upd packets arrivals 
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				
				
			}
		}).start();
		
	}

}
