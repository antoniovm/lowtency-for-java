/**
 * 
 */
package com.avm.stream;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Antonio Vicente Martin
 *
 */
public class OutputStreamManager implements Runnable{
	
	private byte [] data = {'h','e','l','l','o'};
	private OutputStream os;
	
	/**
	 * 
	 */
	public OutputStreamManager(OutputStream os, byte [] buffer) {
		this.os = os;
		//this.data = buffer;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		//while (true) {
			try {
				os.write(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		//}
		
	}

}
