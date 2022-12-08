package com.visionary.crofting.util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordHasher {
    public static String hash(String passwordToHash){
        Argon2 argon2=Argon2Factory.create();
        return argon2.hash(22,65536,1,passwordToHash);
    }
    public static boolean verify(String hash,String password){
        Argon2 argon2=Argon2Factory.create();
        return argon2.verify(hash,password);
    }



}
