package com.revplay.model;

import java.sql.Timestamp;

public class PlayHistory {
    private int historyId;
    private int userId;
    private int songId;
    private Timestamp playedAt;

    public PlayHistory() {
    }

    public PlayHistory(int userId, int songId) {
        this.userId = userId;
        this.songId = songId;
    }

    public int getHistoryId() {
        return historyId;
    }

    public int getUserId() {
        return userId;
    }

    public int getSongId() {
        return songId;
    }

    public Timestamp getPlayedAt() {
        return playedAt;
    }
}
