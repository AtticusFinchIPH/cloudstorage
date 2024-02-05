package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
public class FileUploadService {
    private FileMapper fileMapper;
    public FileUploadService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }
    public String[] getFileNameList(Integer userId) {
        String[] fileNames = fileMapper.getFileNameListByUserId(userId);
        return fileNames;
    }
    public boolean isFileNameExisted(Integer userId, String name) {
        String[] fileNames = fileMapper.getFileNameListByUserId(userId);
        for(String filesName : fileNames) {
            if (name.equals(filesName)) {
                return true;
            }
        }
        return false;
    }
    public void saveFile(MultipartFile fileUpload, Integer userId) {
        try {
            String fileName = fileUpload.getOriginalFilename();
            String contentType = fileUpload.getContentType();
            String fileSize = Objects.toString(fileUpload.getSize(), null);
            byte[] fileData = fileUpload.getBytes();
            File newFile = new File(fileName, contentType, fileSize, userId, fileData);
            fileMapper.insert(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public File getFile(String fileName, Integer userId) {
        return fileMapper.getFile(fileName, userId);
    }
    public void deleteFile(String fileName, Integer userId) {
        fileMapper.delete(fileName, userId);
    }
}
