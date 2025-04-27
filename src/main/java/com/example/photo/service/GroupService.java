package com.example.photo.service;

import com.example.photo.model.Group;
import com.example.photo.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    // Create a new group
    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    // Get all groups
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    // Get a single group by ID
    public Group getGroupById(String groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
    }

    // Join a group
    public Group joinGroup(String groupId, String username) {
        Group group = getGroupById(groupId);
        if (!group.getMembers().contains(username)) {
            group.getMembers().add(username); // Add the user to the members list
            return groupRepository.save(group);
        }
        throw new RuntimeException("User is already a member of this group");
    }

    // Leave a group
    public Group leaveGroup(String groupId, String username) {
        Group group = getGroupById(groupId);
        if (group.getMembers().contains(username)) {
            group.getMembers().remove(username); // Remove the user from the members list
            return groupRepository.save(group);
        }
        throw new RuntimeException("User is not a member of this group");
    }

    // Get all groups created by a specific user
    public List<Group> getGroupsCreatedBy(String username) {
        return groupRepository.findByCreatedBy(username);
    }

    // Get all groups where a specific user is a member
    public List<Group> getGroupsForUser(String username) {
        return groupRepository.findByMembersContaining(username);
    }

    // In GroupService
    public void addMemberToGroup(String groupId, String username) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        if (!group.getMembers().contains(username)) {
            group.getMembers().add(username);
            groupRepository.save(group);
        }
    }

    /**
     * Edit a group's details (name and description).
     *
     * @param groupId The ID of the group to edit.
     * @param updatedGroup The updated group details.
     * @return The updated group.
     * @throws RuntimeException If the group doesn't exist or the user is unauthorized.
     */
    public Group editGroup(String groupId, Group updatedGroup) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        // Update the group details
        group.setName(updatedGroup.getName());
        group.setDescription(updatedGroup.getDescription());

        return groupRepository.save(group);
    }

    /**
     * Delete a group by its ID.
     *
     * @param groupId The ID of the group to delete.
     * @throws RuntimeException If the group doesn't exist or the user is unauthorized.
     */
    public void deleteGroup(String groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        try {
            groupRepository.deleteById(groupId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete the group", e);
        }
    }

}
