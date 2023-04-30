package com.udacity.jwdnd.course1.cloudstorage.services.NoteService;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;

import java.util.List;

public interface NoteService {
    public List<Note> getAllUserIdNotes(Integer userId);

    public void createNote(Note note,Integer userId);

    public void updateNote(Note note);
    public void deleteNote(Long noteId);

}
