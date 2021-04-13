package edu.byu.cs.tweeter.server.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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

        /*
        // Create AmazonS3 object for doing S3 operations
        AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion("us-west-2")
                .build();
        // Filename and bucketname
        String filename = userAlias + ".jpg";  // images are [userAlias].jpg
        String bucketName = "tweeteruserprofileimages";


        // TODO: DECODE the bytearray
        byte[] decodedImageByteArray = Base64.getDecoder().decode(imageByteArray);

        // 3. upload file to the specified S3 bucket using the filename as the S3 key
//        ObjectMetadata meta = new ObjectMetadata();
//        meta.setContentLength(decodedImageByteArray.length);
//        meta.setContentType("image/png");
        s3.putObject(bucketName, filename, new ByteArrayInputStream(decodedImageByteArray), new ObjectMetadata());
//        PutObjectRequest s3Request = new PutObjectRequest(bucketName, filename, new ByteArrayInputStream(decodedImageByteArray), new ObjectMetadata());
//        s3.putObject(s3Request.withCannedAcl(CannedAccessControlList.PublicRead));


//        arn:aws:s3:::tweeteruserprofileimages
        return "https://cs340-tweeter.s3-us-west-2.amazonaws.com/tweeteruserprofileimages/" + filename;


        */



        byte[] decodedImageByteArray = Base64.getDecoder().decode(imageByteArray);

        AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .build();
        String file_name = userAlias + ".jpg";
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


        return "https://cs340-tweeter.s3-us-west-2.amazonaws.com/" + bucket_name + "/" + file_name;
    }
}












//        // https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/examples-s3-objects.html#upload-object
//        Region region = Region.US_WEST_2;
//        s3 = S3Client.builder()
//                .region(region)
//                .build();
//
//        createBucket(s3, bucketName, region);
//
//        PutObjectRequest objectRequest = PutObjectRequest.builder()
//                .bucket(bucketName)
//                .key(key)
//                .build();
//
//        s3.putObject(objectRequest, RequestBody.fromByteBuffer(getRandomByteBuffer(10_000)));







