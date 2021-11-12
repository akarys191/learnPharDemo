package com.pharm.demo.web.data.services;

import com.pharm.demo.web.data.model.PharmUser;
import com.pharm.demo.web.data.services.base.CrudService;

public interface PharmUserService<T extends PharmUser> extends CrudService<T, Long> {
    T findByUserName(String userName);
}
