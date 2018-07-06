package com.waggi.metersdata.service;

import com.waggi.metersdata.data.FilterMetersData;
import com.waggi.metersdata.data.domain.MetersData;
import com.waggi.metersdata.data.domain.User;
import com.waggi.metersdata.exception.UserNotFoundException;
import com.waggi.metersdata.data.repository.MetersDataRepository;
import com.waggi.metersdata.data.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Principal;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private MetersDataRepository metersDataRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminService adminService;

    @Autowired
    private Logger log;

    public Object[] findAllFilteredMetersData(FilterMetersData filter, Principal principal) throws UserPrincipalNotFoundException {

        User user = userRepository.findUserByUsername(principal.getName());

        if (user == null) {
            throw new UserPrincipalNotFoundException("User not found");
        }

        if (!filter.checkSortField() || !filter.checkOrder()) {
            throw new IllegalArgumentException();
        }

        if (filter.getEndDate() == null) {
            filter.setEndDate(new Date());
        }

        if (filter.getStartDate() != null && filter.getStartDate().after(filter.getEndDate())) {
            log.info("UserService.findAllFilteredMetersData invalid arguments");
            throw new IllegalArgumentException();
        }

        return metersDataRepository.findAllByFilter(filter, user);
    }

    public MetersData addMetersData(MetersData metersData, Principal principal) {

        if (metersData == null) { throw new IllegalArgumentException(); }

        User user = userRepository.findUserByUsername(principal.getName());

        if (user == null) {
            throw new UserNotFoundException();
        }

        if (metersData.getId() == null) {
            user.getMetersData().add(metersData);
        } else {
            user.getMetersData().add(editMetersData(metersData, user));
        }

        userRepository.save(user);

        return metersData;
    }

    public boolean deleteMetersData(Long id, Principal principal) {

        User user = adminService.getUser(principal.getName());

        if (user == null) {
            throw new UserNotFoundException();
        }

        boolean result = user.getMetersData()
                .removeIf(it ->
                        it.getId().equals(id)
                );

        if (result) {
            userRepository.save(user);
        }

        return result;
    }

    private MetersData editMetersData(MetersData metersData, User user) {

        Collection<MetersData> data = user.getMetersData();

        if (data.removeIf(it -> it.getId().equals(metersData.getId()))) {
            data.add(metersData);
            metersDataRepository.save(metersData);
        }

        return metersData;
    }
}
