package com.nikhilsujith.sayitrightapi.repository;

import com.nikhilsujith.sayitrightapi.model.SayItRight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SayItRightRepository extends MongoRepository<SayItRight, String> {
}
