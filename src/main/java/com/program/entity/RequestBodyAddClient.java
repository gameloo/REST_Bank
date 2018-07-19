package com.program.entity;

public class RequestBodyAddClient {
    private Client client;
    private Long groupID;

    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public Long getGroupID() {
        return groupID;
    }
    public void setGroupID(Long groupID) {
        this.groupID = groupID;
    }

}
