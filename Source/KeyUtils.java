import java.io.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class KeyUtils {
    public static SecretKey getSecretKeyFromString(String password){
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] keyBytes = sha256.digest(password.getBytes());

            byte[] aesKeyBytes = new byte[16];
            System.arraycopy(keyBytes, 0, aesKeyBytes, 0, 16);

            return new SecretKeySpec(aesKeyBytes, "AES");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    public static SecretKey retrieve(String alias, char[] pw){
        try {
        KeyStore ks = KeyStore.getInstance("JCEKS");
        try(FileInputStream fis = new FileInputStream("ks.jceks");){
            ks.load(fis, pw);
            
        }
        Key key = ks.getKey(alias, pw);
        SecretKey sk = (SecretKey) key;
        return sk;
        } catch (Exception e) {
        System.out.println("Error: " + e);
        }
        return null;
    }
}
