package com.omnicrola.testing.util;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashMap;
import java.util.Set;

public class JunitExceptionHandler implements UncaughtExceptionHandler {

	private final HashMap<Thread, Throwable> exceptions;

	public JunitExceptionHandler() {
		this.exceptions = new HashMap<Thread, Throwable>();

	}

	public void assertNoExceptionsOccured() {
		if (this.exceptions.size() > 0) {
			String message = "";
			final Set<Thread> keySet = this.exceptions.keySet();
			for (final Thread thread : keySet) {
				final Throwable throwable = this.exceptions.get(thread);
				message += "Exception in thread \"" + thread.getName()
						+ "\" : " + throwable.getClass().getSimpleName();
			}

			throw new AssertionError(message);

		}
	}

	@Override
	public void uncaughtException(Thread thread, Throwable exception) {
		this.exceptions.put(thread, exception);
	}
}
