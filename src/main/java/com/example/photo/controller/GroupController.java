package com.example.photo.controller;

import com.example.photo.model.Group;
import com.example.photo.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    // Create a new group
    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        Group createdGroup = groupService.createGroup(group);
        return ResponseEntity.ok(createdGroup);
    }

    // Get all groups
    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    // Get a single group by ID
    @GetMapping("/{groupId}")
    public ResponseEntity<Group> getGroupById(@PathVariable String groupId) {
        Group group = groupService.getGroupById(groupId);
        return ResponseEntity.ok(group);
    }

    // Join a group
    @PostMapping("/{groupId}/join")
    public ResponseEntity<Group> joinGroup(@PathVariable String groupId, @RequestParam String username) {
        Group group = groupService.joinGroup(groupId, username);
        return ResponseEntity.ok(group);
    }

    // Leave a group
    @PostMapping("/{groupId}/leave")
    public ResponseEntity<Group> leaveGroup(@PathVariable String groupId, @RequestParam String username) {
        Group group = groupService.leaveGroup(groupId, username);
        return ResponseEntity.ok(group);
    }

    // Get all groups created by a specific user
    @GetMapping("/created-by/{username}")
    public ResponseEntity<List<Group>> getGroupsCreatedBy(@PathVariable String username) {
        List<Group> groups = groupService.getGroupsCreatedBy(username);
        return ResponseEntity.ok(groups);
    }

    // Get all groups where a specific user is a member
    @GetMapping("/member-of/{username}")
    public ResponseEntity<List<Group>> getGroupsForMember(@PathVariable String username) {
        List<Group> groups = groupService.getGroupsForUser(username);
        return ResponseEntity.ok(groups);
    }

    @PostMapping("/{groupId}/add-member")
    public ResponseEntity<Void> addMemberToGroup(
            @PathVariable String groupId,
            @RequestParam String username) {
        groupService.addMemberToGroup(groupId, username);
        return ResponseEntity.ok().build();
    }

    /**
     * Edit a group's details (name and description).
     *
     * @param groupId The ID of the group to edit.
     * @param updatedGroup The updated group details.
     * @return ResponseEntity with the updated group or an error message.
     */
    @PutMapping("/{groupId}")
    public ResponseEntity<Group> editGroup(
            @PathVariable String groupId,
            @RequestBody Group updatedGroup) {
        Group group = groupService.editGroup(groupId, updatedGroup);
        return ResponseEntity.ok(group);
    }

    /**
     * Delete a group by its ID.
     *
     * @param groupId The ID of the group to delete..
     * @return ResponseEntity indicating success or failure.
     */
    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(
            @PathVariable String groupId) {
        groupService.deleteGroup(groupId);
        return ResponseEntity.noContent().build(); // 204 No Content on success
    }
}