package com.calise.tb_backend.services.utils;

import java.util.UUID;

public class Utils {

    public static String generateFileName(String s){
        return UUID.randomUUID().toString() + "-" + s;
    }

}
