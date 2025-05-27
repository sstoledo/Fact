package Hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

public class SHA2Hasher {
    public static String hash(String input, String tipo) {
        try {
            MessageDigest md = MessageDigest.getInstance(tipo);
            // Forzar uso de UTF-8
            byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        System.out.println(SHA2Hasher.hash("1nf0rm4t1c4" + "1234", "SHA-512"));
        
    }
}
