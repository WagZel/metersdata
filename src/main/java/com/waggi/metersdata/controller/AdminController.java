package com.waggi.metersdata.controller;

import com.waggi.metersdata.data.domain.User;
import com.waggi.metersdata.service.AdminService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private Logger log;

    @GetMapping("/list")
    public ResponseEntity<List<User>> usersList(@RequestParam(defaultValue = "") String username) {

        List<User> users = adminService.findUsersLikeByUsername(username);

        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        if (!adminService.deleteUser(id)) {
            log.info(String.format("AdminController.deleteUser deleted object with id:%d not found", id));

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/disabled/{id}")
    public ResponseEntity<Void> disableUser(@PathVariable Long id) {

        if (!adminService.disableUser(id)) {
            log.info(String.format("AdminController.disableUser disabled object with id:%d not found", id));

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
