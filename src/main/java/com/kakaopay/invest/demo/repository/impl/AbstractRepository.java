package com.kakaopay.invest.demo.repository.impl;

import java.util.*;

abstract public class AbstractRepository<T> {
    protected final Map<Long, T> map;

    public AbstractRepository() {
        this.map = new HashMap<>();
    }

    public List<T> findAll() {
        return selectAll();
    }

    public Optional<T> findById(Long id) {
        return selectById(id);
    }

    protected List<T> selectAll() {
        return new ArrayList<>(map.values());
    }

    protected Optional<T> selectById(Long id) {
        if (map.containsKey(id)) {
            return Optional.of(map.get(id));
        }

        return Optional.empty();
    }

    protected void insert(Long id, T obj) {
        if (map.containsKey(id)) {
            return;
        }

        map.put(id, obj);
    }

    protected void update(Long id, T obj) {
        if (map.containsKey(id)) {
            map.put(id, obj);
        }
    }

    protected void delete(Long id) {
        map.remove(id);
    }
}
