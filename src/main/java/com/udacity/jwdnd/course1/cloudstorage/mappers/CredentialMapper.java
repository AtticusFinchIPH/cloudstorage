package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    Credential[] getCredentialListByUserId(Integer userId);
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId} AND userid = #{userId}")
    Credential getCredential(Integer userId, Integer credentialId);
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, #{userName}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);
    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{userName}, key = #{key}, password = #{password} WHERE credentialid = #{credentialId} AND userid = #{userId}")
    int update(Credential credential);
    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId} AND userid = #{userId}")
    void delete(Integer userId, Integer credentialId);
}
