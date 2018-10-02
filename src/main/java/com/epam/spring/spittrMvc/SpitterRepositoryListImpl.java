package com.epam.spring.spittrMvc;

import com.epam.spring.spittrMvc.data.Spitter;
import com.epam.spring.spittrMvc.data.Spittle;
import com.epam.spring.spittrMvc.interfaces.SpitterRepository;
import com.epam.spring.spittrMvc.interfaces.SpittleRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SpitterRepositoryListImpl implements SpitterRepository {
    List<Spitter> spitterList;


    public Spitter save(Spitter  spitter) {
        if(this.spitterList==null || this.spitterList.isEmpty())
        this.spitterList = new ArrayList<Spitter>();
            System.out.println(spitter.getUsername());
            this.spitterList.add(spitter);

        return  spitter ;
    }

    @Override
    public Spitter findByUsername(String username) {
        return spitterList.stream().filter(spitter -> spitter.getUsername().equals(username)).findFirst().get();
    }
}
