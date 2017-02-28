package com.example.oauth.socialmedia.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleSocialUsersDetailService implements SocialUserDetailsService
{

    private final UserDetailsService userDetailsService;

    @Override
    public SocialUserDetails loadUserByUserId(String userId)
            throws UsernameNotFoundException
    {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        return new SocialUser(userDetails.getUsername(), userDetails.getPassword(),
                userDetails.getAuthorities());
    }

}