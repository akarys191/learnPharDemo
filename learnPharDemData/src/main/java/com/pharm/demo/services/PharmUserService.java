package com.pharm.demo.services;

import com.pharm.demo.model.PharmUser;

public interface PharmUserService<T extends PharmUser> extends CrudService<T, Long> {
    T findByUserName(String userName);
}
