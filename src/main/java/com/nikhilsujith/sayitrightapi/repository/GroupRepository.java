package com.nikhilsujith.sayitrightapi.repository;

import com.nikhilsujith.sayitrightapi.model.Group;
import com.nikhilsujith.sayitrightapi.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends MongoRepository<Group, ObjectId> {

    //    Get group by creator name
    @Query(value = "{'creatorId':?0}")
    Optional<List<Group>> getGroupByCreatorId(String groupName);

//    //    Delete by id
//    @Query(value="{'_id' : $0}", delete = true)
//    public User deleteById(ObjectId _id);
}
