/**
 * 
 */
package com.avm.audio.libtest;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;


/**
 * @author Antonio Vicente Martin
 *
 */
public class AudioWaveManager {

	private ByteBuffer buffer;
	private Oscillator oscillator;
	
	public AudioWaveManager(int bufferSize, double sampleRate) {
		this.oscillator = new Oscillator(sampleRate);
		this.buffer = ByteBuffer.allocate(bufferSize);
		this.buffer.order(ByteOrder.BIG_ENDIAN);
	}
	
	public void fillBuffer() {
		// Converts a short value into 2 bytes
		for (int i = 0; i < buffer.capacity()/2; i++) {
			double doubleValue = oscillator.nextSineSample();
			buffer.putShort(i,(short)(doubleValue*Short.MAX_VALUE));
		}
	}
	
	public byte[] getBuffer() {
		return buffer.array();
	}
	
}
