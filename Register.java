import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.ProtectionParameter;
import java.util.*;
import javax.crypto.*;

public class Register {
    public static void createUser(){
        try (FileOutputStream fos = new FileOutputStream("ks.jceks"); Scanner dkScanner = new Scanner(System.in);) {
            KeyStore ks = KeyStore.getInstance("JCEKS");
            ks.load(null, null);

            PM.Border();
            Path path = Paths.get("passwords.txt");

            if(Files.notExists(path)){
                Files.createFile(path);
                System.out.println("Passwords file created");
            }
            else{
                System.out.println("Passwords file exists");
            }
        
        

            
            System.out.println("Please enter a key to load your database: ");
            
            char[] password = dkScanner.nextLine().toCharArray();
            ProtectionParameter pm = new PasswordProtection(password);

            System.out.println("Please enter the username you wish to use: ");
            String alias = dkScanner.nextLine();

            System.out.println("Enter a new decryption key: ");
            String dk = dkScanner.nextLine();
            SecretKey sk = KeyUtils.getSecretKeyFromString(dk);

            KeyStore.SecretKeyEntry skE = new KeyStore.SecretKeyEntry(sk);
            ks.setEntry(alias, skE, pm);


            

            ks.store(fos, password);
            System.out.println("Master key created");
            Utils.encryptFile(sk, "passwords.txt", "passwords.enc");
            PM.reRun(sk);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }


    public static void auth(String alias, char[] pw){
        try (FileInputStream fis = new FileInputStream("ks.jceks"); Scanner ds = new Scanner(System.in);) {
            KeyStore ks = KeyStore.getInstance("JCEKS");
            
            
            

            System.out.println("Enter your decryption key: ");
            String attempt = ds.nextLine();
            ks.load(fis, pw);
            Key key = ks.getKey(alias, pw);
            SecretKey sk = (SecretKey) key;
            if(KeyUtils.getSecretKeyFromString(attempt).equals(sk)){
                System.out.println("Success");
                Utils.decryptFile(sk, "passwords.enc", "passwords.txt");
                PM.Options(sk);
            }
            else{ 
            System.out.println("Invalid Decryption Key, Try Again...");    
            while(!KeyUtils.getSecretKeyFromString(attempt).equals(sk)){
                auth(alias, pw);
                break;
            }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
    
}
