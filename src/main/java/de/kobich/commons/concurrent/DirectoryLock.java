package de.kobich.commons.concurrent;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

import org.apache.log4j.Logger;

import de.kobich.commons.utils.StreamUtils;

public class DirectoryLock {
	private static final Logger logger = Logger.getLogger(DirectoryLock.class);
	private final File file;
	private FileChannel channel;
	private FileLock lock;
	private boolean locked;

	public DirectoryLock(File file) {
		this.file = file;
		this.locked = false;
	}

	@SuppressWarnings("resource")
	public boolean tryLock() {
		try {
			this.channel = new RandomAccessFile(this.file, "rw").getChannel();

			try {
				this.lock = this.channel.tryLock();
			}
			catch (OverlappingFileLockException e) {
				// already locked
				release();
				return false;
			}

			if (lock == null) {
				release();
				return false;
			}
			// file is now locked
			this.locked = true;
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	public void registerShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			// destroy the lock when the JVM is closing
			public void run() {
				DirectoryLock.this.release();
				DirectoryLock.this.deleteFile();
			}
		});
	}

	public void release() {
		try {
			if (lock != null) {
				lock.release();
			}
		}
		catch (Exception e) {
			logger.warn("Lock cannot be released", e);
		}
		StreamUtils.forceClose(channel);
	}

	private void deleteFile() {
		try {
			if (locked) {
				file.delete();
			}
		}
		catch (Exception e) {
			logger.warn("Lock file cannot be deleted", e);
		}
	}

}
