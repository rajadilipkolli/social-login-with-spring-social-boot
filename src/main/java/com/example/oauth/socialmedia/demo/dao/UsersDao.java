package com.example.oauth.socialmedia.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.oauth.socialmedia.demo.model.UserConnection;
import com.example.oauth.socialmedia.demo.model.UserProfile;

import lombok.extern.slf4j.Slf4j;

/**
 * @author rajakolli
 *
 */
@Repository
@Slf4j
public class UsersDao
{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersDao(DataSource dataSource)
    {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public UserProfile getUserProfile(final String userId)
    {
        log.debug("SQL SELECT ON UserProfile: {}", userId);

        return jdbcTemplate.queryForObject("select * from UserProfile where userId = ?",
                new RowMapper<UserProfile>()
                {
                    @Override
                    public UserProfile mapRow(ResultSet resultSet, int rowNum)
                            throws SQLException
                    {
                        return new UserProfile(userId, resultSet.getString("name"),
                                resultSet.getString("firstName"),
                                resultSet.getString("lastName"),
                                resultSet.getString("email"),
                                resultSet.getString("username"));
                    }
                }, userId);
    }

    public UserConnection getUserConnection(final String userId)
    {
        log.debug("SQL SELECT ON UserConnection: {}", userId);

        return jdbcTemplate.queryForObject(
                "select * from UserConnection where userId = ?",
                new RowMapper<UserConnection>()
                {
                    @Override
                    public UserConnection mapRow(ResultSet rs, int rowNum)
                            throws SQLException
                    {
                        return new UserConnection(userId, rs.getString("providerId"),
                                rs.getString("providerUserId"), rs.getInt("rank"),
                                rs.getString("displayName"), rs.getString("profileUrl"),
                                rs.getString("imageUrl"), rs.getString("accessToken"),
                                rs.getString("secret"), rs.getString("refreshToken"),
                                rs.getLong("expireTime"));
                    }
                }, userId);
    }

    public void createUser(String userId, UserProfile profile)
    {
        if (log.isDebugEnabled())
        {
            log.debug("SQL INSERT ON users, authorities and userProfile: " + userId
                    + " with profile: " + profile.getEmail() + ", "
                    + profile.getFirstName() + ", " + profile.getLastName() + ", "
                    + profile.getName() + ", " + profile.getUsername());
        }

        jdbcTemplate.update(
                "INSERT into users(username,password,enabled) values(?,?,true)", userId,
                RandomStringUtils.randomAlphanumeric(8));
        jdbcTemplate.update("INSERT into authorities(username,authority) values(?,?)",
                userId, "USER");
        jdbcTemplate.update(
                "INSERT into userProfile(userId, email, firstName, lastName, name, username) values(?,?,?,?,?,?)",
                userId, profile.getEmail(), profile.getFirstName(), profile.getLastName(),
                profile.getName(), profile.getUsername());
    }
}
