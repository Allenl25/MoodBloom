package com.moodbloom.models;

public class User {
    private long id;
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private Boolean guidelines;
    private Boolean privacy;


    public User(){

    }

    public User(long id, String username, String email, String password, String confirmPassword, Boolean guidelines, Boolean privacy){
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.guidelines = guidelines;
        this.privacy = privacy;

    }

    public Long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;

    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;

    }


public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;

    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

     public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Boolean getGuidelines() {
        return guidelines;
    }

    public void setGuidelines(Boolean guidelines) {
        this.guidelines = guidelines;
    }

    public Boolean getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Boolean privacy) {
        this.privacy = privacy;
    }
    
    
}
