package com.revplay.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Album {
    private int albumId;
    private int artistId;
    private String albumName;
    private String releaseDate;
    private String genre;
    private Timestamp createdAt;

    public Album() {}

    public Album(int artistId, String albumName, String releaseDate, String genre) {
        this.artistId = artistId;
        this.albumName = albumName;
        this.releaseDate = releaseDate;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Album{" +
                "albumId=" + albumId +
                ", albumName='" + albumName + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }

    public int getAlbumId() {
        return albumId;
    }
    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getArtistId() {
        return artistId;
    }
    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getAlbumName() {
        return albumName;
    }
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}
