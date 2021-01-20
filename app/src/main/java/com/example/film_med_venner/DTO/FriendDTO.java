package com.example.film_med_venner.DTO;

public class FriendDTO {
    private String requester;
    private int status;

    public FriendDTO(String requester, int status) {
        this.requester = requester;
        this.status = status;
    }

    public FriendDTO() {
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
