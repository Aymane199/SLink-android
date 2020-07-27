package com.ensim.mic.slink.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FolderOfUser implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("public")
    @Expose
    private String _public;
    @SerializedName("owner_id")
    @Expose
    private String ownerId;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("links")
    @Expose
    private String links;
    @SerializedName("likes")
    @Expose
    private String likes;
    @SerializedName("type")
    @Expose
    private String type;

    /**
     * No args constructor for use in serialization
     *
     */
    public FolderOfUser() {
    }

    /**
     *
     * @param owner
     * @param _public
     * @param name
     * @param description
     * @param links
     * @param id
     * @param ownerId
     * @param type
     * @param picture
     * @param likes
     */
    public FolderOfUser(String id, String name, String description, String picture, String _public, String ownerId, String owner, String links, String likes, String type) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.picture = picture;
        this._public = _public;
        this.ownerId = ownerId;
        this.owner = owner;
        this.links = links;
        this.likes = likes;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPublic() {
        return _public;
    }

    public void setPublic(String _public) {
        this._public = _public;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserFolder{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", picture='" + picture + '\'' +
                ", _public='" + _public + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", owner='" + owner + '\'' +
                ", links='" + links + '\'' +
                ", likes='" + likes + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
