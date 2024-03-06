package com.learn;

import java.util.Optional;

class Ok<V, E> implements Result<V, E> {

    private final V value;

    public Ok(V value) {
        this.value = value;
    }

    @Override
    public Optional<V> getValue() {
        return Optional.of(value);
    }

    @Override
    public Optional<E> getError() {
        return Optional.empty();
    }

    @Override
    public boolean isOk() {
        return true;
    }

    @Override
    public boolean isError() {
        return false;
    }

    @Override
    public V unwrap() {
        return value;
    }
}