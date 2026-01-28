package com.revplay.controller;

import com.revplay.model.Album;
import com.revplay.model.Artist;
import com.revplay.model.FavoriteInfo;
import com.revplay.model.Song;
import com.revplay.service.ArtistService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArtistController {

    static Scanner sc = new Scanner(System.in);

    private String name;
    private String email;
    private String password;
    private String bio;
    private String genre;
    private String instagram;
    private String twitter;
    private int albumId;
    private String title;
    private double duration;
    private String releaseDate;
    private String albumName;
    private int songId;

    private Artist artist;
    private Song song;
    private Album album;

    private static final Logger logger =
            LogManager.getLogger(ArtistController.class);

    private static List<Integer> currentAlbumIds = new ArrayList<>();
    private static List<Integer> currentPlaylistSongIds = new ArrayList<>();

    private ArtistService artistService = new ArtistService();

    public void register() throws SQLException {
        System.out.println("*******************Registration*********************");
        System.out.println("Enter name");
        name = sc.nextLine();
        System.out.println("Enter email");
        email = sc.nextLine();
        System.out.println("Enter password");
        password = sc.nextLine();
        System.out.println("Enter bio");
        bio = sc.nextLine();
        System.out.println("Enter genre");
        genre = sc.nextLine();
        System.out.println("Enter instagram link");
        instagram = sc.nextLine();
        System.out.println("Enter twitter link");
        twitter = sc.nextLine();

        artist = new Artist(name, email, password, bio, genre, instagram, twitter);
        logger.info("started artist registration");
        String result = artistService.register(artist);
        System.out.println(result);
    }

    public Artist login() throws SQLException {
        System.out.println("*******************Login*********************");
        System.out.println("Enter your email");
        email = sc.nextLine();
        System.out.println("Enter your password");
        password = sc.nextLine();
        logger.info("started artist login");
        return artistService.login(email, password);
    }

    public void uploadSong(int artistId) throws SQLException {
        System.out.println("*******************Song Details*********************");
        System.out.println("Enter album id in which you want to upload song");
        albumId = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter title");
        title = sc.nextLine();
        System.out.println("Enter duration of song");
        duration = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter genre");
        genre = sc.nextLine();
        System.out.println("Enter release date - yyyy-MM-dd");
        releaseDate = sc.nextLine();

        song = new Song(artistId, albumId, title, genre, duration, releaseDate);
        logger.info("started song uploading");
        String result = artistService.uploadSong(song);
        System.out.println(result);
    }

    public void createAlbum(int artistId) throws SQLException {
        System.out.println("*******************Album Details*********************");
        System.out.println("Enter album name");
        albumName = sc.nextLine();
        System.out.println("Enter release date");
        releaseDate = sc.nextLine();
        System.out.println("Enter genre");
        genre = sc.nextLine();

        album = new Album(artistId, albumName, releaseDate, genre);
        logger.info("started creating album");
        String result = artistService.createAlbum(album);
        System.out.println(result);
    }

    public void getAllSongs(int artistId) throws SQLException {
        logger.info("fetching all songs of artist");
        System.out.println("********************Get all songs*********************");
        List<Song> allSongs = artistService.getAllSongs(artistId);
        currentPlaylistSongIds.clear();
        System.out.printf("%-8s %-30s%n", "SONG_ID", "SONG NAME");
        for (Song song : allSongs) {
            currentPlaylistSongIds.add(song.getSongId());
            System.out.printf("%-8s %-30s%n", song.getSongId(), song.getTitle());
        }
        if (allSongs.isEmpty()) System.out.println("No Songs here!");
    }

    public void getAllAlbums(int artistId) throws SQLException {
        logger.info("fetching all albums of artist");
        System.out.println("********************Get all albums*********************");
        List<Album> allAlbums = artistService.getAllAlbums(artistId);
        currentAlbumIds.clear();
        System.out.printf("%-8s %-25s %-10s%n", "ALBUM_ID", "ALBUM NAME", "GENRE");
        for (Album album : allAlbums) {
            currentAlbumIds.add(album.getAlbumId());
            System.out.printf("%-8s %-25s %-10s%n", album.getAlbumId(), album.getAlbumName(), album.getGenre());
        }
        if (allAlbums.isEmpty()) System.out.println("No album here, create album first");
    }

    public void updateSongDetails(int artistId) throws SQLException {
        System.out.println("********************Update Song Details*********************");
        System.out.println("Enter song id which details you want to update");
        songId = sc.nextInt();
        System.out.println("Enter new album id");
        albumId = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter new title");
        title = sc.nextLine();
        System.out.println("Enter new genre");
        genre = sc.nextLine();
        song = new Song();
        song.setSongId(songId);
        song.setArtistId(artistId);
        song.setTitle(title);
        song.setGenre(genre);
        song.setAlbumId(albumId);
        logger.info("updating song details");
        String result = artistService.updateSongDetails(song);
        System.out.println(result);
    }

    public void updateAlbumDetails(int artistId) throws SQLException {
        System.out.println("********************Update Album Details*********************");
        System.out.println("Enter album id which details you want to update");
        albumId = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter new album name");
        albumName = sc.nextLine();
        System.out.println("Enter new genre");
        genre = sc.nextLine();
        album = new Album();
        album.setArtistId(artistId);
        album.setAlbumId(albumId);
        album.setAlbumName(albumName);
        album.setGenre(genre);
        logger.info("updating album details");
        String result = artistService.uploadAlbumDetails(album);
        System.out.println(result);
    }

    public void deleteSong(int artistId) throws SQLException {
        System.out.println("********************Delete Song*********************");
        System.out.println("Enter song id which details you want to delete");
        songId = sc.nextInt();
        logger.info("started deleting songs");
        String result = artistService.deleteSong(songId);
        System.out.println(result);
    }

    public void deleteAlbum(int artistId) throws SQLException {
        System.out.println("********************Delete Album*********************");
        System.out.println("Enter album id which details you want to delete");
        albumId = sc.nextInt();
        logger.info("started deleting album");
        String result = artistService.deleteAlbum(albumId);
        System.out.println(result);
    }

    public void viewSongStats(int artistId) throws SQLException {
        logger.info("fetching song statistics");
        System.out.println("********************Song Statistics*********************");
        List<Song> songs = artistService.viewSongStats(artistId);
        System.out.printf("%-25s %-12s %s%n", "SONG_TITLE", "RELEASE_DATE", "PLAY_COUNT");
        System.out.println();
        for (Song song : songs) {
            System.out.printf("%-25s %-12s %s%n", song.getTitle(), song.getReleaseDate(), song.getPlayCount());
        }
    }

    public void viewUserWhoMarkedMySongFavourite(int artistId) throws SQLException {
        logger.info("fetching user who marked my song favourite");
        List<FavoriteInfo> favoriteInfoList = artistService.viewUserWhoMarkedMySongFavourite(artistId);
        System.out.printf("%-8s %-20s %-25s %-20s%n", "USER_ID", "USER_NAME", "SONG_NAME", "FAVOURITE_AT");
        for (FavoriteInfo info : favoriteInfoList) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String formattedDate = sdf.format(info.getFavoritedAt());
            System.out.printf("%-8s %-20s %-25s %-20s%n", info.getUserId(), info.getUserName(), info.getSongTitle(), formattedDate);
        }
        if (favoriteInfoList.isEmpty()) System.out.println("No songs marked favourite");
    }

    public List<Integer> getCurrentAlbumIds() {
        return currentAlbumIds;
    }

    public List<Integer> getCurrentPlaylistSongIds() {
        return currentPlaylistSongIds;
    }
}
