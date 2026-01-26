package com.revplay.service;

import com.revplay.model.PlayHistory;
import com.revplay.model.Playlist;
import com.revplay.model.Song;
import com.revplay.model.User;
import com.revplay.repository.Repository;

import java.sql.SQLException;
import java.util.List;

public class UserService {


    private final Repository userRepository = new Repository();

    public String register(User user) throws SQLException, ClassNotFoundException {
        boolean result = userRepository.save(user);

        return (result) ? "Registered Successfully!" : "Some error occurred!";
    }


    public User login(String email, String password) throws SQLException {

        return userRepository.fetch(email, password);

    }

    public List<Song> searchSongsByKeyword(String keyword) throws SQLException {
        keyword = "%" + keyword + "%";
        return userRepository.searchAll(keyword);
    }

    public List<Song> searchSongsByGenre(String genre) throws SQLException {
        return userRepository.searchSongByGenre(genre);
    }

    public List<Song> searchSongsByArtistName(String artistName) throws SQLException {
        return userRepository.searchSongByArtistName(artistName);
    }

    public List<Song> searchSongsByAlbumName(String albumName) throws SQLException {
        return userRepository.searchSongByAlbumName(albumName);
    }

    public String markSongFavourite(int userId, int songId) throws SQLException {
        boolean result = userRepository.markSongFavourite(userId, songId);
        return result ? "Song Marked Favourite Successfully" : "Some error occurred";
    }

    public List<Song> viewFavouriteSong(int userId) throws SQLException {
        return userRepository.viewFavouriteSongs(userId);
    }

    public String createPlaylist(Playlist playlist) throws SQLException {
        boolean result = userRepository.createPlaylist(playlist);
        return result ? "Playlist created successfully" : "Some error occurred";
    }

    public String addSongToPlaylist(int playlistId, int songId) throws SQLException {
        boolean result = userRepository.addSongToPlaylist(playlistId, songId);
        return result ? "Song successfully added to playlist" : "Some error occurred";
    }

    public String removeSongFromPlaylist(int playlistId, int songId) throws SQLException {
        boolean result = userRepository.removeSongFromPlaylist(playlistId, songId);
        return result ? "Song successfully removed from playlist" : "Some error occurred";
    }

    public List<Playlist> viewAllPlaylistByUser(int userId) throws SQLException {
        return userRepository.viewAllPlaylistByUser(userId);
    }

    public List<Playlist> viewAllPublicPlaylistByOtherUser(int userId) throws SQLException {
        return userRepository.viewAllPublicPlaylistByOtherUser(userId);
    }

    public String updatePlaylistDetails(Playlist playlist, int playlistId) throws SQLException {
        boolean result = userRepository.updatePlaylistDetails(playlist, playlistId);
        return result ? "Playlist Details Updated Successfully" : "Some error occurred";
    }

    public String deletePlaylist(int userId, int playlistId) throws SQLException {
        boolean result = userRepository.deletePlaylist(userId, playlistId);
        return result ? "Playlist Deleted Successfully" : "Some error occurred";
    }

    public Song getSongNameById(int songId) throws SQLException {
        return userRepository.getSongById(songId);
    }

    public String addSongToPlayHistory(PlayHistory playHistory) throws SQLException {
        boolean result = userRepository.addSongToPlayHistory(playHistory);
        return result ? "Song added to listening history" : "Some error occurred";
    }

    public List<Song> viewRecentPlayedSongs(int userId) throws SQLException {
        return userRepository.viewRecentPlayedSongs(userId);
    }

    public List<Song> viewListeningHistory(int userId) throws SQLException {
        return userRepository.viewListeningHistory(userId);
    }

    public List<Song> viewAllSongs() throws SQLException {
        return userRepository.viewAllSongs();
    }

    public List<Song> viewPlaylistSongs(int userId, int playlistId) throws SQLException {
        return userRepository.viewPlaylistSong(userId, playlistId);
    }
}
