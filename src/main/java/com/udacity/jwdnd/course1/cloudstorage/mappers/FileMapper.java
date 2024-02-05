package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {
    @Select("SELECT filename FROM FILES WHERE userid = #{userId}")
    String[] getFileNameListByUserId(Integer userId);
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);
    @Select("SELECT * FROM FILES WHERE userid = #{userId} AND filename = #{fileName}")
    File getFile(String fileName, Integer userId);
    @Delete("DELETE FROM FILES WHERE userid = #{userId} AND filename = #{fileName}")
    int delete(String fileName, Integer userId);
}

