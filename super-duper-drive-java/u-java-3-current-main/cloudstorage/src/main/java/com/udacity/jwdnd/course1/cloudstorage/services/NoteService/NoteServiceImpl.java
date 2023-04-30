package com.udacity.jwdnd.course1.cloudstorage.services.NoteService;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class NoteServiceImpl implements NoteService{
    private NoteMapper noteMapper;

    @Override
    public List<Note> getAllUserIdNotes(Integer userId) {
        return noteMapper.getAllUserIdNotes(userId);
    }

    @Override
    public void createNote(Note note,Integer userId) {
        if(note.getNoteId()==null){
            noteMapper.createNote(note.getNoteTitle(),note.getNoteDescription(),userId);
        }
    }

    @Override
    public void updateNote(Note note) {
        if (note.getNoteId() != null) {
            noteMapper.updateNote(note);
        }
    }

    @Override
    public void deleteNote(Long noteId) {
        noteMapper.deleteNote(noteId);
    }
}
