package com.pharm.demo.web.data.services.base;

public interface CrudJpaService<T, ID> extends CrudService<T, ID> {
    T saveFlush(T object);
}
