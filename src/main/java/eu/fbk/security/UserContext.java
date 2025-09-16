package eu.fbk.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class UserContext {
    public static final String USER_ID = "UserId";

    public static String getOwner() {
        //return request.getHeader(USER_ID);
        if (SecurityContextHolder.getContext().getAuthentication() instanceof JwtAuthenticationToken) {
            Jwt jwt = (Jwt)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String email = jwt.getClaimAsString("email");
            return email;
        }
        return null;
    }
}
