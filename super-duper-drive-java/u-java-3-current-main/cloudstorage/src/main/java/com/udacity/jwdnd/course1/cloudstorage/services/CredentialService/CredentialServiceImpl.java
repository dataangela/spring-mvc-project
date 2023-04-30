package com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@AllArgsConstructor
@Service
public class CredentialServiceImpl implements CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;
    @Override
    public List<Credential> getAllUserIdCredentials(Integer userId) {
        return credentialMapper.getAllUserIdCredentials(userId);
    }

    @Override
    public Credential getCredential(Long credentialId){
        Credential credential;
        credential = credentialMapper.findByCredentialId(credentialId);

        return credential;
    }

    @Override
    public void createCredential(Credential credential) {
        String password = credential.getPassword();
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
        if(credential.getCredentialId()==null){
            credentialMapper.createCredential(credential.getUrl(),credential.getUsername(),encryptedPassword,encodedKey,credential.getUserId());
        }
    }

    @Override
    public void updateCredential(Credential credential) {
        String password = credential.getPassword();
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
        if (credential.getCredentialId() != null) {
            credentialMapper.updateCredential(credential);
        }
    }

    @Override
    public void deleteCredential(Long credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }

    @Override
    public String getPlainCredential(Long credentialId){
        return credentialMapper.getPlainPassword(credentialId);
    }
}
