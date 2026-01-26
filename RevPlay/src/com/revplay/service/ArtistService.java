package com.revplay.service;

import com.revplay.model.Album;
import com.revplay.model.Artist;
import com.revplay.model.FavoriteInfo;
import com.revplay.model.Song;
import com.revplay.repository.Repository;

import java.sql.SQLException;
import java.util.List;

public class ArtistService {

    private final Repository userRepository = new Repository();

    public String register(Artist artist) throws SQLException {
        boolean result = userRepository.saveArtist(artist);
        return (result) ? "Registered Successfully!" : "Some error occurred!";
    }

    public Artist login(String email, String password) throws SQLException {

        return userRepository.fetchArtist(email, password);

    }

    public String uploadSong(Song song) throws SQLException {
        java.sql.Date sqlDate = java.sql.Date.valueOf(song.getReleaseDate());
        boolean result = userRepository.uploadSong(song, sqlDate);
        return (result) ? "Song Added Successfully!" : "Some error occurred!";
    }

    public String createAlbum(Album album) throws SQLException {
        java.sql.Date sqlDate = java.sql.Date.valueOf(album.getReleaseDate());
        boolean result = userRepository.createAlbum(album, sqlDate);
        return (result) ? "Album Created Successfully!" : "Some error occurred!";
    }

    public List<Song> getAllSongs(int artistId) throws SQLException {
        return userRepository.getAllSongs(artistId);
    }

    public List<Album> getAllAlbums(int artistId) throws SQLException {
        return userRepository.getAllAlbums(artistId);
    }

    public String updateSongDetails(Song song) throws SQLException {
        boolean result = userRepository.updateSongDetails(song);
        return (result) ? "Song Details Updated Successfully" : "Some Error Occurred";
    }

    public String uploadAlbumDetails(Album album) throws SQLException {
        boolean result = userRepository.uploadAlbumDetails(album);
        return (result) ? "Album Details Updated Successfully" : "Some Error Occurred";
    }

    public String deleteSong(int songId) throws SQLException {
        boolean result = userRepository.deleteSong(songId);
        return (result) ? "Song Deleted Successfully" : "Some Error Occured";
    }

    public String deleteAlbum(int albumId) throws SQLException {
        boolean result = userRepository.deleteAlbum(albumId);
        return (result) ? "Album Deleted Successfully" : "Some Error Occured";

    }

    public List<Song> viewSongStats(int artistId) throws SQLException {
        return userRepository.viewSongStats(artistId);
    }

    public List<FavoriteInfo> viewUserWhoMarkedMySongFavourite(int artistId) throws SQLException {
        return userRepository.viewUserWhoMarkedMySongFavourite(artistId);
    }
}
