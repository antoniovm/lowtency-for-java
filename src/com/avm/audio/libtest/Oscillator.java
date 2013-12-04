package com.avm.audio.libtest;

public class Oscillator {

	private double sampleRate;
	private double freq;
	private double amplitude;
	private long dt;
	
	public Oscillator(double sampleRate) {
		this.sampleRate = sampleRate;
		this.freq = 440.0;
		this.dt = 0l;
		this.amplitude = 1.0;
	}
	
	public double nextSineSample() {
		increaseTime();
		return WaveGenerator.sine(dt, sampleRate, freq)*amplitude;
	}
	
	public double nextSquareSample() {
		increaseTime();
		return WaveGenerator.square(dt, sampleRate, freq)*amplitude;
	}
	
	public void increaseTime() {
		dt=(dt+1)%Long.MAX_VALUE;
	}

	public void changeFrequency(double freq) {
		if (freq < 0) {
			throw new IllegalArgumentException("Frequency must be greater than 0");
		}
		this.freq = freq;
	}
	
	public void changeAmplitude(double amplitude) {
		if (amplitude < 0 || amplitude > 1) {
			throw new IllegalArgumentException("Amplitude must be betweem 0 and 1");
		}
		this.amplitude = amplitude;
	}
	
}
