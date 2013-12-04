/**
 * 
 */
package com.avm.audio.libtest;


/**
 * @author Antonio Vicente Martin
 *
 */
public class AudioWaveManager {

	private byte [] buffer;
	private Oscillator oscillator;
	
	public AudioWaveManager(int bufferSize, double sampleRate) {
		this.oscillator = new Oscillator(sampleRate);
		this.buffer = new byte [bufferSize];
	}
	
	public void fillBuffer() {
		for (int i = 0; i < buffer.length; i++) {
			buffer[i] = (byte)(oscillator.nextSineSample()*Byte.MAX_VALUE);
		}
	}
	
	public byte[] getBuffer() {
		return buffer;
	}
	
}
