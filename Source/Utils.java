import java.io.*;
import javax.crypto.*;

public class Utils {
    public static void encryptFile(SecretKey secretKey, String inputFile, String outputFile) throws Exception{
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
        deleteFile(inputFile);
    }

    public static void decryptFile(SecretKey secretKey, String inputFile, String outputFile) throws Exception{
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
        deleteFile(inputFile);
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
            System.out.println("Error: " + e);
        }
    }
    public static void deleteFile(String path){
        File file = new File(path);
        if(file.delete()){
            System.out.println("Process Completed.");
        }
        else{
            System.out.println("Failed to delete");
        }
    }
}
