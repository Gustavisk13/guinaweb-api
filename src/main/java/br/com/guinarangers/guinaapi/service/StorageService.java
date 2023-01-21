package br.com.guinarangers.guinaapi.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Tag;

import br.com.guinarangers.guinaapi.amazon.AmazonClient;

@Component
public class StorageService {

    @Value("${aws.bucket-name}")
    private String bucketName;

    @Autowired
    private AmazonClient amazonClient;


    public void uploadFile(String base64, String fileName) {

        try {
            byte[] decodedBytes = Base64.decodeBase64(base64.getBytes());

            InputStream inputStream = new ByteArrayInputStream(decodedBytes);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/jpeg");

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, metadata);

            List<Tag> tags = new ArrayList<Tag>();
            tags.add(new Tag("public", "yes"));

            putObjectRequest.setTagging(new ObjectTagging(tags));

            
            amazonClient.s3client.putObject(putObjectRequest);
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        }

    }

}
