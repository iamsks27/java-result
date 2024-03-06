package com.learn;

import com.learn.exception.ResultException;

import java.util.Optional;

class Error<V, E> implements Result<V, E> {

    private final E error;

    public Error(E error) {
        this.error = error;
    }

    @Override
    public Optional<V> getValue() {
        return Optional.empty();
    }

    @Override
    public Optional<E> getError() {
        return Optional.of(error);
    }

    @Override
    public boolean isOk() {
        return false;
    }

    @Override
    public boolean isError() {
        return true;
    }

    @Override
    public V unwrap() throws ResultException {
        throw new ResultException("Unwrap is not support on Error");
    }
}