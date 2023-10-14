package com.mynotes.controller;

import com.mynotes.dao.NoteDao;
import com.mynotes.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class NoteController {
    @Autowired
    private NoteDao noteDao;

    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/notes")
    public String listNotes(Model model) {
        model.addAttribute("notes", noteDao.getAllNotes());
        return "note-list";
    }

    @GetMapping("/notes/{id}")
    public String viewNote(@PathVariable Long id, Model model) {
        Note note = noteDao.getNoteById(id);
        model.addAttribute("note", note);
        return "note-details";
    }

    @GetMapping("/notes/add")
    public String addNoteForm(Model model) {
        model.addAttribute("note", new Note());
        return "add-note";
    }

    @PostMapping("/notes/add")
    public String addNoteSubmit(@ModelAttribute Note note) {
        noteDao.addNote(note);
        return "redirect:/notes";
    }

    @GetMapping("/notes/edit/{id}")
    public String editNoteForm(@PathVariable Long id, Model model) {
        Note note = noteDao.getNoteById(id);
        model.addAttribute("note", note);
        return "edit-note";
    }

    @PostMapping("/notes/edit/{id}")
    public String editNoteSubmit(@PathVariable Long id, @ModelAttribute Note note) {
        noteDao.updateNote(id, note);
        return "redirect:/notes";
    }

    @GetMapping("/notes/delete/{id}")
    public String deleteNote(@PathVariable Long id) {
        noteDao.deleteNote(id);
        return "redirect:/notes";
    }
}
