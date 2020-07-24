package com.ensim.mic.slink.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LinkOfFolder {

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
    @SerializedName("comments")
    @Expose
    private List<Comment> comments;

    /**
     * No args constructor for use in serialization
     *
     */
    public LinkOfFolder() {
    }

    /**
     *  @param id
     * @param name
     * @param picture
     * @param url
     * @param description
     * @param like
     * @param save
     * @param comments
     */
    public LinkOfFolder(String id, String name, String picture, String url, String description, Object like, String save, List<Comment> comments) {
        super();
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.url = url;
        this.description = description;
        this.like = like;
        this.save = save;
        this.comments = comments;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
