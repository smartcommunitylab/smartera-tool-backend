package eu.fbk.dslab.smartera.engine.model;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sip")
public class SIP {
    
    @Id
    private String id;
    @Indexed
    private String owner;
    private String inviteCode;
    private Map<String, Object> body;

    public SIP() {
    }

    public SIP(String id, String owner, String inviteCode, Map<String, Object> body) {
        this.id = id;
        this.owner = owner;
        this.inviteCode = inviteCode;
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

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }
}