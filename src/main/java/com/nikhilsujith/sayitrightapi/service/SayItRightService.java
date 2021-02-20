package com.nikhilsujith.sayitrightapi.service;

import com.nikhilsujith.sayitrightapi.model.SayItRight;
import com.nikhilsujith.sayitrightapi.repository.SayItRightRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SayItRightService {

    SayItRightRepository repository;

    public SayItRightService(SayItRightRepository repository) {
        this.repository = repository;
    }

    //    Get all data
    public List<SayItRight> getAllData(){
        System.out.println(repository.findAll());
        return repository.findAll();
    }
}
