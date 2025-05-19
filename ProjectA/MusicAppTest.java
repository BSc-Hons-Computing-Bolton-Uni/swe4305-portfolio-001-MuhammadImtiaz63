package ProjectA;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MusicAppTest {

    private Song song1;
    private Song song2;
    private Song song3;
    private ArrayList<Song> songList;

    @BeforeEach
    void setUp() {
        song1 = new Song("Artist A", "Song A", 10);
        song2 = new Song("Artist B", "Song B", 5);
        song3 = new Song("Artist C", "Song C", 20);
        songList = new ArrayList<>();
        songList.add(song1);
        songList.add(song2);
        songList.add(song3);
    }

    // Song class tests
    // Test Song constructor and getters
    @Test
    void testSongConstructorAndGetters() {
        Song song = new Song("Taylor Swift", "Love Story", 100);
        assertEquals("Taylor Swift", song.getArtist());
        assertEquals("Love Story", song.getTitle());
        assertEquals(100, song.getPlayCount());
    }

    // Test increasing play count of a song
    @Test
    void testIncreasePlayCount() {
        song1.increasePlayCount();
        assertEquals(11, song1.getPlayCount());
    }

    // Test Song toString method
    @Test
    void testToString() {
        String expected = "Song A by Artist A | Plays: 10";
        assertEquals(expected, song1.toString());
    }

    // Test removing a song by title when it exists
    @Test
    void testRemoveSongByTitleExists() {
        boolean removed = songList.removeIf(song -> song.getTitle().equalsIgnoreCase("Song B"));
        assertTrue(removed);
        assertEquals(2, songList.size());
    }

    // MusicApp class tests
    // Test removing a song by title when it does not exist
    @Test
    void testRemoveSongByTitleNotExists() {
        boolean removed = songList.removeIf(song -> song.getTitle().equalsIgnoreCase("Nonexistent"));
        assertFalse(removed);
        assertEquals(3, songList.size());
    }

    // Test filtering songs by minimum play count
    @Test
    void testFilterSongsByPlayCount() {
        int minPlays = 10;
        ArrayList<Song> filtered = new ArrayList<>();
        for (Song song : songList) {
            if (song.getPlayCount() >= minPlays) {
                filtered.add(song);
            }
        }
        assertEquals(2, filtered.size());
        assertTrue(filtered.contains(song1));
        assertTrue(filtered.contains(song3));
    }

    // Test behavior of an empty song list
    @Test
    void testEmptySongList() {
        ArrayList<Song> emptyList = new ArrayList<>();
        assertTrue(emptyList.isEmpty());
    }
}
