package com.guga.lab.bddlab;

import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;

@Service
public class AuthenticationService {

    private String VALID_TOKEN = "dummyValidToken";

    public boolean isValid(String token) {
        return VALID_TOKEN.equals(token);
    }
}
