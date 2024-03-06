package com.learn;

import com.learn.exception.ResultException;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author sksingh created on 06/03/24
 */
public interface Result<V, E> {

    static <V, E> Result<V, E> ok(final V value) {
        return new Ok<>(value);
    }

    static <V, E> Result<V, E> error(final E error) {
        return new Error<>(error);
    }

    Optional<V> getValue();

    Optional<E> getError();

    boolean isOk();

    boolean isError();

    V unwrap() throws ResultException;

    default <U> Result<U, E> andThen(Function<V, Result<U, E>> function) {
        return getValue()
                .map(function)
                .orElseGet(() -> {
                    @SuppressWarnings("unchecked") final Result<U, E> result = (Result<U, E>) this;
                    return result;
                });
    }

    default <U> Result<U, E> map(Function<V, U> function) {
        return getValue()
                .map(v -> Result.<U, E>ok(function.apply(v)))
                .orElseGet(() -> {
                    @SuppressWarnings("unchecked") final Result<U, E> result = (Result<U, E>) this;
                    return result;
                });
    }

    default <F> Result<V, F> mapError(Function<E, F> function) {
        return getError()
                .map(e -> Result.<V, F>error(function.apply(e)))
                .orElseGet(() -> {
                    @SuppressWarnings("unchecked") final Result<V, F> result = (Result<V, F>) this;
                    return result;
                });
    }
}
