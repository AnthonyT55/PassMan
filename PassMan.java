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
import java.util.Scanner;
import java.util.*;


class PassMan{
    public static void encrypt(){
        String inputFile = "passwords.txt";
        String encryptedFile = "passwords.enc";
        


        try {
            FileManager.encryptFile(inputFile, encryptedFile, FileManager.secret());
            System.out.println("Encrypted successfully");
            deleteFile(inputFile);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }

    public static void decrypt(){
        String encryptedFile = "passwords.enc";
        String decryptedFile = "passwords.txt";

        try {
            FileManager.decryptFile(encryptedFile, decryptedFile, FileManager.getSecret());
            System.out.println("Decrypted successfully");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }

        
    public static void checkFile(){
        Path path = Paths.get("passwords.enc");

        try {
            if(Files.notExists(path)){
                Files.createFile(path);
                System.out.println("Passwords file created");
            }
            else{
                System.out.println("Passwords file exists");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
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

    public static void addCredentials(String username, String password, String platform){
        String filePath = "passwords.txt";
        String data = "Username: " + username + "\n Password: " + password + "\n Platform: " + platform + "\n";

        try {
            FileWriter writer = new FileWriter(filePath, true);
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
        int endLineToDelete = startLineToDelete + linesToRemove;
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
            // TODO: handle exception
            e.printStackTrace();
        }
    }


    public static void reRun(){
        System.out.println("Would you like to run again? (yes or no)");
        Scanner runScanner = new Scanner(System.in);
        String answer = runScanner.nextLine();
        switch(answer){
            case "yes":
            Options();
            break;
            case "no":
            System.out.println("Okay Goodbye");
            break;
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

    public static void Options(){
        FileManager.clearScreen();
        Border();
        System.out.println("1: Check File (choose if this is first time running the program)");
        System.out.println("2: Add Credentials");
        System.out.println("3: Show Credentials");
        System.out.println("4: Generate Password");
        System.out.println("5: Delete User");
        System.out.println("6: Exit");
        System.out.print("Choose your option: ");
       try {
        Scanner choiceScanner = new Scanner(System.in);
        int choice = choiceScanner.nextInt();
        switch(choice){
            case 1:
            FileManager.clearScreen();
            System.out.println("Checking for file...");
            checkFile();
            reRun();
            break;

            case 2:
            FileManager.clearScreen();
            System.out.println("Adding Credentials...");
            decrypt();
            Scanner dataScanner = new Scanner(System.in);
            System.out.println("Enter the username you would like to store: ");
            String username = dataScanner.nextLine();
            System.out.println("Enter the password you would like to store: ");
            String password = dataScanner.nextLine();
            System.out.println("What platform is this information for? ");
            String platform = dataScanner.nextLine();
            addCredentials(username, password, platform);
            encrypt();
            reRun();
            break;

            case 3:
            FileManager.clearScreen();
            decrypt();
            System.out.println("Loading Your Credentials");
            FileManager.clearScreen();
            showCredentials();
            encrypt();
            reRun();
            break;

            case 4:
            FileManager.clearScreen();
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
                encrypt();
                reRun();
                break;

                case 2:
                reRun();
                break;

            }

            case 5:
            FileManager.clearScreen();
            System.out.println("Beginning delete sequence...");
            decrypt();
            Scanner deleteScanner = new Scanner(System.in);
            System.out.println("Please enter the line the username you would like to delete appears on: ");
            int Id = deleteScanner.nextInt();
            delete(Id);
            encrypt();
            reRun();
            break;

            case 6:
            FileManager.clearScreen();
            System.out.println("Goodbye...");
            break;
            
        }
        choiceScanner.close();
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

    public static void main(String[] args) {
        Options();
        
        
    }
}
