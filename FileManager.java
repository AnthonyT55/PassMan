import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class FileManager {
    public static String defaultPassword = "password12345678";

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
        SecretKey secretKey = getKeyFromString(defaultPassword);
        System.out.print("Enter key: ");
        String guess = keyScanner.nextLine();
        if (guess.equals(defaultPassword)){
            return secretKey;
        }
        else{
            System.out.println("Invalid key try again...");
            while(!guess.equals(defaultPassword)){
                getSecret();
                return secretKey;
            }
        }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        keyScanner.close();
        return null;
    }

    public static SecretKey secret(){
        SecretKey secretKey = getKeyFromString(defaultPassword);
        return secretKey;
    }

    public static void clearScreen(){
        String os = System.getProperty("os.name").toLowerCase();

        try {

            //Windows
            if(os.contains("win")){
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            //If any other OS
            else if(os.contains("nix") || os.contains("nux") || os.contains("mac")){
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
            else{
                System.out.println("OS not recognized, cannot clear screen");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

