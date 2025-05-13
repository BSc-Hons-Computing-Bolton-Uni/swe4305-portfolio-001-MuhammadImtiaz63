package ProjectA;
import java.util.ArrayList;
import java.util.Scanner;


// Main class placeholder
public class Main {
}


// Represents a song with artist, title, and play count
class Song {
    private String artist;
    private String title;
    private int playCount;


    // Constructor to create a Song
    public Song(String artist, String title, int playCount) {
        this.artist = artist;
        this.title = title;
        this.playCount = playCount;
    }


    // Getters for artist, title, and play count
    public String getArtist() { return artist; }
    public String getTitle() { return title; }
    public int getPlayCount() { return playCount; }


    // Increases the play count by 1
    public void increasePlayCount() {
        this.playCount++;
    }


    // Returns a readable description of the song
    @Override
    public String toString() {
        return title + " by " + artist + " | Plays: " + playCount;
    }
}


// Main MusicApp class that manages songs
class MusicApp {
    private static ArrayList<Song> songList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);


    // Main method to run the application
    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            switch (getMenuChoice()) {
                case 1 -> addSong();
                case 2 -> removeSong();
                case 3 -> printSongs(songList, "==  All Songs  ==", 0);
                case 4 -> printSongsByPlayCount();
                case 5 -> {
                    System.out.println("Exiting Program");
                    exit = true;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    // Display menu and get user choice
    private static int getMenuChoice() {
        System.out.println("""
              \n= Music Streaming App =
              1. Add a new song
              2. Remove a song
              3. Show all songs
              4. Show songs with plays over a certain number
              5. Exit
              """);
        return getIntInput("Choose an option: ");
    }


    // Adds a new song based on user input
    private static void addSong() {
        songList.add(new Song(
                getStringInput("Enter artist name: "),
                getStringInput("Enter song title: "),
                getIntInput("Enter initial play count: ")
        ));
        System.out.println("Song added successfully!");
    }


    // Removes a song by title
    private static void removeSong() {
        String title = getStringInput("Enter the title of the song to remove: ");
        boolean removed = songList.removeIf(song -> song.getTitle().equalsIgnoreCase(title));
        System.out.println(removed ? "Song '" + title + "' removed." : "Song not found.");
    }


    // Show songs with play count above a minimum number
    private static void printSongsByPlayCount() {
        int minPlays = getIntInput("Enter the minimum number of plays: ");
        printSongs(songList, "== Songs with more than " + minPlays + " plays ==", minPlays);
    }


    // Print songs based on filter
    private static void printSongs(ArrayList<Song> songs, String header, int minPlays) {
        System.out.println("\n" + header);
        boolean found = false;
        for (Song song : songs) {
            if (song.getPlayCount() >= minPlays) {
                System.out.println(song);
                found = true;
            }
        }
        if (!found) {
            System.out.println(minPlays == 0 ? " No songs available." : " No songs with more than " + minPlays + " plays.");
        }
    }


    // Gets an integer input from the user
    private static int getIntInput(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }


    // Gets a string input from the user
    private static String getStringInput(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }
}

