package com.musicagent.service.impl;

import com.musicagent.model.Song;
import com.musicagent.service.MusicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Implementation of MusicService with in-memory storage.
 */
@Service
public class MusicServiceImpl implements MusicService {
    
    private static final Logger logger = LoggerFactory.getLogger(MusicServiceImpl.class);
    
    private final Map<String, Song> songs = new ConcurrentHashMap<>();
    private final Random random = new Random();
    
    @Override
    public Song addSong(Song song) {
        if (song.getId() == null || song.getId().trim().isEmpty()) {
            song.setId(generateId());
        }
        
        songs.put(song.getId(), song);
        logger.info("Added song: {}", song.getTitle());
        return song;
    }
    
    @Override
    public Optional<Song> getSongById(String id) {
        Song song = songs.get(id);
        return Optional.ofNullable(song);
    }
    
    @Override
    public List<Song> getAllSongs() {
        return new ArrayList<>(songs.values());
    }
    
    @Override
    public List<Song> searchByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return Collections.emptyList();
        }
        
        String searchTerm = title.toLowerCase().trim();
        return songs.values().stream()
                .filter(song -> song.getTitle() != null && 
                        song.getTitle().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Song> searchByArtist(String artist) {
        if (artist == null || artist.trim().isEmpty()) {
            return Collections.emptyList();
        }
        
        String searchTerm = artist.toLowerCase().trim();
        return songs.values().stream()
                .filter(song -> song.getArtist() != null && 
                        song.getArtist().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Song> searchByAlbum(String album) {
        if (album == null || album.trim().isEmpty()) {
            return Collections.emptyList();
        }
        
        String searchTerm = album.toLowerCase().trim();
        return songs.values().stream()
                .filter(song -> song.getAlbum() != null && 
                        song.getAlbum().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean updateSong(Song song) {
        if (song.getId() == null || !songs.containsKey(song.getId())) {
            logger.warn("Cannot update song: song not found with ID {}", song.getId());
            return false;
        }
        
        songs.put(song.getId(), song);
        logger.info("Updated song: {}", song.getTitle());
        return true;
    }
    
    @Override
    public boolean deleteSong(String id) {
        Song removed = songs.remove(id);
        if (removed != null) {
            logger.info("Deleted song: {}", removed.getTitle());
            return true;
        }
        logger.warn("Cannot delete song: song not found with ID {}", id);
        return false;
    }
    
    @Override
    public int getSongCount() {
        return songs.size();
    }
    
    /**
     * Generate a unique ID for a song.
     * @return a unique string ID
     */
    private String generateId() {
        return "song_" + System.currentTimeMillis() + "_" + random.nextInt(1000);
    }
} 