package com.calise.tb_backend.controllers.sockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/video")
public class VideoUploadController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/upload")
    public Map<String, String> uploadVideo(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        String fileName = file.getOriginalFilename();

        /**
         * Export everything is service
         * 1) Upload video
         * 2) Add video data to db
         * 3) Run the queue of Extracting tn
         */
        try {
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();

            // send notifications.
            messagingTemplate.convertAndSend("/topic/uploadProgress", "Caricamento completato: " + fileName);

            response.put("message", "Caricamento completato con successo!");
        } catch (IOException e) {
            e.printStackTrace();
            response.put("message", "Errore durante il caricamento del video.");
        }

        return response;
    }
}
