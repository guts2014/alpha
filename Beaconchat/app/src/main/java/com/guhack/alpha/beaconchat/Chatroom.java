package com.guhack.alpha.beaconchat;

/**
 * Created by Raitis on 12.10.2014..
 */
public class Chatroom {
    private String id;
    private String name;

    public Chatroom(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getName(){return name;}
    public String getId(){return id;}

}
