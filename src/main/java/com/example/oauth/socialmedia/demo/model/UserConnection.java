package com.example.oauth.socialmedia.demo.model;

import lombok.Data;

/**
 * @author rajakolli
 *
 */
@Data
public class UserConnection
{

    private final String userId;

    private final String providerId;
    private final String providerUserId;
    private final int rank;
    private final String displayName;
    private final String profileUrl;
    private final String imageUrl;
    private final String accessToken;
    private final String secret;
    private final String refreshToken;
    private final Long expireTime;

}