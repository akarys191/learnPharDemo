package com.pharm.demo.services.base;

public interface CrudJpaService<T, ID> extends CrudService<T, ID> {
    T saveFlush(T object);
}
