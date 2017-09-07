package com.example.scouser.minirepositoryviewer;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Scouser on 9/3/2017.
 */


public class GitHub extends RealmObject {
    private String name, full_name, owner,url,type,avatar;
    @PrimaryKey
    private int id;
    public GitHub() {
    }

    public GitHub(int id,String name, String full_name, String owner,String url,String type,String avatar) {
        this.name = name;
        this.full_name = full_name;
        this.owner = owner;
        this.url = url;
        this.type = type;
        this.id=id;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getID() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
