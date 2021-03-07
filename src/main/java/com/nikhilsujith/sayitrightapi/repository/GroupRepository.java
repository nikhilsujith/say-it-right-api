package com.nikhilsujith.sayitrightapi.repository;

import com.nikhilsujith.sayitrightapi.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {

    //    Get group by creator name
    @Query(value="{'creatorName':?0}")
    Optional<List<Group>> getGroupByCreatorName(String creatorName);

    //    Get group by creator name
    @Query(value="{'creatorId': ?0}")
    Optional<List<Group>> getGroupByCreatorId(String creatorId);
}
