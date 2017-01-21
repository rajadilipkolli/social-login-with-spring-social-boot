package com.example.oauth.socialmedia.demo.services;

import java.util.UUID;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

import com.example.oauth.socialmedia.demo.dao.UsersDao;
import com.example.oauth.socialmedia.demo.model.UserProfile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rajakolli
 *
 */
@Slf4j
@RequiredArgsConstructor
public class AccountConnectionSignUpService implements ConnectionSignUp
{

    private final UsersDao usersDao;

    @Override
    public String execute(Connection<?> connection)
    {
        org.springframework.social.connect.UserProfile profile = connection
                .fetchUserProfile();
        String userId = UUID.randomUUID().toString();
        log.debug("Created user-id: " + userId);
        usersDao.createUser(userId, new UserProfile(userId, profile));
        return userId;
    }
}