package com.example.film_med_venner.DTO;


public class FProfileDTO {

    private String ID;
    private String name;

    FProfileDTO(){

    }

    public FProfileDTO(String ID, String name, String email, String profileURL) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.profileURL = profileURL;
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

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    private String email;
    private String profileURL;





}
