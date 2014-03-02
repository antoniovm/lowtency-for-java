/**
 * 
 */
package com.avm.audio.libtest;

import com.avm.util.ByteConverter;



/**
 * @author Antonio Vicente Martin
 *
 */
public class AudioWaveManager {

	private byte [] buffer;
	private Oscillator oscillator;
	
	
	public AudioWaveManager(int sampleBufferSize, int sampleSize, int channels, double sampleRate) {
		this.oscillator = new Oscillator(sampleRate);
		this.buffer = new byte [sampleBufferSize];
	}
	
	public void fillBuffer() {
		// Converts a short value into 2 bytes
		for (int i = 0; i < buffer.length; i+=2) {
			double doubleValue = oscillator.nextSineSample();
			short shortValue = (short)(doubleValue*Short.MAX_VALUE);
			buffer[i] = ByteConverter.getByteAt(shortValue, 0);
			buffer[i+1] = ByteConverter.getByteAt(shortValue, 1);
		}
	}
	
	public byte[] getBuffer() {
		return buffer;
	}
	
}
