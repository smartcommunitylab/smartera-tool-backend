package eu.fbk.dslab.smartera.engine.model;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user-prefs")
public class UserPrefs {
    @Id
    private String id;
    @Indexed
    private String owner;
    private Map<String, Object> body;

    public UserPrefs() {
    }

    public UserPrefs(String id, String owner, Map<String, Object> body) {
        this.id = id;
        this.owner = owner;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Map<String, Object> getBody() {
        return body;
    }
    
    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

}