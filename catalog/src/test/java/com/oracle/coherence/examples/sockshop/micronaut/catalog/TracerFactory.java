/*
 * Copyright (c) 2021 Oracle and/or its affiliates.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at
 * https://oss.oracle.com/licenses/upl.
 */

package com.oracle.coherence.examples.sockshop.micronaut.catalog;

import io.jaegertracing.internal.JaegerSpan;
import io.jaegertracing.internal.reporters.InMemoryReporter;
import io.jaegertracing.spi.Reporter;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Factory
public class TracerFactory {
	@Singleton
	Reporter inMemoryReporter() {
		return new SafeInMemoryReporter();
	}

	public static class SafeInMemoryReporter extends InMemoryReporter {

		@Override
		public synchronized List<JaegerSpan> getSpans()
		{
			List<JaegerSpan> spans = super.getSpans();
			int              size  = spans.size();
			List<JaegerSpan> copy  = new ArrayList<>(size);
			for (int i = 0, len = spans.size(); i < len; i++) {
				copy.add(spans.get(i));
			}
			return copy;
		}

		@Override
		public String toString() {
			return "SafeInMemoryReporter{" +
					"spans=" + getSpans() +
					'}';
		}
	}

}
