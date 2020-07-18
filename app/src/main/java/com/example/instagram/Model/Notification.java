package com.example.instagram.Model;

public class Notification {

    private String userId;
    private String text;
    private String postId;
    private boolean isPost;

    public Notification() {

    }
    public Notification(String userId, String text, String postId, boolean isPost) {
        this.userId = userId;
        this.text = text;
        this.postId = postId;
        this.isPost = isPost;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public boolean isIsPost() {
        return isPost;
    }

    public void setIsPost(boolean post) {
        isPost = post;
    }


}
