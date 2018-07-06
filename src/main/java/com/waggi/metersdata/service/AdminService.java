package com.waggi.metersdata.service;

import com.waggi.metersdata.data.domain.User;
import com.waggi.metersdata.data.repository.UserRepository;
import com.waggi.metersdata.utils.Utils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Logger log;

    public void createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setUserRoles(new HashSet<>(Collections.singletonList(User.UserRole.ROLE_USER)));
        user.setEnabled(true);
        user.setMetersData(new HashSet<>());

        userRepository.save(user);
    }

    public boolean disableUser(Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return false;
        }

        if (user.isEnabled()) {
            user.setEnabled(false);
        } else {
            user.setEnabled(true);
        }

        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return false;
        }

        userRepository.delete(user);

        return true;
    }

    public User getUser(String username) {
        return userRepository.findUserByUsername(username);
    }

    public List<User> findUsersLikeByUsername(String username) {

        username = username.trim();

        return username.equals("") ?
                Utils.newArrayList(userRepository.findAll()) :
                Utils.newArrayList(userRepository.findByUsernameContaining(username));
    }
}
