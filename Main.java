import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Path path = Paths.get("ks.jceks");
        

        try (Scanner ds = new Scanner(System.in);){
            if(Files.notExists(path)){
                Register.createUser();
                
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
