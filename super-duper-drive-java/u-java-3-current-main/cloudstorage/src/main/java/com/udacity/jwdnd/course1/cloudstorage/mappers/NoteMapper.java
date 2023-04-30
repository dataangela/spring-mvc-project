package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userId=#{userId}")
    List<Note> getAllUserIdNotes(Integer userId);

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription,userId) VALUES(#{noteTitle},#{noteDescription},#{userId})")
    Integer createNote(String noteTitle,String noteDescription,Integer userId);

    @Update("UPDATE NOTES SET noteTitle = #{noteTitle},noteDescription = #{noteDescription} WHERE noteId=#{noteId}")
    void updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId=#{noteId}")
    void deleteNote(Long noteId);

}
