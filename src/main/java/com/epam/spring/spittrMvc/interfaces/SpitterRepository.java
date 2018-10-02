package com.epam.spring.spittrMvc.interfaces;

import com.epam.spring.spittrMvc.data.Spitter;
import com.epam.spring.spittrMvc.data.Spittle;

import java.util.List;


public interface SpitterRepository {

    Spitter save(Spitter spitter);

    Spitter findByUsername(String username);

}