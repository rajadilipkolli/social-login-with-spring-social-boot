package com.example.oauth.socialmedia.demo.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author rajakolli
 *
 */
@Getter
@Setter
public class UserProfile
{

    private final String userId;

    private String name;

    private final String firstName;

    private final String lastName;

    private final String email;

    private final String username;

    public UserProfile(String userId, String name, String firstName, String lastName,
            String email, String username)
    {
        this.userId = userId;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;

        fixName();
    }

    private void fixName()
    {
        // Is the name null?
        if (name == null)
        {

            // Ok, lets try with first and last name...
            name = firstName;

            if (lastName != null)
            {
                if (name == null)
                {
                    name = lastName;
                }
                else
                {
                    name += " " + lastName;
                }
            }

            // Try with email if still null
            if (name == null)
            {
                name = email;
            }

            // Try with username if still null
            if (name == null)
            {
                name = username;
            }

            // If still null set name to UNKNOWN
            if (name == null)
            {
                name = "UNKNOWN";
            }
        }
    }

    public UserProfile(String userId,
            org.springframework.social.connect.UserProfile userprofile)
    {
        this.userId = userId;
        this.name = userprofile.getName();
        this.firstName = userprofile.getFirstName();
        this.lastName = userprofile.getLastName();
        this.email = userprofile.getEmail();
        this.username = userprofile.getUsername();
    }

}