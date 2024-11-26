import java.io.*;
import java.security.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class FileManager {
    public static String passwordString = "password12345678";

    public static void encryptFile(String inputFile, String outputFile, SecretKey secretKey) throws Exception{
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        try (FileInputStream fileInputStream = new FileInputStream(inputFile);
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream, cipher)) {


            byte[] buffer = new byte[1024];
            int bytesRead;
            while((bytesRead = fileInputStream.read(buffer)) != -1){
                cipherOutputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    public static void decryptFile(String inputFile, String outputFile, SecretKey secretKey) throws Exception{
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        try(FileInputStream fileInputStream = new FileInputStream(inputFile);
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        CipherInputStream cipherInputStream = new CipherInputStream(fileInputStream, cipher)){

            byte[] buffer = new byte[1024];
            int bytesRead;
            while((bytesRead = cipherInputStream.read(buffer)) != -1){
                fileOutputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    public static SecretKey generateAESKey() throws NoSuchAlgorithmException{
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }

    public static SecretKey getKeyFromString(String key){
        return new SecretKeySpec(key.getBytes(), "AES");
    }

    public static SecretKey getSecret(){
        Scanner keyScanner = new Scanner(System.in);
        try {
        SecretKey secretKey = getKeyFromString(passwordString);
        System.out.print("Enter key: ");
        String guess = keyScanner.nextLine();
        if (guess.equals(passwordString)){
            return secretKey;
        }
        else{
            System.out.println("Invalid key try again...");
            getSecret();
            return null;
        }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public static SecretKey secret(){
        SecretKey secretKey = getKeyFromString(passwordString);
        return secretKey;
    }
}
