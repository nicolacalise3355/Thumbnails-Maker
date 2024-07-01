package com.calise.tb_backend.services.utils;

import java.util.UUID;

public class Utils {

    public static String generateFileName(String s){
        return UUID.randomUUID().toString() + "-" + s;
    }

    public static String removeFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return fileName;
        }
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return fileName;
        }
        return fileName.substring(0, lastDotIndex);
    }

}
