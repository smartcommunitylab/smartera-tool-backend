package eu.fbk.dslab.smartera.engine.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "invite")
public class Invite {

    @Id
    private String id;
    @Indexed
    private String owner;
    private String sipId;
    private String invitedUser;

    public Invite() {
    }

    public Invite(String owner, String sipId, String invitedUser) {
        this.owner = owner;
        this.sipId = sipId;
        this.invitedUser = invitedUser;
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

    public String getSipId() {
        return sipId;
    }

    public void setSipId(String sipId) {
        this.sipId = sipId;
    }

    public String getInvitedUser() {
        return invitedUser;
    }

    public void setInvitedUser(String invitedUser) {
        this.invitedUser = invitedUser;
    }

}