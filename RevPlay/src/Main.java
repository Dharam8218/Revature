import com.revplay.controller.ArtistController;
import com.revplay.controller.UserController;
import com.revplay.model.Artist;
import com.revplay.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static boolean isLoggedIn = false;
    static int currentUserId;
    static int currentArtistId;
    static List<Integer> currentSongIds;
    static List<Integer> currentPlaylistIds;
    static List<Integer> currentAlbumIds;
    static List<Integer> currentPlaylistSongIds;
    static int currentIndex;
    static int playlistId;

    private static final Logger logger =
            LogManager.getLogger(Main.class);


    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        System.setProperty("log4j.configurationFile", "log4j2.xml");

        UserController userController = new UserController();
        ArtistController artistController = new ArtistController();
        logger.info("RevPlay started successfully");
        while (true) {
            System.out.println("WELCOME TO REVPLAY");
            System.out.println("1. Register as User");
            System.out.println("2. Login as User");
            System.out.println("3. Register as Artist");
            System.out.println("4. Login as Artist");
            System.out.println("0. Exit");

            System.out.println("Enter option no: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    userController.register();
                    break;

                case 2:
                    User user = userController.login();
                    if (user != null) {
                        isLoggedIn = true;
                        currentUserId = user.getUserId();
                        System.out.println("Welcome! " + user.getName());
                        userMenu(userController);
                    } else {
                        System.out.println("Bad Credentials! Invalid Email or Password.");
                    }
                    break;
                case 3:
                    artistController.register();
                    break;
                case 4:
                    Artist artist = artistController.login();
                    if (artist != null) {
                        isLoggedIn = true;
                        currentArtistId = artist.getArtistId();
                        artistMenu(artistController);
                    } else {
                        System.out.println("Bad Credentials! Invalid Email or Password.");
                    }
                    break;
                case 0:
                    System.out.println("Thank you for using RevPlay!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void userMenu(UserController userController) throws SQLException {

        while (isLoggedIn) {
            System.out.println("USER DASHBOARD");
            System.out.println("1. Browse Songs by Genre");
            System.out.println("2. Browse Songs by Artist Name");
            System.out.println("3. Browse Songs by Album");
            System.out.println("4. Search Songs");
            System.out.println("5. Play Song");
            System.out.println("6. View Favorites");
            System.out.println("7. Playlist Manager");
            System.out.println("8. Recently Played");
            System.out.println("9. Listening History");
            System.out.println("0. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();
            currentSongIds = userController.getCurrentSongIds();
            switch (choice) {
                case 1:
                    userController.browseSongByGenre();
                    if (currentSongIds.isEmpty()) {
                        System.out.println("Enter 0 to go back");
                        scanner.nextInt();
                        break;
                    }
                    playSongFlow(userController);
                    break;
                case 2:
                    userController.browseSongByArtist();
                    if (currentSongIds.isEmpty()) {
                        System.out.println("Enter 0 to go back");
                        scanner.nextInt();
                        break;
                    }
                    playSongFlow(userController);
                    break;
                case 3:
                    userController.browseSongByAlbum();
                    if (currentSongIds.isEmpty()) {
                        System.out.println("Enter 0 to go back");
                        scanner.nextInt();
                        break;
                    }
                    playSongFlow(userController);
                    break;
                case 4:
                    userController.searchSongsByKeyword();
                    if (currentSongIds.isEmpty()) {
                        System.out.println("Enter 0 to go back");
                        scanner.nextInt();
                        break;
                    }
                    playSongFlow(userController);
                    break;
                case 5:
                    userController.viewAllSongs();
                    if (currentSongIds.isEmpty()) {
                        System.out.println("Enter 0 to go back");
                        scanner.nextInt();
                        break;
                    }
                    playSongFlow(userController);
                    break;

                case 6:
                    userController.viewFavouriteSong(currentUserId);
                    if (currentSongIds.isEmpty()) {
                        System.out.println("Enter 0 to go back");
                        scanner.nextInt();
                        break;
                    }
                    playSongFlow(userController);
                    break;

                case 7:
                    playlistMenu(userController);
                    break;

                case 8:
                    userController.viewRecentPlayed(currentUserId);
                    System.out.println("Go Back!, Enter 0");
                    boolean flag = false;
                    while (!flag) {
                        int input = scanner.nextInt();
                        if (input == 0) {
                            flag = true;
                        } else {
                            System.out.println("Enter 0 to go back!");
                        }
                    }
                    userMenu(userController);
                    break;

                case 9:
                    userController.viewListeningHistory(currentUserId);
                    System.out.println("Go Back!, Enter 0");
                    flag = false;
                    while (!flag) {
                        int input = scanner.nextInt();
                        if (input == 0) {
                            flag = true;
                        } else {
                            System.out.println("Enter 0 to go back!");
                        }
                    }
                    userMenu(userController);
                    break;

                case 0:
                    isLoggedIn = false;
                    System.out.println("Logged out successfully");
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void playSongFlow(UserController userController) throws SQLException {

        System.out.println("1. Select Song ID to Play:");
        System.out.println("2. Go Back! Enter 0");
        int songId = scanner.nextInt();

        if (songId == 0) {
            userMenu(userController);
            return;
        }

        currentSongIds = userController.getCurrentSongIds();
        currentPlaylistIds = userController.getPlaylistIds();
        currentIndex = currentSongIds.indexOf(songId);

        if (currentIndex == -1) {
            System.out.println("Song not found in queue");
            return;
        }

        userController.playSong(currentUserId, songId);

        boolean isPlaying = true;

        while (isPlaying) {
            System.out.println("PLAYER MENU");
            System.out.println("1. Pause");
            System.out.println("2. Next Song");
            System.out.println("3. Repeat Song/Play");
            System.out.println("4. Add to Favorites");
            System.out.println("5. Add to Playlist");
            System.out.println("0. Exit Player");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Song Paused");
                    break;

                case 2:
                    playNextSong(userController);
                    break;
                case 3:
                    userController.playSong(currentUserId, songId);
                    break;
                case 4:
                    userController.markSongFavourite(currentUserId, songId);
                    break;

                case 5:
                    userController.viewAllPlaylistByUser(currentUserId);
                    if (currentPlaylistIds.isEmpty()) {
                        System.out.println("Enter 0 to go back");
                        scanner.nextInt();
                        break;
                    }
                    userController.addSongToPlaylist(songId);
                    break;

                case 0:
                    isPlaying = false;
                    System.out.println("Player Closed");
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        }

    }

    private static void playNextSong(UserController userController) throws SQLException {
        if (currentIndex < currentSongIds.size() - 1) {
            currentIndex++;
            int nextSongId = currentSongIds.get(currentIndex);
            userController.playSong(currentUserId, nextSongId);
        } else {
            System.out.println("End of song list");
        }
    }

    private static void playlistMenu(UserController userController) throws SQLException {

        boolean inPlaylistMenu = true;

        while (inPlaylistMenu) {

            System.out.println("PLAYLIST MANAGER");
            System.out.println("1. Create Playlist");
            System.out.println("2. View My Playlists");
            System.out.println("3. View Public Playlists");
            System.out.println("4. Update Playlist");
            System.out.println("5. Delete Playlist");
            System.out.println("0. Back");

            int choice = scanner.nextInt();
            scanner.nextLine();
            currentPlaylistIds = userController.getPlaylistIds();

            switch (choice) {
                case 1:
                    userController.createPlaylist(currentUserId);
                    break;
                case 2:
                    userController.viewAllPlaylistByUser(currentUserId);
                    if (currentPlaylistIds.isEmpty()) {
                        System.out.println("Enter 0 to go back");
                        scanner.nextInt();
                        break;
                    }
                    playlistId = userController.viewPlayListSong(currentUserId);
                    playSongFlowFomPlaylist(userController, playlistId);
                    break;
                case 3:
                    userController.viewAllPublicPlaylistByOtherUser(currentUserId);
                    if (currentPlaylistIds.isEmpty()) {
                        System.out.println("Enter 0 to go back");
                        scanner.nextInt();
                        break;
                    }
                    userController.viewPlayListSong(currentUserId);
                    playSongFlow(userController);
                    break;
                case 4:
                    userController.viewAllPlaylistByUser(currentUserId);
                    if (currentPlaylistIds.isEmpty()) {
                        System.out.println("Enter 0 to go back");
                        scanner.nextInt();
                        break;
                    }
                    userController.updatePlaylistDetails(currentUserId);
                    break;
                case 5:
                    userController.viewAllPlaylistByUser(currentUserId);
                    if (currentPlaylistIds.isEmpty()) {
                        System.out.println("Enter 0 to go back");
                        scanner.nextInt();
                        break;
                    }
                    userController.deletePlaylist(currentUserId);
                    break;
                case 0:
                    inPlaylistMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void playSongFlowFomPlaylist(UserController userController, int playlistId) throws SQLException {

        boolean inPlaylist = true;

        while (inPlaylist) {

            System.out.println("PLAYLIST OPTIONS");
            System.out.println("1. Play a Song");
            System.out.println("2. Remove Song from Playlist");
            System.out.println("0. Go Back");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    playSongFlow(userController);
                    break;
                case 2:
                    System.out.print("Enter Song ID to remove: ");
                    int removeSongId = scanner.nextInt();
                    scanner.nextLine();
                    userController.removeSongFromPlaylist(playlistId, removeSongId);
                    break;
                case 0:
                    inPlaylist = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void artistMenu(ArtistController artistController) throws SQLException {

        while (isLoggedIn) {
            System.out.println("ARTIST DASHBOARD");
            System.out.println("1. Upload Song");
            System.out.println("2. Create Album");
            System.out.println("3. Get all Albums");
            System.out.println("4. Update Album Details");
            System.out.println("5. Delete Album");
            System.out.println("6. View My Songs");
            System.out.println("7. Update Song Details");
            System.out.println("8. Delete Song");
            System.out.println("9. View Song Stats");
            System.out.println("10. View User who marked my song favourite");
            System.out.println("0. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();
            currentAlbumIds = artistController.getCurrentAlbumIds();
            currentPlaylistSongIds = artistController.getCurrentPlaylistSongIds();

            switch (choice) {
                case 1:
                    artistController.getAllAlbums(currentArtistId);
                    if (currentAlbumIds.isEmpty()) {
                        System.out.println("Enter 0 to go back");
                        scanner.nextInt();
                        break;
                    }
                    artistController.uploadSong(currentArtistId);
                    break;
                case 2:
                    artistController.createAlbum(currentArtistId);
                    break;
                case 3:
                    artistController.getAllAlbums(currentArtistId);
                    break;
                case 4:
                    artistController.getAllAlbums(currentArtistId);
                    if (currentAlbumIds.isEmpty()) {
                        System.out.println("Enter 0 to go back");
                        scanner.nextInt();
                        break;
                    }
                    artistController.updateAlbumDetails(currentArtistId);
                    break;
                case 5:
                    artistController.getAllAlbums(currentArtistId);
                    if (currentAlbumIds.isEmpty()) {
                        System.out.println("Enter 0 to go back");
                        scanner.nextInt();
                        break;
                    }
                    artistController.deleteAlbum(currentArtistId);
                    break;
                case 6:
                    artistController.getAllSongs(currentArtistId);
                    break;
                case 7:
                    artistController.getAllSongs(currentArtistId);
                    if (currentPlaylistSongIds.isEmpty()) {
                        System.out.println("Enter 0 to go back");
                        scanner.nextInt();
                        break;
                    }
                    artistController.updateSongDetails(currentArtistId);
                    break;
                case 8:
                    artistController.getAllSongs(currentArtistId);
                    if (currentPlaylistSongIds.isEmpty()) {
                        System.out.println("Enter 0 to go back");
                        scanner.nextInt();
                        break;
                    }
                    artistController.deleteSong(currentArtistId);
                    break;
                case 9:
                    artistController.viewSongStats(currentArtistId);
                    break;
                case 10:
                    artistController.viewUserWhoMarkedMySongFavourite(currentArtistId);
                    break;
                case 0:
                    isLoggedIn = false;
                    System.out.println("Logged out successfully");
                    break;
            }
        }
    }
}



