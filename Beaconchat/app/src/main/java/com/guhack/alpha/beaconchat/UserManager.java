package com.guhack.alpha.beaconchat;

/**
 * Created by Vlad on 11.10.2014.
 * This is a singleton class that contains all of the user specific information
 * Currently only stores username, but will be used for facebook later
 */
public class UserManager {
    private static UserManager userManager = new UserManager();

    private String username;

    private UserManager(){

    }

    public static UserManager getInstance(){
        return userManager;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
