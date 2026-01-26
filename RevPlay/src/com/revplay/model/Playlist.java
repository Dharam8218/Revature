package com.revplay.model;

import java.sql.Timestamp;

public class Playlist {
    private int playlistId;
    private int userId;
    private String playlistName;
    private String description;
    private boolean isPublic;

    @Override
    public String toString() {
        return "Playlist{" +
                "playlistId=" + playlistId +
                ", playlistName='" + playlistName + '\'' +
                ", description='" + description + '\'' +
                ", isPublic=" + isPublic +
                '}';
    }

    private Timestamp createdAt;

    public Playlist() {
    }

    public Playlist(int userId, String playlistName,
                    String description, boolean isPublic) {
        this.userId = userId;
        this.playlistName = playlistName;
        this.description = description;
        this.isPublic = isPublic;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}
