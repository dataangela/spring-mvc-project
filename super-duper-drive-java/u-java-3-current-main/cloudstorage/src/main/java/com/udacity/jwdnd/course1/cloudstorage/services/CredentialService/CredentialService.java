package com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;


import java.util.List;

public interface CredentialService {
    public List<Credential> getAllUserIdCredentials(Integer userId);

    public void createCredential(Credential credential);

    public Credential getCredential(Long credentialId);

    public void updateCredential(Credential credential);
    public void deleteCredential(Long credentialId);

    public String getPlainCredential(Long credentialId);

}
