package com.rapplogic.xbee.util;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWrapper implements IIntArrayInputStream {

	private InputStream in;
	
	public InputStreamWrapper(InputStream in) {
		this.in = in;
	}
	
	@Override
	public int read() throws IOException {
		return in.read();
	}
	
	@Override
	public int read(String s) throws IOException {
		return in.read();
	}
}
