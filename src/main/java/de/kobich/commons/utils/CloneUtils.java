package de.kobich.commons.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Utilitiy class to clone objects.
 */
public class CloneUtils {
	/**
	 * Creates and returns a deep copy of this object
	 * @param <T> the object type
	 * @param obj the object to clone
	 * @return the object clone
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deepCopy(T obj) {
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		try {
			// Serialization
			FasterByteArrayOutputStream fbaos = new FasterByteArrayOutputStream();
			oos = new ObjectOutputStream(fbaos);
			oos.writeObject(obj);
			oos.flush();
			// Deserialization
			ois = new ObjectInputStream(fbaos.getInputStream());
			Object copyObj = ois.readObject();
			Class<T> clazz = (Class<T>) obj.getClass();
			T clone = clazz.cast(copyObj);
			return clone;
		}
		catch (IOException exc) {
			throw new IllegalStateException("Clone failed", exc);
		}
		catch (ClassNotFoundException exc) {
			throw new IllegalStateException("Clone failed", exc);
		}
		finally {
			StreamUtils.forceClose(oos);
			StreamUtils.forceClose(ois);
		}
	}

	/**
	 * This <code>InputStream</code> is adapted from <code>ByteArrayInputStream</code>. It does not use method synchronization due to performance
	 * issues. See http://javatechniques.com/blog/faster-deep-copies-of-java-objects/
	 */
	private static class FasterByteArrayInputStream extends InputStream {
		private byte[] buf = null;
		private int count = 0;
		private int pos = 0;

		public FasterByteArrayInputStream(byte[] buf, int count) {
			this.buf = buf;
			this.count = count;
		}

		@Override
		public int available() {
			return count - pos;
		}

		@Override
		public int read() {
			return (pos < count) ? (buf[pos++] & 0xff) : -1;
		}

		@Override
		public int read(byte[] ba, int off, int len) {
			if (pos >= count) {
				return -1;
			}
			if ((pos + len) > count) {
				len = count - pos;
			}
			System.arraycopy(buf, pos, ba, off, len);
			pos += len;
			return len;
		}

		@Override
		public long skip(long n) {
			if ((pos + n) > count) {
				n = count - pos;
			}
			if (n < 0) {
				return 0;
			}
			pos += n;
			return n;
		}

	}

	/**
	 * This <code>OutputStream</code> is adapted from <code>ByteArrayOutputStream</code>. It does not use method synchronization and also does not
	 * make copies in the method <code>toByteArray</code> See http://javatechniques.com/blog/faster-deep-copies-of-java-objects/
	 */
	private static class FasterByteArrayOutputStream extends OutputStream {

		private byte[] buf = null;

		private int size = 0;

		public FasterByteArrayOutputStream() {
			this(5 * 1024);
		}

		public FasterByteArrayOutputStream(int initSize) {
			this.size = 0;
			this.buf = new byte[initSize];
		}

		private void verifyBufferSize(int sz) {
			if (sz > buf.length) {
				byte[] old = buf;
				buf = new byte[Math.max(sz, 2 * buf.length)];
				System.arraycopy(old, 0, buf, 0, old.length);
				old = null;
			}
		}

		@Override
		public final void write(byte b[]) {
			verifyBufferSize(size + b.length);
			System.arraycopy(b, 0, buf, size, b.length);
			size += b.length;
		}

		@Override
		public final void write(byte b[], int off, int len) {
			verifyBufferSize(size + len);
			System.arraycopy(b, off, buf, size, len);
			size += len;
		}

		@Override
		public final void write(int b) {
			verifyBufferSize(size + 1);
			buf[size++] = (byte) b;
		}

		public InputStream getInputStream() {
			return new FasterByteArrayInputStream(buf, size);
		}
	}
}
