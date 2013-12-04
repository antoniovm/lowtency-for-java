package com.avm.audio.libtest;

public class WaveGenerator {

	public static double sine(long dt, double lamda, double freq) {
		return Math.sin((float) (dt * 2.0 * Math.PI / lamda * freq));
	}

	public static double square(long dt, double lamda, double freq) {
		//return WaveGenerator.sine(dt, lamda, freq) > 0 ? 1 : -1;
		return (dt / lamda) % freq < freq ? 1 : -1;
	}
}
