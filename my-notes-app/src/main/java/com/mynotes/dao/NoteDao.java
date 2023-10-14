package com.mynotes.dao;

import com.mynotes.model.Note;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class NoteDao {
    private final List<Note> notes = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong();

    public List<Note> getAllNotes() {
        return notes;
    }

    public Note getNoteById(Long id) {
        for (Note note : notes) {
            if (note.getId().equals(id)) {
                return note;
            }
        }
        return null;
    }

    public void addNote(Note note) {
        note.setId(idCounter.incrementAndGet());
        notes.add(note);
    }

    public void updateNote(Long id, Note updatedNote) {
        for (Note note : notes) {
            if (note.getId().equals(id)) {
                note.setText(updatedNote.getText());
                break;
            }
        }
    }

    public void deleteNote(Long id) {
        notes.removeIf(note -> note.getId().equals(id));
    }
}
