package com.ensim.mic.slink.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FolderLink {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("like")
    @Expose
    private Object like;
    @SerializedName("save")
    @Expose
    private String save;

    /**
     * No args constructor for use in serialization
     *
     */
    public FolderLink() {
    }

    /**
     *
     * @param like
     * @param name
     * @param save
     * @param description
     * @param id
     * @param picture
     * @param url
     */
    public FolderLink(String id, String name, String picture, String url, String description, Object like, String save) {
        super();
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.url = url;
        this.description = description;
        this.like = like;
        this.save = save;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getLike() {
        return like;
    }

    public void setLike(Object like) {
        this.like = like;
    }

    public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }

    @Override
    public String toString() {
        return "Link{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", like=" + like +
                ", save='" + save + '\'' +
                '}';
    }
}
