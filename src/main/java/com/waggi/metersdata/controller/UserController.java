package com.waggi.metersdata.controller;

import com.waggi.metersdata.data.FilterMetersData;
import com.waggi.metersdata.data.domain.MetersData;
import com.waggi.metersdata.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Logger log;

    @PostMapping("/list")
    public ResponseEntity<Object[]> list(@RequestBody FilterMetersData filter, Principal principal) {
        Object[] result;

        try {
            result = userService.findAllFilteredMetersData(filter, principal);
        } catch (UserPrincipalNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<MetersData> addMetersData(@RequestBody MetersData metersData, Principal principal) {

        MetersData returnMetersData;

        try {
            returnMetersData = userService.addMetersData(metersData, principal);
        } catch (IllegalArgumentException e) {
            log.info("UserController.addMetersData", e);
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(returnMetersData);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMetersData(@PathVariable("id") Long id, Principal principal) {

        if (!userService.deleteMetersData(id, principal)) {
            log.info(String.format("UserController.deleteMetersData delete object with id:%d not found", id));
        }

        return ResponseEntity.noContent().build();
    }
}
