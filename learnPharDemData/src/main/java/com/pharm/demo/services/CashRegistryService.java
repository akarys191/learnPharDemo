package com.pharm.demo.services;

import com.pharm.demo.model.CashRegistry;
import com.pharm.demo.services.base.CrudJpaService;

public interface CashRegistryService extends CrudJpaService<CashRegistry, Long> {
    CashRegistry findLatestForToday();
}