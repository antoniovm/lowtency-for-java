package com.avm.audio.libtest;

public class WaveGenerator {

	public static double sine(long dt, double lamda, double freq) {
		return Math.sin((float) (2.0 * Math.PI / lamda * freq));
	}

	public static double square(long dt, double lamda, double freq) {
		return WaveGenerator.sine(dt, lamda, freq) > 0 ? 1 : -1;
	}
}