package com.ace.generator;


import com.ace.utilities.entity.Users;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    public static List<Users> generateUsers() {
        List<Users> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i < 30) {
                Users user = new Users();
                user.setUserId((long) get1Int());
                user.setUsername("Peter");
                user.setEmail(get2Int() + "peter@domain.com");
                users.add(user);
            } else if (i >= 30 && i < 85) {
                Users user = new Users();
                user.setUserId((long) get1Int());
                user.setUsername("Lily");
                user.setEmail(get2Int() + "lily@domain.com");
                users.add(user);
            } else {
                Users user = new Users();
                user.setUserId((long) get1Int());
                user.setUsername("Garlam");
                user.setEmail(get2Int() + "garlam@domain.com");
                users.add(user);
            }
        }
        return users;
    }


    private static int get1Int() {
        SecureRandom random = new SecureRandom();
        int i = random.nextInt(9) + 1;
        return i;
    }

    private static int get2Int() {
        SecureRandom random = new SecureRandom();
        int i = random.nextInt(90) + 10;
        return i;
    }

    private static int get3Int() {
        SecureRandom random = new SecureRandom();
        int i = random.nextInt(900) + 100;
        return i;
    }

    private static int get4Int() {
        SecureRandom random = new SecureRandom();
        int i = random.nextInt(9000) + 1000;
        return i;
    }

    private static int get5Int() {
        SecureRandom random = new SecureRandom();
        int i = random.nextInt(90000) + 10000;
        return i;
    }

    private static int get6Int() {
        SecureRandom random = new SecureRandom();
        int i = random.nextInt(900000) + 100000;
        return i;
    }
}
