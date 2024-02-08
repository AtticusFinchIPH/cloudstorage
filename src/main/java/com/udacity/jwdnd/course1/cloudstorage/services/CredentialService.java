package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }
    public Credential[] getCredentialList(Integer userId) {
//        Credential[] credentials = Arrays.asList(credentialMapper.getCredentialListByUserId(userId)).stream().map(credential -> {
//            String encryptedPassword = credential.getPassword();
//            String encodedKey = credential.getKey();
//            String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);
//            credential.setPassword(decryptedPassword);
//            return credential;
//        }).toArray(Credential[] ::new);
        Credential[] credentials = credentialMapper.getCredentialListByUserId(userId);
        return credentials;
    }
    public Credential getCredentialById(Integer userId, Integer credentialId) {
        Credential credential = credentialMapper.getCredential(userId, credentialId);
        String encryptedPassword = credential.getPassword();
        String encodedKey = credential.getKey();
        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);
        credential.setPassword(decryptedPassword);
        return credential;
    }
    public int saveCredential(Credential credential, boolean isNewCredential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
        if (isNewCredential) {
            return credentialMapper.insert(credential);
        }
        return credentialMapper.update(credential);
    }
    public void deleteCredentialById(Integer userId, Integer credentialId) {
        credentialMapper.delete(userId, credentialId);
    }
}
