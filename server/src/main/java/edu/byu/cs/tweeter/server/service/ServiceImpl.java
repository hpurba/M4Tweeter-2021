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

import java.sql.Timestamp;
import java.util.Base64;

import edu.byu.cs.tweeter.server.dao.AuthorizationsDAO;

/**
 * This has basic services that can be used in the other services
 * Includes:
 *  - hashPassword
 *  - uploadImage
 *  - validateAToken
 */
public class ServiceImpl {

    // expirationDuration is in milliseconds.
    // Use this link to do conversion from minutes to milliseconds:
    //      https://www.google.com/search?q=milliseconds+to+minutes&oq=milliseconds+to+minutes&aqs=chrome..69i57j0i10j0i10i433j0i10l6j0i10i433.15592j0j7&sourceid=chrome&ie=UTF-8
    // public final long expirationDuration =      5000;  // 5 seconds
    // public final long expirationDuration =     20000;  // 20 seconds
//    public final long expirationDuration =       300000;  // 5 minutes
    // public final long expirationDuration =   3600000;  // 1 hour
     public final long expirationDuration =  86400000;  // 1 day 8.64e+7
    // public final long expirationDuration = 604800000;  // 1 week 6.048e+8

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

    /**
     * Uploads an image to S3 Bucket.
     * Image goes in as a byteArray, but is converted to ByteArrayInputStream.
     * Uploads to bucket: tweeteruserprofileimages
     * Content type: PNG
     * Returns generated url based on userAlias
     *
     * @param userAlias         String userAlias
     * @param imageByteArray    imageByteArray as base64 String (later decoded)
     * @return URL String generated based on userAlias.
     * @throws Exception
     */
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

    /**
     * Checks the authToken to see if it is valid.
     *
     * @param authToken
     */
    public void checkAuthorizationToken(String authToken) {
        // authToken is null
        if (authToken == null) {
            throw new RuntimeException("[BadRequest400] 400");
        }

        // Use AuthorizationsDAO to validateToken in table
        AuthorizationsDAO authorizationsDAO = new AuthorizationsDAO();
        long response = authorizationsDAO.validateToken(authToken);

        // authToken was not found
        if (response == -1) {
            throw new RuntimeException("[BadRequest400] 400 : authToken was not found.");
        }

        // authtoken expired
        long curr_time = new Timestamp(System.currentTimeMillis()).getTime();
        if (curr_time - response > expirationDuration) {
            throw new RuntimeException("[BadRequest400] 400 : authToken is no longer valid");
        }
    }
}