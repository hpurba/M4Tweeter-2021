package edu.byu.cs.tweeter.server.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.util.Base64;

/**
 * This has basic services that can be used in the other services
 * Includes:
 *  - hashPassword
 *  - uploadImage
 *  - validateAToken
 */
public class ServiceImpl {

    /**
     * Hashes Password.
     * Original code for this is from: https://gist.github.com/NovaMachina/2e5c72484cffec0a8ea763a820ba193c
     * @param passwordToHash
     * @return
     */
    public static String hashPassword(String passwordToHash) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("500");
        }
    }

    public String uploadImageToS3(String userAlias, String imageByteArray) throws Exception {
        byte[] decodedImageByteArray = Base64.getDecoder().decode(imageByteArray);

        AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .build();
        String file_name = userAlias + ".png";
        String bucket_name = "tweeteruserprofileimages";

        InputStream stream = new ByteArrayInputStream(decodedImageByteArray);
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(decodedImageByteArray.length);
        meta.setContentType("image/png");

        try{
            s3.putObject(new PutObjectRequest(bucket_name, file_name, stream, meta)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            stream.close();
        }
        catch (Exception e) {
            throw new Exception("Could not upload" + e.toString());
        }

        return "https://" + bucket_name + ".s3-us-west-2.amazonaws.com/" + file_name;
    }
}