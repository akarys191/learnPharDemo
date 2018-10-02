package com.epam.spring.spittrMvc;

import com.epam.spring.spittrMvc.data.Spittle;
import com.epam.spring.spittrMvc.interfaces.SpittleRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SpittleRepositoryListImpl implements SpittleRepository {
    List<Spittle> spittleList;
    @Override
    public List<Spittle> findSpittles(long max, int count) {
        return createSpittleList(20);
    }

    @Override
    public Spittle findOne(Long spittleId){
        System.out.println("spittleList.size(): "+spittleList.size()+ "-> "+spittleId);
       // Optional<Spittle> optionalSpittles = Optional.of(spittleList);
        return this.spittleList.stream().filter(s->s.getId().equals(spittleId)).findFirst().get();

    }

    private List<Spittle> createSpittleList(int count) {
        this.spittleList = new ArrayList<Spittle>();
        for (int i=0; i < count; i++) {
            this.spittleList.add(new Spittle((long)i,"Spittle " + i, LocalDateTime.now()));
        }
        return  this.spittleList;
    }
}
