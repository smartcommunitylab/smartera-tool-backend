package eu.fbk.security;

import jakarta.servlet.http.HttpServletRequest;

public class UserContext {
    public static final String USER_ID = "UserId";

    public static String getOwner(HttpServletRequest request) {
        return request.getHeader(USER_ID);
    }
}
