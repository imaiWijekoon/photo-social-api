package com.example.photo.repository;


import com.example.photo.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {
    // Find all groups created by a specific user
    List<Group> findByCreatedBy(String createdBy);

    // Find all groups where a specific user is a member
    List<Group> findByMembersContaining(String username);
}
