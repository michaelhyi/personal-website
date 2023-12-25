package com.personalwebsite.api.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthWhitelistedEmails {
    private List<String> whitelistedEmails;

    public AuthWhitelistedEmails(
            @Value("#{'${auth.whitelisted-emails}'.split(',')}")
            List<String> whitelistedEmails
    ) {
        this.whitelistedEmails = whitelistedEmails;
    }

    public List<String> getWhitelistedEmails() {
        return whitelistedEmails;
    }
}
