package com.crio.app.exchange;

import java.util.Set;

import com.crio.app.data.Badges;

public record UserRegistrationResponse(String userId, String username, Integer score, Set<Badges> badges) {
    
}
