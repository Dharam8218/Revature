package com.revplay.repository;

import com.revplay.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {

    private static final String URL = "jdbc:mysql://localhost:3306/revplay";
    private static final String USER = "root";
    private static final String PASSWORD = "dharam";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String INSERT_USER_SQL =
            "INSERT INTO user (name, email, password, security_answer) VALUES (?, ?, ?, ?)";
    private static final String SELECT_USER_SQL =
            "SELECT * FROM user WHERE email = ? AND password = ?";
    private static final String INSERT_ARTIST_SQL =
            "INSERT INTO artists (artist_name,email,password,bio,genre,instagram,twitter) VALUES (?,?,?,?,?,?,?)";
    private static final String SELECT_ARTIST_SQL =
            "SELECT artist_id,artist_name FROM artists WHERE email = ? AND password = ?";
    private static final String INSERT_SONG =
            "INSERT INTO songs (artist_id,album_id,title, genre,duration,release_date) VALUES (?,?,?,?,?,?)";
    private static final String INSERT_ALBUM =
            "INSERT INTO albums (artist_id, album_name, release_date, genre) VALUES (?,?,?,?)";
    private static final String SELECT_ALL_SONGS_BY_ARTIST_ID =
            "SELECT * FROM songs where artist_id = ?";
    private static final String SELECT_ALL_ALBUMS_BY_ARTIST_ID =
            "SELECT * FROM albums where artist_id = ?";
    private static final String UPDATE_SONG_DETAILS =
            "UPDATE songs SET album_id = ?, title = ?, genre = ? WHERE song_id = ?";
    private static final String UPDATE_ALBUM_DETAILS =
            "UPDATE albums SET album_name = ?, genre = ? WHERE album_id = ?";
    private static final String DELETE_SONG =
            "DELETE FROM songs WHERE song_id = ?";
    private static final String DELETE_ALBUM =
            "DELETE FROM albums WHERE album_id = ?";
    private static final String SEARCH_ALL =
            "SELECT * FROM songs WHERE title LIKE ? ORDER BY RAND()";
    private static final String SEARCH_SONG_BY_GENRE =
            "SELECT * FROM songs WHERE genre = ? ORDER BY RAND()";
    private static final String SEARCH_SONG_BY_ARTIST_NAME =
            "SELECT s.song_id, s.title FROM songs s INNER JOIN artists a ON s.artist_id = a.artist_id WHERE artist_name = ? ORDER BY RAND()";
    private static final String SEARCH_SONG_BY_ALBUM_NAME =
            "SELECT s.song_id, s.title FROM songs s INNER JOIN albums a ON s.album_id = a.album_id WHERE album_name = ? ORDER BY RAND()";
    private static final String MARK_SONG_FAVOURITE =
            "INSERT INTO favorites (user_id,  song_id) VALUES (?,?)";
    private static final String VIEW_FAVOURITE =
            "SELECT s.song_id, s.title FROM songs s INNER JOIN favorites f ON s.song_id = f.song_id INNER JOIN user u ON f.user_id = u.user_id WHERE u.user_id = ? ORDER BY RAND()";
    private static final String CREATE_PLAYLIST =
            "INSERT INTO playlists (user_id, playlist_name,description,is_public) VALUES (?,?,?,?)";
    private static final String ADD_SONG_TO_PLAYLIST =
            "INSERT INTO playlist_songs (playlist_id, song_id) VALUES (?,?)";
    private static final String REMOVE_SONG_FROM_PLAYLIST =
            "DELETE FROM playlist_songs WHERE playlist_id = ? AND song_id = ?";
    private static final String VIEW_ALL_PLAYLIST =
            "SELECT * FROM playlists WHERE user_id = ?";
    private static final String VIEW_ALL_PUBLIC_PLAYLIST =
            "SELECT * FROM playlists WHERE is_public = true AND user_id NOT IN (?)";
    private static final String UPDATE_PLAYLIST =
            "UPDATE playlists SET playlist_name=?,description=?,is_public=? WHERE user_id=? AND playlist_id=?";
    private static final String DELETE_PLAYLIST =
            "DELETE FROM playlists WHERE user_id = ? AND playlist_id = ?";
    private static final String GET_SONG_NAME_BY_ID =
            "SELECT * FROM songs WHERE song_id = ?";
    private static final String INSERT_SONG_INTO_HISTORY =
            "INSERT INTO play_history (user_id,song_id) VALUES (?,?)";
    private static final String VIEW_RECENT_PLAYED =
            "SELECT s.title,s.song_id,p.played_at FROM songs s INNER JOIN play_history p ON s.song_id = p.song_id WHERE p.user_id = ? ORDER BY p.played_at DESC LIMIT 5";
    private static final String VIEW_LISTENING_HISTORY =
            "SELECT s.title,s.song_id,p.played_at FROM songs s INNER JOIN play_history p ON s.song_id = p.song_id WHERE p.user_id = ?";
    private static final String VIEW_ALL_SONGS =
            "SELECT song_id, title FROM songs ORDER BY RAND() LIMIT 10";
    private static final String VIEW_PLAYLIST_SONGS =
            "SELECT s.song_id,s.title FROM playlists p INNER JOIN playlist_songs ps ON p.playlist_id = ps.playlist_id INNER JOIN songs s ON ps.song_id = s.song_id WHERE (p.user_id = ? OR p.is_public = 1) AND p.playlist_id = ?";
    private static final String VIEW_USER =
            "SELECT u.user_id, u.name AS user_name,s.song_id,s.title AS song_name, f.favorited_at FROM favorites f JOIN songs s ON f.song_id = s.song_id JOIN user u  ON f.user_id = u.user_id WHERE s.artist_id = ? ORDER BY f.favorited_at DESC";
    private static final String GET_USER_BY_EMAIL =
            "SELECT * FROM user WHERE email = ?";
    private static final String FORGOT_PASSWORD =
            "UPDATE user SET password  = ? WHERE email = ?";

    private Connection conn;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public boolean save(User user) throws ClassNotFoundException, SQLException {

        boolean isInserted = false;

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            preparedStatement = conn.prepareStatement(INSERT_USER_SQL);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getSecurityAnswer());
            int rowUpdated = preparedStatement.executeUpdate();

            isInserted = rowUpdated > 0;


        } catch (SQLException e) {
            System.out.println("Error inserting user: " + e.getMessage());
        }

        preparedStatement.close();
        conn.close();

        return isInserted;


    }

    public User fetch(String email, String password) throws SQLException {
        User user = null;

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            preparedStatement = conn.prepareStatement(SELECT_USER_SQL);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("name"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error founding user: " + e.getMessage());
        }

        resultSet.close();
        preparedStatement.close();
        conn.close();

        return user;

    }

    public boolean saveArtist(Artist artist) throws SQLException {

        boolean isInserted = false;

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            preparedStatement = conn.prepareStatement(INSERT_ARTIST_SQL);
            preparedStatement.setString(1, artist.getArtistName());
            preparedStatement.setString(2, artist.getEmail());
            preparedStatement.setString(3, artist.getPassword());
            preparedStatement.setString(4, artist.getBio());
            preparedStatement.setString(5, artist.getGenre());
            preparedStatement.setString(6, artist.getInstagram());
            preparedStatement.setString(7, artist.getTwitter());
            int rowUpdated = preparedStatement.executeUpdate();

            isInserted = rowUpdated > 0;


        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error inserting artist: " + e.getMessage());
        }

        preparedStatement.close();
        conn.close();

        return isInserted;
    }

    public Artist fetchArtist(String email, String password) throws SQLException {

        Artist artist = null;

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            preparedStatement = conn.prepareStatement(SELECT_ARTIST_SQL);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                artist = new Artist();
                artist.setArtistId(resultSet.getInt("artist_id"));
                artist.setArtistName(resultSet.getString("artist_name"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error founding artist: " + e.getMessage());
        }

        resultSet.close();
        preparedStatement.close();
        conn.close();

        return artist;

    }

    public boolean uploadSong(Song song, Date sqlDate) throws SQLException {
        boolean isInserted = false;

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            preparedStatement = conn.prepareStatement(INSERT_SONG);
            preparedStatement.setInt(1, song.getArtistId());
            preparedStatement.setInt(2, song.getAlbumId());
            preparedStatement.setString(3, song.getTitle());
            preparedStatement.setString(4, song.getGenre());
            preparedStatement.setDouble(5, song.getDuration());
            preparedStatement.setDate(6, sqlDate);

            int rowUpdated = preparedStatement.executeUpdate();

            isInserted = rowUpdated > 0;


        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error inserting song: " + e.getMessage());
        }

        preparedStatement.close();
        conn.close();

        return isInserted;
    }

    public boolean createAlbum(Album album, Date sqlDate) throws SQLException {
        boolean isInserted = false;

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            preparedStatement = conn.prepareStatement(INSERT_ALBUM);
            preparedStatement.setInt(1, album.getArtistId());
            preparedStatement.setString(2, album.getAlbumName());
            preparedStatement.setDate(3, sqlDate);
            preparedStatement.setString(4, album.getGenre());

            int rowUpdated = preparedStatement.executeUpdate();

            isInserted = rowUpdated > 0;


        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error creating album: " + e.getMessage());
        }

        preparedStatement.close();
        conn.close();

        return isInserted;
    }

    public List<Song> getAllSongs(int artistId) throws SQLException {

        List<Song> songs = new ArrayList<>();

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(SELECT_ALL_SONGS_BY_ARTIST_ID);
            preparedStatement.setInt(1, artistId);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Song song = new Song();
                song.setSongId(resultSet.getInt("song_id"));
                song.setTitle(resultSet.getString("title"));
                song.setGenre(resultSet.getString("genre"));
                song.setReleaseDate(resultSet.getDate("release_date").toString());

                songs.add(song);
            }


        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error finding songs: " + e.getMessage());
        }

        resultSet.close();
        preparedStatement.close();
        conn.close();
        return songs;
    }


    public List<Album> getAllAlbums(int artistId) throws SQLException {
        List<Album> albums = new ArrayList<>();

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(SELECT_ALL_ALBUMS_BY_ARTIST_ID);
            preparedStatement.setInt(1, artistId);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Album album = new Album();
                album.setAlbumId(resultSet.getInt("album_id"));
                album.setAlbumName(resultSet.getString("album_name"));
                album.setGenre(resultSet.getString("genre"));
                album.setReleaseDate(resultSet.getDate("release_date").toString());

                albums.add(album);
            }


        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error finding albums: " + e.getMessage());
        }

        resultSet.close();
        preparedStatement.close();
        conn.close();
        return albums;
    }

    public boolean updateSongDetails(Song song) throws SQLException {

        boolean isUpdated = false;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            preparedStatement = conn.prepareStatement(UPDATE_SONG_DETAILS);
            preparedStatement.setInt(1, song.getAlbumId());
            preparedStatement.setString(2, song.getTitle());
            preparedStatement.setString(3, song.getGenre());
            preparedStatement.setInt(4, song.getSongId());

            int rowUpdated = preparedStatement.executeUpdate();

            isUpdated = rowUpdated > 0;


        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error Updating song: " + e.getMessage());
        }

        preparedStatement.close();
        conn.close();

        return isUpdated;
    }

    public boolean uploadAlbumDetails(Album album) throws SQLException {
        boolean isUpdated = false;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(UPDATE_ALBUM_DETAILS);
            preparedStatement.setString(1, album.getAlbumName());
            preparedStatement.setString(2, album.getGenre());
            preparedStatement.setInt(3, album.getAlbumId());

            int rowUpdated = preparedStatement.executeUpdate();
            isUpdated = rowUpdated > 0;

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error Updating album: " + e.getMessage());
        }

        preparedStatement.close();
        conn.close();

        return isUpdated;
    }

    public boolean deleteSong(int songId) throws SQLException {
        boolean isDeleted = false;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(DELETE_SONG);
            preparedStatement.setInt(1, songId);

            int rowUpdated = preparedStatement.executeUpdate();

            isDeleted = rowUpdated > 0;

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error deleting song: " + e.getMessage());

        }

        preparedStatement.close();
        conn.close();

        return isDeleted;
    }

    public boolean deleteAlbum(int albumId) throws SQLException {

        boolean isDeleted = false;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(DELETE_ALBUM);
            preparedStatement.setInt(1, albumId);

            int rowUpdated = preparedStatement.executeUpdate();
            isDeleted = rowUpdated > 0;


        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error deleting album: " + e.getMessage());

        }

        preparedStatement.close();
        conn.close();

        return isDeleted;
    }

    public List<Song> viewSongStats(int artistId) throws SQLException {
        List<Song> songs = new ArrayList<>();

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(SELECT_ALL_SONGS_BY_ARTIST_ID + " ORDER BY play_count DESC");
            preparedStatement.setInt(1, artistId);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Song song = new Song();
                song.setTitle(resultSet.getString("title"));
                song.setPlayCount(resultSet.getInt("play_count"));
                song.setReleaseDate(resultSet.getDate("release_date").toString());
                songs.add(song);
            }


        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error finding songs: " + e.getMessage());
        }

        resultSet.close();
        preparedStatement.close();
        conn.close();

        return songs;
    }

    public List<Song> searchAll(String keyword) throws SQLException {
        List<Song> songs = new ArrayList<>();
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(SEARCH_ALL);
            preparedStatement.setString(1, keyword);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Song song = new Song();
                song.setSongId(resultSet.getInt("song_id"));
                song.setTitle(resultSet.getString("title"));
                songs.add(song);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error finding songs: " + e.getMessage());
        }

        resultSet.close();
        preparedStatement.close();
        conn.close();

        return songs;

    }

    public List<Song> searchSongByGenre(String genre) throws SQLException {
        List<Song> songs = new ArrayList<>();
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(SEARCH_SONG_BY_GENRE);
            preparedStatement.setString(1, genre);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Song song = new Song();
                song.setSongId(resultSet.getInt("song_id"));
                song.setTitle(resultSet.getString("title"));
                songs.add(song);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error finding songs: " + e.getMessage());
        }

        resultSet.close();
        preparedStatement.close();
        conn.close();

        return songs;
    }

    public List<Song> searchSongByArtistName(String artistName) throws SQLException {
        List<Song> songs = new ArrayList<>();
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(SEARCH_SONG_BY_ARTIST_NAME);
            preparedStatement.setString(1, artistName);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Song song = new Song();
                song.setSongId(resultSet.getInt("song_id"));
                song.setTitle(resultSet.getString("title"));
                songs.add(song);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error finding songs: " + e.getMessage());
        }

        resultSet.close();
        preparedStatement.close();
        conn.close();

        return songs;
    }

    public List<Song> searchSongByAlbumName(String albumName) throws SQLException {
        List<Song> songs = new ArrayList<>();
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(SEARCH_SONG_BY_ALBUM_NAME);
            preparedStatement.setString(1, albumName);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Song song = new Song();
                song.setSongId(resultSet.getInt("song_id"));
                song.setTitle(resultSet.getString("title"));
                songs.add(song);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error finding songs: " + e.getMessage());
        }

        resultSet.close();
        preparedStatement.close();
        conn.close();

        return songs;
    }

    public boolean markSongFavourite(int userId, int songId) throws SQLException {
        boolean isMarked = false;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(MARK_SONG_FAVOURITE);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, songId);

            int rowUpdated = preparedStatement.executeUpdate();
            isMarked = rowUpdated > 0;

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error marking song favourite: Song already added to favorite");
        }

        preparedStatement.close();
        conn.close();

        return isMarked;
    }

    public List<Song> viewFavouriteSongs(int userId) throws SQLException {
        List<Song> songs = new ArrayList<>();
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(VIEW_FAVOURITE);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Song song = new Song();
                song.setSongId(resultSet.getInt("song_id"));
                song.setTitle(resultSet.getString("title"));
                songs.add(song);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error view favourite songs : " + e.getMessage());
        }

        resultSet.close();
        preparedStatement.close();
        conn.close();

        return songs;
    }

    public boolean createPlaylist(Playlist playlist) throws SQLException {
        boolean isCreated = false;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(CREATE_PLAYLIST);
            preparedStatement.setInt(1, playlist.getUserId());
            preparedStatement.setString(2, playlist.getPlaylistName());
            preparedStatement.setString(3, playlist.getDescription());
            preparedStatement.setBoolean(4, playlist.isPublic());

            int rowUpdated = preparedStatement.executeUpdate();

            isCreated = rowUpdated > 0;

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error creating playlist : " + e.getMessage());
        }

        preparedStatement.close();
        conn.close();

        return isCreated;
    }

    public boolean addSongToPlaylist(int playlistId, int songId) throws SQLException {
        boolean isAdded = false;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(ADD_SONG_TO_PLAYLIST);
            preparedStatement.setInt(1, playlistId);
            preparedStatement.setInt(2, songId);

            int rowUpdated = preparedStatement.executeUpdate();
            isAdded = rowUpdated > 0;

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error adding song to playlist : Song already added to playlist");
        } finally {
            preparedStatement.close();
            conn.close();
        }

        return isAdded;

    }

    public boolean removeSongFromPlaylist(int playlistId, int songId) throws SQLException {
        boolean isDeleted = false;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(REMOVE_SONG_FROM_PLAYLIST);
            preparedStatement.setInt(1, playlistId);
            preparedStatement.setInt(2, songId);

            int rowUpdated = preparedStatement.executeUpdate();
            isDeleted = rowUpdated > 0;

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error adding song to playlist : " + e.getMessage());
        } finally {
            preparedStatement.close();
            conn.close();
        }

        return isDeleted;
    }

    public List<Playlist> viewAllPlaylistByUser(int userId) throws SQLException {
        List<Playlist> playlists = new ArrayList<>();
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(VIEW_ALL_PLAYLIST);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Playlist playlist = new Playlist();
                playlist.setPlaylistId(resultSet.getInt("playlist_id"));
                playlist.setPlaylistName(resultSet.getString("playlist_name"));
                playlist.setDescription(resultSet.getString("description"));
                playlist.setPublic(resultSet.getBoolean("is_public"));
                playlists.add(playlist);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error finding playlist : " + e.getMessage());
        } finally {
            preparedStatement.close();
            conn.close();
        }

        return playlists;
    }

    public List<Playlist> viewAllPublicPlaylistByOtherUser(int userId) throws SQLException {
        List<Playlist> playlists = new ArrayList<>();
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(VIEW_ALL_PUBLIC_PLAYLIST);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Playlist playlist = new Playlist();
                playlist.setPlaylistId(resultSet.getInt("playlist_id"));
                playlist.setPlaylistName(resultSet.getString("playlist_name"));
                playlist.setDescription(resultSet.getString("description"));
                playlist.setPublic(resultSet.getBoolean("is_public"));
                playlists.add(playlist);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error finding playlist : " + e.getMessage());
        } finally {
            preparedStatement.close();
            conn.close();
        }

        return playlists;
    }

    public boolean updatePlaylistDetails(Playlist playlist, int playlistId) throws SQLException {
        boolean isUpdated = false;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(UPDATE_PLAYLIST);

            preparedStatement.setString(1, playlist.getPlaylistName());
            preparedStatement.setString(2, playlist.getDescription());
            preparedStatement.setBoolean(3, playlist.isPublic());
            preparedStatement.setInt(4, playlist.getUserId());
            preparedStatement.setInt(5, playlistId);

            int rowUpdated = preparedStatement.executeUpdate();
            System.out.println(rowUpdated);
            isUpdated = rowUpdated > 0;

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error updating playlist : " + e.getMessage());
        }

        preparedStatement.close();
        conn.close();

        return isUpdated;
    }

    public boolean deletePlaylist(int userId, int playlistId) throws SQLException {
        boolean isDeleted = false;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(DELETE_PLAYLIST);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, playlistId);

            int rowUpdated = preparedStatement.executeUpdate();

            isDeleted = rowUpdated > 0;

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error deleting playlist : " + e.getMessage());

        } finally {
            preparedStatement.close();
            conn.close();
        }

        return isDeleted;
    }

    public Song getSongById(int songId) throws SQLException {
        Song song = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(GET_SONG_NAME_BY_ID);
            preparedStatement.setInt(1, songId);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                song = new Song();
                song.setTitle(resultSet.getString("title"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error finding song : " + e.getMessage());

        } finally {
            resultSet.close();
            preparedStatement.close();
            conn.close();
        }

        return song;
    }

    public boolean addSongToPlayHistory(PlayHistory playHistory) throws SQLException {
        boolean isAdded = false;

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(INSERT_SONG_INTO_HISTORY);
            preparedStatement.setInt(1, playHistory.getUserId());
            preparedStatement.setInt(2, playHistory.getSongId());

            int rowUpdated = preparedStatement.executeUpdate();
            isAdded = rowUpdated > 0;

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error inserting song into listening history: " + e.getMessage());

        } finally {
            preparedStatement.close();
            conn.close();
        }
        return isAdded;
    }

    public List<Song> viewRecentPlayedSongs(int userId) throws SQLException {
        List<Song> songs = new ArrayList<>();
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(VIEW_RECENT_PLAYED);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Song song = new Song();
                song.setTitle(resultSet.getString("title"));
                song.setSongId(resultSet.getInt("song_id"));
                songs.add(song);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error viewing recent played: " + e.getMessage());

        } finally {
            resultSet.close();
            preparedStatement.close();
            conn.close();
        }

        return songs;

    }

    public List<Song> viewListeningHistory(int userId) throws SQLException {
        List<Song> songs = new ArrayList<>();
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(VIEW_LISTENING_HISTORY);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Song song = new Song();
                song.setTitle(resultSet.getString("title"));
                song.setSongId(resultSet.getInt("song_id"));
                songs.add(song);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error viewing listening history: " + e.getMessage());

        } finally {
            resultSet.close();
            preparedStatement.close();
            conn.close();
        }

        return songs;

    }

    public List<Song> viewAllSongs() throws SQLException {
        List<Song> songs = new ArrayList<>();
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(VIEW_ALL_SONGS);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Song song = new Song();
                song.setTitle(resultSet.getString("title"));
                song.setSongId(resultSet.getInt("song_id"));
                songs.add(song);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error viewing songs: " + e.getMessage());
        } finally {
            resultSet.close();
            preparedStatement.close();
            conn.close();
        }

        return songs;
    }

    public List<Song> viewPlaylistSong(int userId, int playlistId) throws SQLException {
        List<Song> songs = new ArrayList<>();

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(VIEW_PLAYLIST_SONGS);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, playlistId);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Song song = new Song();
                song.setSongId(resultSet.getInt("song_id"));
                song.setTitle(resultSet.getString("title"));
                songs.add(song);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error viewing playlist songs: " + e.getMessage());

        } finally {
            resultSet.close();
            preparedStatement.close();
            conn.close();
        }

        return songs;
    }

    public List<FavoriteInfo> viewUserWhoMarkedMySongFavourite(int artistId) throws SQLException {
        List<FavoriteInfo> favoriteInfoList = new ArrayList<>();
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(VIEW_USER);
            preparedStatement.setInt(1, artistId);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                FavoriteInfo favoriteInfo = new FavoriteInfo(resultSet.getInt("user_id"), resultSet.getString("user_name"), resultSet.getString("song_name"), resultSet.getTimestamp("favorited_at"));
                favoriteInfoList.add(favoriteInfo);
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            resultSet.close();
            preparedStatement.close();
            conn.close();
        }

        return favoriteInfoList;
    }

    public User getUserByEmail(String email) throws SQLException {
        User user = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(GET_USER_BY_EMAIL);
            preparedStatement.setString(1, email);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user = new User();
                user.setSecurityAnswer(resultSet.getString("security_answer"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("No user found: " + e.getMessage());
        } finally {
            resultSet.close();
            preparedStatement.close();
            conn.close();
        }

        return user;
    }

    public boolean forgotPassword(String email, String password) throws SQLException {
        boolean isUpdated = false;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            preparedStatement = conn.prepareStatement(FORGOT_PASSWORD);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, email);

            int rowUpdated = preparedStatement.executeUpdate();
            isUpdated = rowUpdated > 0;


        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            preparedStatement.close();
            conn.close();
        }

        return isUpdated;
    }
}
