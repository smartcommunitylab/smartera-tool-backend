package eu.fbk.dslab.smartera.engine.security;

public class UserInfo {
    private String email;
    private String name;

    public UserInfo(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }   
    public String getName() {
        return name;
    }
    public void setEmail(String email) {
        this.email = email;
    }   
    public void setName(String name) {
        this.name = name;
    }
}
