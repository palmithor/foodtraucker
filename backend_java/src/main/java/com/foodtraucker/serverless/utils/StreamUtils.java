package com.foodtraucker.serverless.utils;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Utilities to work with streams. Has utilities to transform iterators to a stream etc.
 *
 * @author palmithor
 * @since 2017-04-27.
 */
public class StreamUtils {

    private StreamUtils() {
    }

    public static <T> Stream<T> asStream(Iterator<T> sourceIterator) {
        return asStream(sourceIterator, false);
    }

    public static <T> Stream<T> asStream(Iterator<T> sourceIterator, boolean parallel) {
        Iterable<T> iterable = () -> sourceIterator;
        return StreamSupport.stream(iterable.spliterator(), parallel);
    }
}

