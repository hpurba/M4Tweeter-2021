package edu.byu.cs.tweeter.server.service;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public String uploadImageToS3(String userAlias, String imageByteArray) throws IOException {
        return "url goes here";
    }
//    public String uploadImage(String imageURL, String alias) throws IOException {
//        URL url = new URL(imageURL);
//        InputStream in = new BufferedInputStream(url.openStream());
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        byte[] buf = new byte[1024];
//        int n = 0;
//        while (-1!=(n=in.read(buf))) {
//            out.write(buf, 0, n);
//        }
//        out.close();
//        in.close();
//        byte[] image = out.toByteArray();
//
//        AmazonS3 s3 = AmazonS3ClientBuilder
//                .standard()
//                .build();
//        String file_name = alias + ".jpg";
//        String bucket_name = "cs340-tweeter/profile-pics";
//
//        InputStream stream = new ByteArrayInputStream(image);
//        ObjectMetadata meta = new ObjectMetadata();
//        meta.setContentLength(image.length);
//        meta.setContentType("image/png");
//
//        s3.putObject(new PutObjectRequest(bucket_name, file_name, stream, meta)
//                .withCannedAcl(CannedAccessControlList.PublicRead));
//        stream.close();
//
//        return "https://cs340-tweeter.s3-us-west-2.amazonaws.com/profile-pics/" + file_name;
//    }

}
