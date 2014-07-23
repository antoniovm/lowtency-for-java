/**
 * 
 */
package com.antoniovm.lowtency.stream;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;

import com.antoniovm.lowtency.audio.libtest.AudioWaveManager;
import com.antoniovm.lowtency.util.ByteConverter;

/**
 * @author Antonio Vicente Martin
 * 
 */
public class OutputStreamManager implements Runnable {

	public static final int DEFAULT_SAMPLE_BUFFER_SIZE = 8192;
	public static final int DEFAULT_CHANNELS = 1;

	// Sample size in bytes
	public static final int DEFAULT_SAMPLE_SIZE = 2;
	public static final int DEFAULT_SAMPLE_RATE = 44100;

	// Header size in bytes
	public static final int DEFAULT_AUDIO_HEADER_SIZE = 4;

	private AudioWaveManager audioWaveManager;
	private DatagramSocket serverUDP;
	private DatagramPacket packetUDP;

	/**
	 * Builds a new {@link OutputStreamManager}
	 * 
	 * @param serverUDP
	 * @param address
	 */
	public OutputStreamManager(DatagramSocket serverUDP, SocketAddress address) {
		this.audioWaveManager = new AudioWaveManager(DEFAULT_SAMPLE_BUFFER_SIZE, DEFAULT_SAMPLE_SIZE, DEFAULT_CHANNELS,
				DEFAULT_SAMPLE_RATE, DEFAULT_AUDIO_HEADER_SIZE);

		audioWaveManager.fillBuffer(4);

		this.serverUDP = serverUDP;
		try {
			this.packetUDP = new DatagramPacket(audioWaveManager.getBuffer(), audioWaveManager.getBuffer().length,
					address);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		long[] timesArray = new long[DEFAULT_SAMPLE_BUFFER_SIZE];

		// Testing measurements
		measureUDPTime(timesArray);

		// Infinite loop
		handleOutputAudioStream(timesArray);

	}

	/**
	 * This is the main method where all streaming goes out
	 * 
	 * @param timesArray
	 *            An array of time per each UDP datagram for testing purposes
	 */
	private void handleOutputAudioStream(long[] timesArray) {

		long udpId = 0;

		packetUDP.setData(audioWaveManager.getBuffer());

		while (true) {
			// TODO get stream
			// TODO compress stream
			// TODO send commpressed stream

			// ------------------Test audio input data--------------------
			audioWaveManager.fillBuffer(DEFAULT_AUDIO_HEADER_SIZE);
			ByteConverter.toBytesArray(udpId, audioWaveManager.getBuffer(), 0, 4, true);
			timesArray[(int) (udpId % DEFAULT_SAMPLE_BUFFER_SIZE)] = System.currentTimeMillis();
			udpId++;
			// System.out.println("UDP " + udpId + ": sent");
			// ------------------Test audio input data--------------------

			try {
				serverUDP.send(packetUDP);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// ------------------Real time audio sync--------------------
			try {
				Thread.sleep((long) ((DEFAULT_SAMPLE_BUFFER_SIZE / (double) (DEFAULT_SAMPLE_RATE)) * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// ------------------Real time audio sync--------------------

		}

	}

	/**
	 * This is a testing method to measure time between UDP datagram arrivals
	 * 
	 * @param timesArray
	 *            The time array where output time per datagram is store
	 */
	private void measureUDPTime(final long[] timesArray) {

		// Starts a new thread to measure the timing of upd packets arrivals
		new Thread(new Runnable() {

			@Override
			public void run() {
				byte[] bytes = new byte[4];
				DatagramPacket datagramPacket = new DatagramPacket(bytes, 4);

				while (true) {
					try {
						serverUDP.receive(datagramPacket);
					} catch (IOException e) {
						e.printStackTrace();
					}

					int updId = ByteConverter.toIntValue(datagramPacket.getData(), 0, true);
					long time = System.currentTimeMillis() - timesArray[updId % DEFAULT_SAMPLE_BUFFER_SIZE];
					System.out.println("UDP " + updId + ": " + time + "ms");
				}

			}
		}).start();

	}

}
