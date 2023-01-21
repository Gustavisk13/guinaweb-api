package br.com.guinarangers.guinaapi.controller;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.guinarangers.guinaapi.controller.form.artigo.ArtigoForm;
import br.com.guinarangers.guinaapi.service.StorageService;
import br.com.guinarangers.guinaapi.util.Helpers;

@RestController
@RequestMapping("/artigos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ArtigoController {

    Logger logger = LoggerFactory.getLogger(ArtigoController.class);

    @Autowired
    StorageService storageService;

    @PostMapping
    @Transactional
    public ResponseEntity<Object> store(@RequestBody ArtigoForm form) {

        if (form.getAuthor() == null) {
            return ResponseEntity.badRequest().body("Author is required");
        }

        if (Helpers.isBase64(form.getImage())) {
            logger.info("Image is base64");
            storageService.uploadFile(form.getImage(), generateFileName(form.getTitle()));
        } else {
            logger.info("Image is not base64");
        }

        if (Helpers.isBase64(form.getThumbnail())) {
            logger.info("Thumbnail is base64", form.getThumbnail());
        } else {
            logger.info("Thumbnail is not base64");
        }

        return ResponseEntity.ok(form);
    }

    public String generateFileName(String title) {
        final LocalDateTime now = LocalDateTime.now();
        final String fileName = title + "-" + now.getYear() + now.getMonthValue() + now.getDayOfMonth() + now.getHour()
                + now.getMinute() + now.getSecond();
        return fileName;
    }

}
