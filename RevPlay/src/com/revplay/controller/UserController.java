package com.revplay.controller;

import com.revplay.model.PlayHistory;
import com.revplay.model.Playlist;
import com.revplay.model.Song;
import com.revplay.model.User;
import com.revplay.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserController {

    public static Scanner sc = new Scanner(System.in);

    private String email;
    private String password;

    private List<Integer> songIds = new ArrayList<>();
    private List<Integer> playlistIds = new ArrayList<>();

    private final UserService userService = new UserService();

    private static final Logger logger =
            LogManager.getLogger(UserController.class);

    public void register() throws SQLException, ClassNotFoundException {
        System.out.println("*******************Registration*********************");
        System.out.println("Enter your name");
        String name = sc.nextLine();
        System.out.println("Enter your email");
        email = sc.nextLine();
        System.out.println("Enter your password");
        password = sc.nextLine();
        System.out.println("What's your favourite color, this is for security");
        String color = sc.nextLine();
        User user = new User(name, email, password, color);
        logger.info("starting user registration");
        String result = userService.register(user);
        System.out.println(result);
    }

    public User login() throws SQLException {
        System.out.println("*******************Login*********************");
        System.out.println("Enter your email");
        email = sc.nextLine();
        System.out.println("Enter your password");
        password = sc.nextLine();
        logger.info("starting user login");
        return userService.login(email, password);
    }

    public void viewAllSongs() throws SQLException {
        logger.info("fetching songs");
        List<Song> songs = userService.viewAllSongs();
        songIds.clear();
        System.out.printf("%-8s %s%n", "SONG_ID", "SONG_NAME");
        for (Song song : songs) {
            songIds.add(song.getSongId());
            System.out.printf("%-8s %s%n", song.getSongId(), song.getTitle());
        }
        if (songs.isEmpty()) System.out.println("No Songs here!");

    }

    public void searchSongsByKeyword() throws SQLException {
        System.out.println("*******************Search Songs*********************");
        System.out.println("Enter keyword");
        String keyword = sc.nextLine();
        List<Song> songs = userService.searchSongsByKeyword(keyword);
        songIds.clear();
        logger.info("searching songs by keyword");
        System.out.printf("%-8s %s%n", "SONG_ID", "SONG_NAME");
        for (Song song : songs) {
            songIds.add(song.getSongId());
            System.out.printf("%-8s %s%n", song.getSongId(), song.getTitle());
        }
        if (songs.isEmpty()) System.out.println("No Songs here!");
    }

    public void browseSongByGenre() throws SQLException {
        System.out.println("*******************Search Songs by Genre*********************");
        System.out.println("Enter Genre");
        String genre = sc.nextLine();
        List<Song> songs = userService.searchSongsByGenre(genre);
        songIds.clear();
        logger.info("searching songs by genre");
        System.out.printf("%-8s %s%n", "SONG_ID", "SONG_NAME");
        for (Song song : songs) {
            songIds.add(song.getSongId());
            System.out.printf("%-8s %s%n", song.getSongId(), song.getTitle());
        }
        if (songs.isEmpty()) System.out.println("No Songs here!");
    }

    public void browseSongByArtist() throws SQLException {
        System.out.println("*******************Search Songs by Artist*********************");
        System.out.println("Enter Artist Name");
        String artistName = sc.nextLine();
        List<Song> songs = userService.searchSongsByArtistName(artistName);
        songIds.clear();
        logger.info("searching songs by artist name");
        System.out.printf("%-8s %s%n", "SONG_ID", "SONG_NAME");
        for (Song song : songs) {
            songIds.add(song.getSongId());
            System.out.printf("%-8s %s%n", song.getSongId(), song.getTitle());
        }
        if (songs.isEmpty()) System.out.println("No Songs here!");
    }

    public void browseSongByAlbum() throws SQLException {
        System.out.println("*******************Search Songs by Album Name*********************");
        System.out.println("Enter Album Name");
        String albumName = sc.nextLine();
        List<Song> songs = userService.searchSongsByAlbumName(albumName);
        logger.info("searching songs by album name");
        songIds.clear();
        System.out.printf("%-8s %s%n", "SONG_ID", "SONG_NAME");
        for (Song song : songs) {
            songIds.add(song.getSongId());
            System.out.printf("%-8s %s%n", song.getSongId(), song.getTitle());
        }
        if (songs.isEmpty()) System.out.println("No Songs here!");
    }

    public void markSongFavourite(int userId, int songId) throws SQLException {
        logger.info("marking song favourite");
        String result = userService.markSongFavourite(userId, songId);
        System.out.println(result);
    }

    public void viewFavouriteSong(int userId) throws SQLException {
        logger.info("fetching favourite songs");
        System.out.println("*******************Favourite Songs*********************");
        List<Song> songs = userService.viewFavouriteSong(userId);
        songIds.clear();
        System.out.printf("%-8s %s%n", "SONG_ID", "SONG_NAME");
        for (Song song : songs) {
            songIds.add(song.getSongId());
            System.out.printf("%-8s %s%n", song.getSongId(), song.getTitle());
        }
        if (songs.isEmpty()) System.out.println("No Songs here!");
    }

    public void createPlaylist(int userId) throws SQLException {
        System.out.println("*******************Create Playlist*********************");
        System.out.println("Enter name of playlist");
        String name = sc.nextLine();
        System.out.println("Enter description of playlist");
        String description = sc.nextLine();
        System.out.println("Visibility of playlist (Public) Enter true/false");
        boolean isPublic = sc.nextBoolean();
        logger.info("starting creating playlist");
        Playlist playlist = new Playlist(userId, name, description, isPublic);
        String result = userService.createPlaylist(playlist);
        System.out.println(result);
    }

    public void addSongToPlaylist(int songId) throws SQLException {
        System.out.println("*******************Adding Song to Playlist*********************");
        System.out.println("Enter playlist id");
        int playlistId = sc.nextInt();
        logger.info("adding to playlist");
        String result = userService.addSongToPlaylist(playlistId, songId);
        System.out.println(result);
    }

    public void removeSongFromPlaylist(int playlistId, int songId) throws SQLException {
        logger.info("removing songs from playlist");
        System.out.println("*******************Deleting song from Playlist*********************");
        String result = userService.removeSongFromPlaylist(playlistId, songId);
        System.out.println(result);
    }

    public void viewAllPlaylistByUser(int userId) throws SQLException {
        logger.info("fetching all playlist");
        System.out.println("*******************All Playlist*********************");
        List<Playlist> playlists = userService.viewAllPlaylistByUser(userId);
        playlistIds.clear();
        System.out.printf("%-12s %s%n", "PLAYLIST_ID", "PLAYLIST_NAME");
        for (Playlist playlist : playlists) {
            playlistIds.add(playlist.getPlaylistId());
            System.out.printf("%-12s %s%n", playlist.getPlaylistId(), playlist.getPlaylistName());
        }
        if (playlists.isEmpty()) System.out.println("No Playlist here!, Create Playlist first");
    }

    public void updatePlaylistDetails(int userId) throws SQLException {
        System.out.println("*******************Update Playlist Details*********************");
        System.out.println("Enter playlist id you want to update");
        int playlistId = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter new playlist name");
        String playlistName = sc.nextLine();
        System.out.println("Enter new description");
        String description = sc.nextLine();
        System.out.println("Enter new visibility, Public Enter true/false");
        boolean isPublic = sc.nextBoolean();
        logger.info("updating playlist details");
        Playlist playlist = new Playlist(userId, playlistName, description, isPublic);
        String result = userService.updatePlaylistDetails(playlist, playlistId);
        System.out.println(result);
    }

    public void viewAllPublicPlaylistByOtherUser(int userId) throws SQLException {
        logger.info("fetching public playlist by other user");
        System.out.println("*******************Public Playlist By Other User*********************");
        List<Playlist> playlists = userService.viewAllPublicPlaylistByOtherUser(userId);
        playlistIds.clear();
        System.out.printf("%-12s %s%n", "PLAYLIST_ID", "PLAYLIST_NAME");
        for (Playlist playlist : playlists) {
            playlistIds.add(playlist.getPlaylistId());
            System.out.printf("%-12s %s%n", playlist.getPlaylistId(), playlist.getPlaylistName());
        }
        if (playlists.isEmpty()) System.out.println("No Playlist here!, Create Playlist first");
    }

    public void deletePlaylist(int userId) throws SQLException {
        System.out.println("*******************Delete Playlist*********************");
        System.out.println("Enter playlist id you want to delete");
        int playlistId = sc.nextInt();
        logger.info("deleting playlist");
        String result = userService.deletePlaylist(userId, playlistId);
        System.out.println(result);
    }

    public String getSongNameBySongId(int songId) throws SQLException {
        Song song = userService.getSongNameById(songId);
        return song.getTitle() != null ? song.getTitle() : "Null";
    }

    public void playSong(int userId, int songId) throws SQLException {
        logger.info("playing song");
        PlayHistory playHistory = new PlayHistory(userId, songId);
        String result = userService.addSongToPlayHistory(playHistory);
        if (result.equals("Song added to listening history")) {
            System.out.println("▶ Now Playing song..." + getSongNameBySongId(songId));
        } else {
            System.out.println("Error in playing song");
        }

    }

    public void viewRecentPlayed(int userId) throws SQLException {
        logger.info("fetching recent played songs");
        System.out.println("*******************Recently played songs*********************");
        List<Song> songs = userService.viewRecentPlayedSongs(userId);
        System.out.printf("%-8s %s%n", "SONG_ID", "SONG_NAME");
        songIds.clear();
        for (Song song : songs) {
            songIds.add(song.getSongId());
            System.out.printf("%-8s %s%n", song.getSongId(), song.getTitle());
        }
        if (songs.isEmpty()) System.out.println("No Songs here!");
    }

    public void viewListeningHistory(int userId) throws SQLException {
        logger.info("fetching listening history");
        System.out.println("*******************Listening history*********************");
        List<Song> songs = userService.viewListeningHistory(userId);
        songIds.clear();
        System.out.printf("%-8s %s%n", "SONG_ID", "SONG_NAME");
        for (Song song : songs) {
            songIds.add(song.getSongId());
            System.out.printf("%-8s %s%n", song.getSongId(), song.getTitle());
        }
        if (songs.isEmpty()) System.out.println("No Songs here!");
    }

    public List<Integer> getCurrentSongIds() {
        return songIds;
    }

    public List<Integer> getPlaylistIds() {
        return playlistIds;
    }

    public void viewPlayListSong(int userId, int playlistId) throws SQLException {
        System.out.println("*******************View Playlist Songs*********************");
        List<Song> songs = userService.viewPlaylistSongs(userId, playlistId);
        songIds.clear();
        logger.info("fetching songs in playlist");
        for (Song song : songs) {
            songIds.add(song.getSongId());
            System.out.println(song.getSongId() + " " + song.getTitle());
        }
        if (songs.isEmpty()) System.out.println("No Songs here!");
    }

}
