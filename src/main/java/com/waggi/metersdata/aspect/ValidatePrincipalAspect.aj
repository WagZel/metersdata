package com.waggi.metersdata.aspect;

import com.waggi.metersdata.exception.UserDisabledException;
import com.waggi.metersdata.exception.UserNotFoundException;
import com.waggi.metersdata.data.repository.UserRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Aspect
public aspect ValidatePrincipalAspect {
    @Autowired
    private Logger log;

    @Autowired
    private UserRepository userRepository;

    @Pointcut("execution(* com.waggi.metersdata.controller.AdminController.*(..))")
    private void adminController() {}

    @Pointcut("execution(* com.waggi.metersdata.controller.UserController.*(..))")
    private void userControllers() {}

    @Before("adminController() || userControllers()")
    public void checkUserPersistentConsistency(JoinPoint joinPoint) {

        UserDetails userDetails = null;

        try {
            userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Boolean enabled = userRepository.findUserEnabledByUsername(userDetails.getUsername());

            if (enabled == null) {
                throw new UserNotFoundException();
            }

            if (!enabled) {
                throw new UserDisabledException();
            }

        } catch (ClassCastException e) {
            log.info("User non authenticated");
            throw e;
        } catch (UserNotFoundException e) {
            log.info("User not found in database");
            throw e;
        } catch (UserDisabledException e) {
            log.info(String.format("User %s is disable", userDetails.getUsername()));
            throw e;
        }
    }
}
