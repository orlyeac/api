package com.tuxpoli.customer.domain.auth;

public interface AuthenticationUtility {

    AuthenticationData authenticate(String username, String password);
}
