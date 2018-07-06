package com.waggi.metersdata.controller;

import com.waggi.metersdata.service.AdminService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private Logger log;

    @GetMapping("/registration")
    public ModelAndView registration() {
        return new ModelAndView("registration");
    }

    @PostMapping("/registration")
    public ModelAndView createUser(@RequestParam String username, @RequestParam String password) {
        adminService.createUser(username, password);
        log.info("Registration controller");
        return new ModelAndView("redirect:/login");
    }
}
