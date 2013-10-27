package de.thm.mni.vewg30.databaseexporter.writer;

import java.io.IOException;
import java.io.Writer;

public class MultiWriter extends Writer {

	private Writer[] writers;

	public MultiWriter(Writer[] writers) {
		this.writers = writers;
	}

	public MultiWriter(Object lock) {
		super(lock);
	}

	@Override
	public void close() throws IOException {
		for (int i = 0; i < writers.length; ++i) {
			writers[i].close();
		}

	}

	@Override
	public void flush() throws IOException {
		for (int i = 0; i < writers.length; ++i) {
			writers[i].flush();
		}

	}

	@Override
	public void write(char[] arg0, int arg1, int arg2) throws IOException {
		for (int i = 0; i < writers.length; ++i) {
			writers[i].write(arg0, arg1, arg2);
		}

	}

}
