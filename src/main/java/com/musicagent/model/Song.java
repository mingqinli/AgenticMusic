package com.musicagent.model;

/**
 * Represents a music song/track in the Music Agent application.
 */
public class Song {
    private String id;
    private String title;
    private String artist;
    private String album;
    private long duration; // Duration in seconds
    private String genre;
    private String filePath;
    private int year;
    
    public Song() {
        // Default constructor for JSON serialization
    }
    
    public Song(String id, String title, String artist, String album, long duration) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getArtist() {
        return artist;
    }
    
    public void setArtist(String artist) {
        this.artist = artist;
    }
    
    public String getAlbum() {
        return album;
    }
    
    public void setAlbum(String album) {
        this.album = album;
    }
    
    public long getDuration() {
        return duration;
    }
    
    public void setDuration(long duration) {
        this.duration = duration;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public void setDuration(String durationStr) {
        if (durationStr != null && !durationStr.trim().isEmpty()) {
            try {
                this.duration = Long.parseLong(durationStr.trim());
            } catch (NumberFormatException e) {
                // If parsing fails, set a default duration of 3 minutes (180 seconds)
                this.duration = 180;
            }
        } else {
            this.duration = 180; // Default 3 minutes
        }
    }
    
    public String getDurationString() {
        return String.valueOf(duration);
    }
    
    @Override
    public String toString() {
        return String.format("Song{id='%s', title='%s', artist='%s', album='%s', duration=%d seconds}",
                id, title, artist, album, duration);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Song song = (Song) obj;
        return id != null && id.equals(song.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
} 