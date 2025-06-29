package com.musicagent.controller;

import com.musicagent.model.Song;
import com.musicagent.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/songs")
public class SongController {
    private final MusicService musicService;

    @Autowired
    public SongController(MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping
    public String listSongs(@RequestParam(value = "search", required = false) String search, Model model) {
        try {
            List<Song> songs = (search == null || search.trim().isEmpty()) ?
                    musicService.getAllSongs() :
                    musicService.searchByTitle(search.trim());
            model.addAttribute("songs", songs);
            model.addAttribute("search", search);
            return "songs";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading songs");
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("song", new Song());
        return "add-song";
    }

    @PostMapping("/add")
    public String addSong(@ModelAttribute Song song, RedirectAttributes redirectAttributes) {
        try {
            if (song.getTitle() == null || song.getTitle().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Title is required");
                return "redirect:/songs/add";
            }
            if (song.getArtist() == null || song.getArtist().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Artist is required");
                return "redirect:/songs/add";
            }
            
            musicService.addSong(song);
            redirectAttributes.addFlashAttribute("message", "Song added successfully!");
            return "redirect:/songs";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding song: " + e.getMessage());
            return "redirect:/songs/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        try {
            Optional<Song> song = musicService.getSongById(id);
            if (song.isPresent()) {
                model.addAttribute("song", song.get());
                return "edit-song";
            } else {
                model.addAttribute("error", "Song not found");
                model.addAttribute("message", "The requested song could not be found");
                return "error";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error loading song");
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/edit/{id}")
    public String editSong(@PathVariable String id, @ModelAttribute Song song, RedirectAttributes redirectAttributes) {
        try {
            if (song.getTitle() == null || song.getTitle().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Title is required");
                return "redirect:/songs/edit/" + id;
            }
            if (song.getArtist() == null || song.getArtist().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Artist is required");
                return "redirect:/songs/edit/" + id;
            }
            
            song.setId(id);
            boolean updated = musicService.updateSong(song);
            if (updated) {
                redirectAttributes.addFlashAttribute("message", "Song updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Song not found or could not be updated");
            }
            return "redirect:/songs";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating song: " + e.getMessage());
            return "redirect:/songs/edit/" + id;
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteSong(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            boolean deleted = musicService.deleteSong(id);
            if (deleted) {
                redirectAttributes.addFlashAttribute("message", "Song deleted successfully!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Song not found or could not be deleted");
            }
            return "redirect:/songs";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting song: " + e.getMessage());
            return "redirect:/songs";
        }
    }
} 