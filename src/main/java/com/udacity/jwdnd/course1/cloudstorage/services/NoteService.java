package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }
    public Note[] getNoteList(Integer userId) {
        return noteMapper.getNoteList(userId);
    }
    public Note getNoteById(Integer userId, Integer noteId) {
        return noteMapper.getNoteById(userId, noteId);
    }
    public int createNote(Note note){
        return noteMapper.insert(note);
    }
    public int updateNote(Note note) {
        return noteMapper.update(note);
    }
    public void deleteNoteById(Integer userId, Integer noteId) {
        noteMapper.delete(userId, noteId);
    }
}
