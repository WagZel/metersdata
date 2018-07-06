package com.waggi.metersdata.config;

import com.waggi.metersdata.data.domain.MetersData;
import com.waggi.metersdata.data.domain.User;
import com.waggi.metersdata.data.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;

@Component
public class DataLoader {

    @Autowired
    private Logger log;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    private void loadData() {
        log.info("---------------------- DataLoader:loadData start ----------------------");

        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword(passwordEncoder.encode("111111"));
        user1.setUserRoles(new HashSet<>(Collections.singletonList(User.UserRole.ROLE_USER)));
        user1.setEnabled(true);
        user1.setMetersData(new HashSet<>());
        user1.getMetersData().add(new MetersData("COLD_WATER", 12.01, "!!!"));
        user1.getMetersData().add(new MetersData("HEAT_WATER", 8.41, "!!!"));
        user1.getMetersData().add(new MetersData("ELECTRICITY_DAY", 105.01, "!!!"));
        user1.getMetersData().add(new MetersData("ELECTRICITY_NIGHT", 160.52, "!!!"));
        user1.getMetersData().add(new MetersData("HEATING", 140.0, "!!!"));

        log.info(String.format("Create new user: username: %s, password: %s, role: %s", user1.getUsername(), user1.getPassword(), user1.getUserRoles()));

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword(passwordEncoder.encode("222222"));
        user2.setUserRoles(new HashSet<>(Collections.singletonList(User.UserRole.ROLE_USER)));
        user2.setEnabled(true);
        user2.setMetersData(new HashSet<>());
        user2.getMetersData().add(new MetersData("COLD_WATER", 500.0, "!!!"));
        user2.getMetersData().add(new MetersData("HEAT_WATER", 500.0, "!!!"));
        user2.getMetersData().add(new MetersData("ELECTRICITY_DAY", 500.0, "!!!"));
        user2.getMetersData().add(new MetersData("ELECTRICITY_NIGHT", 500.0, "!!!"));
        user2.getMetersData().add(new MetersData("HEATING", 500.0, "!!!"));

        log.info(String.format("Create new user: username: %s, password: %s, role: %s", user2.getUsername(), user2.getPassword(), user2.getUserRoles()));

        User user3 = new User();
        user3.setUsername("user3");
        user3.setPassword(passwordEncoder.encode("333333"));
        user3.setUserRoles(new HashSet<>(Collections.singletonList(User.UserRole.ROLE_USER)));
        user3.setEnabled(true);
        user3.setMetersData(new HashSet<>());
        user3.getMetersData().add(new MetersData("COLD_WATER", 12.01, "!!!"));
        user3.getMetersData().add(new MetersData("HEAT_WATER", 8.41, "!!!"));
        user3.getMetersData().add(new MetersData("ELECTRICITY_DAY", 105.01, "!!!"));
        user3.getMetersData().add(new MetersData("ELECTRICITY_NIGHT", 160.52, "!!!"));
        user3.getMetersData().add(new MetersData("HEATING", 140.0, "!!!"));

        log.info(String.format("Create new user: username: %s, password: %s, role: %s", user3.getUsername(), user3.getPassword(), user3.getUserRoles()));

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("000000"));
        admin.setMetersData(new HashSet<>());
        admin.setUserRoles(new HashSet<>());
        admin.getUserRoles().add(User.UserRole.ROLE_USER);
        admin.getUserRoles().add(User.UserRole.ROLE_ADMIN);
        admin.setEnabled(true);

        log.info(String.format("Create new user: username: %s, password: %s, role: %s", admin.getUsername(), admin.getPassword(), admin.getUserRoles()));

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(admin);

        log.info("---------------------- DataLoader:loadData end ----------------------");
    }
}
