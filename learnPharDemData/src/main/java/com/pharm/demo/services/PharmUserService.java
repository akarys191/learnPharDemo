package com.pharm.demo.services;

import com.pharm.demo.model.PharmUser;

public interface PharmUserService extends CrudService<PharmUser, Long> {
    PharmUser findByUserName(String userName);
}
