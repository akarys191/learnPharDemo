package com.epam.spring.spittrMvc.interfaces;

import com.epam.spring.spittrMvc.data.Spittle;
import java.util.List;
import java.util.Map;


public interface SpittleRepository {
   List<Spittle> findSpittles(long max, int count);
    Spittle findOne(Long spittleId);
}