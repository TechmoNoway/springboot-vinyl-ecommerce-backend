package springbootvinylecommercebackend.util;

import io.jsonwebtoken.io.Encoders;
import java.security.SecureRandom;


public class SecretKeyGenerator {
    public static void main(String[] args) {
        byte[] keyBytes = new byte[32]; // 256 bits (32 bytes)
        new SecureRandom().nextBytes(keyBytes); // Generate a secure random key
        String base64Key = Encoders.BASE64.encode(keyBytes); // Encode to Base64

        System.out.println("Generated Secret Key: " + base64Key);
    }
}

