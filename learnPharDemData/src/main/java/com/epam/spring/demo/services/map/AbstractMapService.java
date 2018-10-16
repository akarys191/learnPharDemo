package com.epam.spring.demo.services.map;

import com.epam.spring.demo.model.BaseEntity;

import java.util.*;

public abstract class AbstractMapService<T extends BaseEntity,ID extends Long> {
    protected Map<Long,T>  map = new HashMap<>();
    Set<T> findAll(){
        return new HashSet<>(map.values());
    }

    T findById(ID id){
        return map.get(id);
    }

    T save(T object){
        if(object.getId()==null || object.getId().equals(0L))
            object.setId(this.getNextId());

        map.put(object.getId(),object);
        return object;
    }

    void deleteById(ID id){
        map.remove(id);
    }

    void delete(T object){
        map.entrySet().removeIf(entry-> entry.getValue().equals(object));
    }

    private  Long getNextId(){
      if(!map.isEmpty())
        return  Collections.max(map.keySet())+1;
      else return 1L;
    }
}
