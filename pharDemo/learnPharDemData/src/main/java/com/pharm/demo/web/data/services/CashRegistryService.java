package com.pharm.demo.web.data.services;

import com.pharm.demo.web.data.model.CashRegistry;
import com.pharm.demo.web.data.services.base.CrudJpaService;

public interface CashRegistryService extends CrudJpaService<CashRegistry, Long> {
    CashRegistry findLatestForToday();
}