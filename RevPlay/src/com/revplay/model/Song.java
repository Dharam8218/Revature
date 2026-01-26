package com.revplay.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Song {
    private int songId;
    private int artistId;
    private Integer albumId;
    private String title;
    private String genre;
    private double duration;
    private String releaseDate;
    private int playCount;
    private Timestamp createdAt;

    public Song() {}

    public Song(int artistId, Integer albumId, String title,
                String genre, double duration, String releaseDate) {
        this.artistId = artistId;
        this.albumId = albumId;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songId=" + songId +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }

    public int getSongId() {
        return songId;
    }
    public void setSongId(int songId) {
        this.songId = songId;
    }

    public int getArtistId() {
        return artistId;
    }
    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public Integer getAlbumId() {
        return albumId;
    }
    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getDuration() {
        return duration;
    }
    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getPlayCount() {
        return playCount;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }
}
