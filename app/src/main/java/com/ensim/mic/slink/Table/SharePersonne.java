package com.ensim.mic.slink.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SharePersonne {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("gmail")
    @Expose
    private String gmail;
    @SerializedName("access_right")
    @Expose
    private String accessRight;

    @SerializedName("user")
    @Expose
    private User user;
    /**
     * No args constructor for use in serialization
     *
     */
    public SharePersonne() {
    }

    /**
     *  @param id
     * @param userName
     * @param gmail
     * @param accessRight
     * @param user
     */
    public SharePersonne(String id, String userName, String gmail, String accessRight, User user) {
        super();
        this.id = id;
        this.userName = userName;
        this.gmail = gmail;
        this.accessRight = accessRight;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(String accessRight) {
        this.accessRight = accessRight;
    }

    @Override
    public String toString() {
        return "SharePersonne{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", gmail='" + gmail + '\'' +
                ", accessRight='" + accessRight + '\'' +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
