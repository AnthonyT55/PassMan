import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.crypto.*;





public class PM {

    public static void addCredentials(String username, String password, String platform){
        String filePath = "passwords.txt";
        String data = "Username: " + username + "\n Password: " + password + "\n Platform: " + platform + "\n";

        try (FileWriter writer = new FileWriter(filePath, true);){
            writer.write(data);
            writer.close();
            
            
            System.out.println("Credentials added successfully");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void delete(int ID){
        File file = new File("passwords.txt");
        int startLineToDelete = ID - 1;

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line = reader.readLine()) != null){
                lines.add(line);
            }
        }catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }

        int linesToRemove = 3;
        if(startLineToDelete >= 0 && startLineToDelete < lines.size()){
            for (int i = 0; i < linesToRemove && startLineToDelete < lines.size(); i++){
                lines.remove(startLineToDelete);
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines){
                writer.write(line);
                writer.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("User deleted Successfully");

    }

    public static void showCredentials(){
        String filePath = "passwords.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            int lineNumber = 1;
            while((line = br.readLine()) != null){
                System.out.println( " " + lineNumber + ": " + line);
                lineNumber++;
            }
        } catch (Exception e) {
           System.out.println("Error: " + e);
        }
    }

    public static void reRun(SecretKey sk){
        try (Scanner runScanner = new Scanner(System.in);){
        System.out.println("Would you like to run again? (yes or no)");
        String answer = runScanner.nextLine();
        switch(answer){
            case "yes":
            Path path = Paths.get("passwords.enc");
            
        

            try{
            if(Files.notExists(path)){
                Options(sk);
                
            }
            else{
            Utils.decryptFile(sk, "passwords.enc", "passwords.txt");
            Options(sk);
            break;
            }
            
            }catch (Exception e){
                System.out.println("Error: " + e);
            }
            case "no":
            Path ptxt = Paths.get("passwords.txt");

            try {
                if(Files.exists(ptxt)){
                    System.out.println("Okay Goodbye");
                    Utils.encryptFile(sk, "passwords.txt", "passwords.enc");
                }
                else{
                    System.out.println("Okay Goodbye");
                    break;
                }
                
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
            break;
        }
        }
    }

    public static void Border(){
        System.out.println("__________                         _____                 ");
        System.out.println("\\______   \\_____    ______ ______ /     \\ _____    ____  ");
        System.out.println(" |     ___/\\__  \\  /  ___//  ___//  \\ /  \\\\__  \\  /    \\ ");
        System.out.println(" |    |     / __ \\_\\___ \\ \\___ \\/    Y    \\/ __ \\|   |  \\");
        System.out.println(" |____|    (____  /____  >____  >____|__  (____  /___|  /");
        System.out.println("                \\/     \\/     \\/        \\/     \\/     \\/ ");
        System.out.println("\n");
    }

    public static void Options(SecretKey sk){
        Utils.clearScreen();
        Border();
        System.out.println("1: Add Credentials");
        System.out.println("2: Show Credentials");
        System.out.println("3: Generate Password");
        System.out.println("4: Delete User");
        System.out.println("5: Change Decryption Key");
        System.out.println("6: Exit");
        System.out.print("Choose your option: ");
       try (Scanner choiceScanner = new Scanner(System.in);){
        int choice = choiceScanner.nextInt();
        switch(choice){
            case 1:
            Utils.clearScreen();
            System.out.println("Adding Credentials...");
            Scanner dataScanner = new Scanner(System.in);
            System.out.println("Enter the username you would like to store: ");
            String username = dataScanner.nextLine();
            System.out.println("Enter the password you would like to store: ");
            String password = dataScanner.nextLine();
            System.out.println("What platform is this information for? ");
            String platform = dataScanner.nextLine();
            addCredentials(username, password, platform);
            reRun(sk);
            break;

            case 2:
            Utils.clearScreen();
            System.out.println("Loading Your Credentials");
            showCredentials();
            reRun(sk);
            break;

            case 3:
            Utils.clearScreen();
            System.out.println("Generating your password...");
            Scanner passwordScanner = new Scanner(System.in);
            System.out.print("How long do you want your password?: ");
            int length = passwordScanner.nextInt();
            System.out.print("How many passwords to generate?: ");
            int amount = passwordScanner.nextInt();

            String newPassword = generatePassword(length, amount);
            
            System.out.println(newPassword);
            System.out.println("Would you like to save this password? (1 = yes, 2 = no)");
            switch(passwordScanner.nextInt()){
                case 1:
                Scanner infoScanner = new Scanner(System.in);
                System.out.println("Okay please provide a username to store it with");
                String tempusername = infoScanner.nextLine();
                System.out.println("Now provide me with a platform to store it with as well");
                String plat = infoScanner.nextLine();

                

                addCredentials(tempusername, newPassword, plat);
                reRun(sk);
                break;

                case 2:
                reRun(sk);
                break;

            }
            break;

            case 4:
            Utils.clearScreen();
            System.out.println("Beginning delete sequence...");
            Scanner deleteScanner = new Scanner(System.in);
            System.out.println("Please enter the line the username you would like to delete appears on: ");
            int Id = deleteScanner.nextInt();
            delete(Id);
            reRun(sk);
            break;

            case 5:
            try (Scanner dk = new Scanner(System.in)) {
                Utils.clearScreen();
                System.out.println("Enter your current decryption key: ");
                String attempt = dk.nextLine();
                SecretKey ska = KeyUtils.getSecretKeyFromString(attempt);
                if (ska.equals(sk)){
                    Register.createUser();
                }
                else{
                    System.out.println("Incorrect, Returning to menu...");
                    Options(sk);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
            
            break;

            case 6:
            Utils.clearScreen();
            Utils.encryptFile(sk, "passwords.txt", "passwords.enc");
            System.out.println("Goodbye...");
            break;
            
        }
       } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
       }
    }

    public static String generatePassword(int length, int amount){
        String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String symbols = "!@#$%^&*()+";


        String allCharacters = uppercaseLetters + lowercaseLetters + digits + symbols;

        

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        password.append(getRandomChar(random, uppercaseLetters));
        password.append(getRandomChar(random, lowercaseLetters));
        password.append(getRandomChar(random, digits));
        password.append(getRandomChar(random, symbols));
        
        for (int i = 4; i < length; i++){
            password.append(getRandomChar(random, allCharacters));
        }

        for(int i = 0; i < amount - 1; i++){
            System.out.println(shuffleString(password.toString()));
        }

        return password.toString();

    }

    private static char getRandomChar(SecureRandom random, String characters){
        return characters.charAt(random.nextInt(characters.length()));
    }

    private static String shuffleString(String input){
        char[] array = input.toCharArray();
        SecureRandom random = new SecureRandom();
        for(int i = array.length - 1; i > 0; i--){
            int j = random.nextInt(i + 1);

            char temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return new String(array);
    }
}
