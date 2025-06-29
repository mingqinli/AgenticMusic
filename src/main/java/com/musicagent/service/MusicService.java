package com.musicagent.service;

import com.musicagent.model.Song;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for music management operations.
 */
public interface MusicService {
    
    /**
     * Add a new song to the music library.
     * @param song the song to add
     * @return the added song with generated ID
     */
    Song addSong(Song song);
    
    /**
     * Get a song by its ID.
     * @param id the song ID
     * @return the song if found, empty otherwise
     */
    Optional<Song> getSongById(String id);
    
    /**
     * Get all songs in the library.
     * @return list of all songs
     */
    List<Song> getAllSongs();
    
    /**
     * Search for songs by title.
     * @param title the title to search for
     * @return list of matching songs
     */
    List<Song> searchByTitle(String title);
    
    /**
     * Search for songs by artist.
     * @param artist the artist to search for
     * @return list of matching songs
     */
    List<Song> searchByArtist(String artist);
    
    /**
     * Search for songs by album.
     * @param album the album to search for
     * @return list of matching songs
     */
    List<Song> searchByAlbum(String album);
    
    /**
     * Update an existing song.
     * @param song the song to update
     * @return true if updated successfully, false otherwise
     */
    boolean updateSong(Song song);
    
    /**
     * Delete a song by its ID.
     * @param id the song ID to delete
     * @return true if deleted successfully, false otherwise
     */
    boolean deleteSong(String id);
    
    /**
     * Get the total number of songs in the library.
     * @return the count of songs
     */
    int getSongCount();
} 