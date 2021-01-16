package com.example.film_med_venner.DTO;


public class FProfileDTO {

    private String ID;
    private String name;
    private String email;
    private String profilePictureURL;

    FProfileDTO(){

    }

    public FProfileDTO(String ID, String name, String email, String profilePictureURL) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.profilePictureURL = profilePictureURL;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }


}
