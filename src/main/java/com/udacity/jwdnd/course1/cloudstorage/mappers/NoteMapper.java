package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    Note[] getNoteList(Integer userId);
    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note getNoteById(Integer noteId);
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);
    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    void delete(Integer noteId);
}
