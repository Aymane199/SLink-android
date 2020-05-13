package com.ensim.mic.slink.Table;

public class Folder {
    int id,owner_id,links,likes;
    String name,description,picture,owner,type;
    Boolean isPublic;

    public Folder(int id, int owner_id, int links, int likes, String name, String description, String picture, String owner, String type,boolean isPublic) {
        this.id = id;
        this.owner_id = owner_id;
        this.links = links;
        this.likes = likes;
        this.name = name;
        this.isPublic = isPublic;
        this.description = description;
        this.picture = picture;
        this.owner = owner;
        this.type = type;
    }

    public Folder() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPublic() {
        return this.isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public int getLinks() {
        return links;
    }

    public void setLinks(int links) {
        this.links = links;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
