package eu.fbk.dslab.smartera.engine.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "like")
public class Like {

    @Id
    private String id;
    @Indexed
    private String owner;
    private String name;
    private String sipId;
    private String componentId;
    private long timestamp;

    public Like() {
    }

    public Like(String owner, String name, String sipId, String componentId) {
        this.owner = owner;
        this.name = name;
        this.sipId = sipId;
        this.componentId = componentId;
        this.timestamp = System.currentTimeMillis();
    }

    public Like(String owner, String name, String sipId) {
        this.owner = owner;
        this.name = name;
        this.sipId = sipId;
        this.timestamp = System.currentTimeMillis();
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

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}