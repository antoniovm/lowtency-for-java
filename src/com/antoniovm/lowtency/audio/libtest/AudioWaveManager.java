package com.antoniovm.lowtency.audio.libtest;

import java.util.ArrayList;

import com.antoniovm.lowtency.util.ByteConverter;

/**
 * @author Antonio Vicente Martin
 * 
 */
public class AudioWaveManager {

	private int sampleBufferSize;
	private int sampleSize;
	private int channels;
	private double sampleRate;
	private int headerSize;
	private byte[] buffer;
	private ArrayList<Oscillator> inputs;

	public AudioWaveManager(int sampleBufferSize, int sampleSize, int channels, double sampleRate, int headerSize) {
		this.sampleBufferSize = sampleBufferSize;
		this.sampleSize = sampleSize;
		this.channels = channels;
		this.headerSize = headerSize;
		this.sampleRate = sampleRate;

		this.inputs = new ArrayList<>();
		this.buffer = new byte[sampleBufferSize * sampleSize * channels + headerSize];

		init();
	}

	/**
	 * Initilizes all virtual channels
	 */
	private void init() {
		for (int i = 0; i < channels; i++) {
			inputs.add(new Oscillator(sampleRate, 440.0 * (1 + 4 * Math.random())));
		}
	}

	public void fillBuffer(int offset) {
		int increase = channels * sampleSize;

		// Converts a short value into 2 bytes
		for (int i = offset; i < (buffer.length / increase); i += increase) {
			double doubleValue = 0.0;

			// Get each input sample
			for (int j = 0; j < inputs.size(); j++) {
				doubleValue = inputs.get(j).nextSineSample();

				short shortValue = (short) (doubleValue * Short.MAX_VALUE);

				for (int k = 0; k < sampleSize; k++) {
					buffer[i + k] = ByteConverter.getByteAt(shortValue, k);
				}
			}

		}
	}

	public void fillBuffer() {
		fillBuffer(0);
	}

	public byte[] getBuffer() {
		return buffer;
	}

}
