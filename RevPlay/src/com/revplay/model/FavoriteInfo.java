package com.revplay.model;

import java.sql.Timestamp;

public class FavoriteInfo {

    private int userId;
    private String userName;
    private String songTitle;
    private Timestamp favoritedAt;

    public FavoriteInfo(int userId, String userName, String songTitle, Timestamp favoritedAt) {
        this.userId = userId;
        this.userName = userName;
        this.songTitle = songTitle;
        this.favoritedAt = favoritedAt;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public Timestamp getFavoritedAt() {
        return favoritedAt;
    }
}

