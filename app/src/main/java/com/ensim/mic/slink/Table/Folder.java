package com.ensim.mic.slink.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Folder {
    @SerializedName("id")
    @Expose
    private Integer id;
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
    private Boolean _public;
    @SerializedName("shareLink")
    @Expose
    private Boolean shareLink;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("owner")
    @Expose
    private User owner;

    /**
     * No args constructor for use in serialization
     *
     */
    public Folder() {
    }

    /**
     *
     * @param date
     * @param owner
     * @param _public
     * @param name
     * @param description
     * @param shareLink
     * @param id
     * @param picture
     * @param url
     */
    public Folder(Integer id, String name, String description, String picture, Boolean _public, Boolean shareLink, String url, String date, User owner) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.picture = picture;
        this._public = _public;
        this.shareLink = shareLink;
        this.url = url;
        this.date = date;
        this.owner = owner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Boolean getPublic() {
        return _public;
    }

    public void setPublic(Boolean _public) {
        this._public = _public;
    }

    public Boolean getShareLink() {
        return shareLink;
    }

    public void setShareLink(Boolean shareLink) {
        this.shareLink = shareLink;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return owner;
    }

    public void setUser(User owner) {
        this.owner = owner;
    }


    @Override
    public String toString() {
        return "Folder{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", picture='" + picture + '\'' +
                ", _public=" + _public +
                ", shareLink=" + shareLink +
                ", url='" + url + '\'' +
                ", date='" + date + '\'' +
                ", owner=" + owner +
                '}';
    }
}
