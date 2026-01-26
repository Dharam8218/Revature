package com.revplay.model;

public class Artist {
    private int artistId;
    private String artistName;
    private String email;
    private String password;
    private String bio;
    private String genre;
    private String instagram;
    private String twitter;

    public Artist() {
    }

    public Artist(String artistName, String email, String password,
                  String bio, String genre, String instagram,String twitter) {
        this.artistName = artistName;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.genre = genre;
        this.instagram=instagram;
        this.twitter=twitter;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }


}
