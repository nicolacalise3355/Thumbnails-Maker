package com.calise.tb_backend.services.utils;

import java.util.UUID;

public class Utils {

    /**
     *
     * @param s
     * @return new name file
     * Generate a file name combining name and UUID
     */
    public static String generateFileName(String s){
        return UUID.randomUUID().toString() + "-" + s;
    }

    /**
     * remove file ext
     * @param fileName
     * @return String with ext
     */
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
