package com.testDate;



import com.util.entity.TestEntity;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    public static List<TestEntity> getTestEntity() {
        List<TestEntity> testEntityList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i < 30) {
                TestEntity testEntity = new TestEntity();
                testEntity.setId(get4Int());
                testEntity.setUserId(get1Int());
                testEntity.setUserName("Peter");
                testEntity.setEmail(get2Int()+"peter@domain.com");
                testEntityList.add(testEntity);
            } else if (i >= 30 && i < 85) {
                TestEntity testEntity = new TestEntity();
                testEntity.setId(get4Int());
                testEntity.setUserId(get1Int());
                testEntity.setUserName("Lily");
                testEntity.setEmail(get2Int()+"lily@domain.com");
                testEntityList.add(testEntity);
            } else {
                TestEntity testEntity = new TestEntity();
                testEntity.setId(get4Int());
                testEntity.setUserId(get1Int());
                testEntity.setUserName("Garlam");
                testEntity.setEmail(get2Int()+"garlam@domain.com");
                testEntityList.add(testEntity);
            }
        }
        return testEntityList;
    }


    public static int get1Int() {
        SecureRandom random = new SecureRandom();
        int i = random.nextInt(9) + 1;
        return i;
    }

    public static int get2Int() {
        SecureRandom random = new SecureRandom();
        int i = random.nextInt(90) + 10;
        return i;
    }

    public static int get3Int() {
        SecureRandom random = new SecureRandom();
        int i = random.nextInt(900) + 100;
        return i;
    }

    public static int get4Int() {
        SecureRandom random = new SecureRandom();
        int i = random.nextInt(9000) + 1000;
        return i;
    }

    public static int get5Int() {
        SecureRandom random = new SecureRandom();
        int i = random.nextInt(90000) + 10000;
        return i;
    }

    public static int get6Int() {
        SecureRandom random = new SecureRandom();
        int i = random.nextInt(900000) + 100000;
        return i;
    }
}
