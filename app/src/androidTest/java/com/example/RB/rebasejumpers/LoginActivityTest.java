package com.example.RB.rebasejumpers;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by will on 7/16/17.
 */
public class LoginActivityTest {

    /**
     * On create.
     *
     * @throws Exception the exception
     */
    @Test
    public void onCreate() throws Exception {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        assertNotNull(mAuth);
    }
}