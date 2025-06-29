package com.musicagent;

import com.musicagent.model.Song;
import com.musicagent.service.MusicService;
import com.musicagent.service.impl.MusicServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Music Agent functionality.
 */
public class MusicAgentTest {
    
    private MusicService musicService;
    
    @BeforeEach
    void setUp() {
        musicService = new MusicServiceImpl();
    }
    
    @Test
    void testAddAndRetrieveSong() {
        // Create a test song
        Song song = new Song();
        song.setTitle("Bohemian Rhapsody");
        song.setArtist("Queen");
        song.setAlbum("A Night at the Opera");
        song.setDuration("367"); // This will default to 180 seconds
        song.setGenre("Rock");
        song.setYear(1975);
        
        // Add the song
        Song addedSong = musicService.addSong(song);
        
        // Verify the song was added with an ID
        assertNotNull(addedSong.getId());
        assertEquals("Bohemian Rhapsody", addedSong.getTitle());
        
        // This assertion will fail - expecting 367 but getting 180 (default)
        assertEquals(367L, addedSong.getDuration());
        
        // Retrieve the song by ID
        var retrievedSong = musicService.getSongById(addedSong.getId());
        assertTrue(retrievedSong.isPresent());
        assertEquals("Bohemian Rhapsody", retrievedSong.get().getTitle());
    }

    @Test
    void testInvalidDurationinSong() {
        // Create a test song
        Song song = new Song();
        song.setTitle("Bohemian Rhapsody");
        song.setArtist("Queen");
        song.setAlbum("A Night at the Opera");
        song.setDuration("invalid_duration"); // This will default to 180 seconds
        song.setGenre("Rock");
        song.setYear(1975);
        
        // Add the song
        Song addedSong = musicService.addSong(song);
        
        // Verify the song was added with an ID
        assertNotNull(addedSong.getId());
        assertEquals("Bohemian Rhapsody", addedSong.getTitle());
        
        // This assertion will fail - expecting 367 but getting 180 (default)
        assertEquals(180L, addedSong.getDuration());
        
        // Retrieve the song by ID
        var retrievedSong = musicService.getSongById(addedSong.getId());
        assertTrue(retrievedSong.isPresent());
        assertEquals("Bohemian Rhapsody", retrievedSong.get().getTitle());
    }
    
    @Test
    void testSearchByTitle() {
        // Add multiple songs
        Song song1 = createSong("Bohemian Rhapsody", "Queen", "A Night at the Opera");
        Song song2 = createSong("Stairway to Heaven", "Led Zeppelin", "Led Zeppelin IV");
        Song song3 = createSong("Hotel California", "Eagles", "Hotel California");
        
        musicService.addSong(song1);
        musicService.addSong(song2);
        musicService.addSong(song3);
        
        // Search for songs containing "heaven"
        List<Song> results = musicService.searchByTitle("heaven");
        assertEquals(1, results.size());
        assertEquals("Stairway to Heaven", results.get(0).getTitle());
    }
    
    @Test
    void testSearchByArtist() {
        // Add multiple songs
        Song song1 = createSong("Bohemian Rhapsody", "Queen", "A Night at the Opera");
        Song song2 = createSong("Stairway to Heaven", "Led Zeppelin", "Led Zeppelin IV");
        Song song3 = createSong("Hotel California", "Eagles", "Hotel California");
        
        musicService.addSong(song1);
        musicService.addSong(song2);
        musicService.addSong(song3);
        
        // Search for songs by artist containing "queen"
        List<Song> results = musicService.searchByArtist("queen");
        assertEquals(1, results.size());
        assertEquals("Queen", results.get(0).getArtist());
    }
    
    @Test
    void testSearchByAlbum() {
        // Add multiple songs
        Song song1 = createSong("Bohemian Rhapsody", "Queen", "A Night at the Opera");
        Song song2 = createSong("Stairway to Heaven", "Led Zeppelin", "Led Zeppelin IV");
        Song song3 = createSong("Hotel California", "Eagles", "Hotel California");
        
        musicService.addSong(song1);
        musicService.addSong(song2);
        musicService.addSong(song3);
        
        // Search for songs by album containing "hotel"
        List<Song> results = musicService.searchByAlbum("hotel");
        assertEquals(1, results.size());
        assertEquals("Hotel California", results.get(0).getAlbum());
    }
    
    @Test
    void testDeleteSong() {
        // Add a song
        Song song = createSong("Test Song", "Test Artist", "Test Album");
        Song addedSong = musicService.addSong(song);
        
        // Verify it exists
        assertEquals(1, musicService.getSongCount());
        
        // Delete the song
        boolean deleted = musicService.deleteSong(addedSong.getId());
        assertTrue(deleted);
        
        // Verify it's gone
        assertEquals(0, musicService.getSongCount());
        assertTrue(musicService.getSongById(addedSong.getId()).isEmpty());
    }
    
    @Test
    void testUpdateSong() {
        // Add a song
        Song song = createSong("Original Title", "Original Artist", "Original Album");
        Song addedSong = musicService.addSong(song);
        
        // Update the song
        addedSong.setTitle("Updated Title");
        addedSong.setArtist("Updated Artist");
        
        boolean updated = musicService.updateSong(addedSong);
        assertTrue(updated);
        
        // Verify the update
        var retrievedSong = musicService.getSongById(addedSong.getId());
        assertTrue(retrievedSong.isPresent());
        assertEquals("Updated Title", retrievedSong.get().getTitle());
        assertEquals("Updated Artist", retrievedSong.get().getArtist());
    }
    
    /**
     * Helper method to create a test song.
     */
    private Song createSong(String title, String artist, String album) {
        Song song = new Song();
        song.setTitle(title);
        song.setArtist(artist);
        song.setAlbum(album);
        song.setDuration(210); // 3 minutes 30 seconds in seconds
        song.setGenre("Rock");
        song.setYear(2020);
        return song;
    }
} 