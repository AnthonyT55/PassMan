import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import javax.crypto.*;

public class Main {
    public static void main(String[] args) {
        Path path = Paths.get("ks.jceks");
        Path pf = Paths.get("passwords.txt");
        

        try (Scanner ds = new Scanner(System.in);){
            if(Files.notExists(path)){
                Register.createUser();
                
            }
            else if(Files.exists(pf)){
                PM.Border();
                System.out.println("Passwords file seems to exist, but isn't encrypted.\nPlease enter your decryption key before we begin: ");
                String sk = ds.nextLine();
                SecretKey dk = KeyUtils.getSecretKeyFromString(sk);
                Utils.encryptFile(dk, "passwords.txt", "passwords.enc");
                Utils.clearScreen();

                System.out.println("Enter your username: ");
                String alias = ds.nextLine();
        
                System.out.println("Enter your load key: ");
                char[] pw = ds.nextLine().toCharArray();
                
                Register.auth(alias, pw);
            }
            else{
                PM.Border();
                System.out.println("Enter your username: ");
                String alias = ds.nextLine();
        
                System.out.println("Enter your load key: ");
                char[] pw = ds.nextLine().toCharArray();
                Register.auth(alias, pw);

            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        
    }
}

