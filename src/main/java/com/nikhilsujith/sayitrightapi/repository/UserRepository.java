package com.nikhilsujith.sayitrightapi.repository;

import com.nikhilsujith.sayitrightapi.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

	public User findByPoolId(String poolId);

}
