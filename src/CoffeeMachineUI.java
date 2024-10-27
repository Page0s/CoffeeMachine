import java.util.Scanner;

public class CoffeeMachineUI {
    private final Scanner scanner = new Scanner(System.in);

    public String getUserChoice() {
        return scanner.nextLine();
    }

    public String getUsername(){
        System.out.println("Enter username:");
        return scanner.nextLine();
    }

    public String getPassword(){
        System.out.println("Enter password:");
        return scanner.nextLine();
    }

    public void displayMenu() {
        System.out.println("\nWrite action: \nbuy, login, exit");
        System.out.print("Enter:");
    }

    public void displayAdminMenu(){
        System.out.println("\nWrite action: \n *** fill, remaining, take, log, exit ***");
        System.out.print("Enter:");
    }

    public void wrongUserOrPass(){
        System.out.println("Wrong username or password");
    }

    public void showStorage() {
        System.out.println("CoffeeMachine{ water=" + CoffeeResource.water +
                ", milk=" + CoffeeResource.milk +
                ", coffeeBeans=" + CoffeeResource.beans +
                ", cups=" + CoffeeResource.cups +
                ", money=" + CoffeeResource.money + " }");
    }

    public String writeHowMuchWater(){
        System.out.println("Write how many ml of water you want to add:");
        System.out.print("> ");
        return scanner.nextLine();
    }

    public String writeHowMuchMilk(){
        System.out.println("Write how ml milk you want to add:");
        System.out.print("> ");
        return scanner.nextLine();
    }

    public String writeHowMuchBeans(){
        System.out.println("Write how many grams of coffee beans you want to add:");
        System.out.print("> ");
        return scanner.nextLine();
    }

    public String writeHowMuchCups(){
        System.out.println("Write how many disposable cups you want to add:");
        System.out.print("> ");
        return scanner.nextLine();
    }

    public void wringInput() {
        System.out.println("Wrong input! Please try again!");
    }

    public void displayWhatTypeYouBuy() {
        System.out.println("What do you want to buy?\n1 - espresso\n2 - latte\n3 - cappuccino\nback - to main menu:");
        System.out.print("> ");
    }

    public String makingYouEspresso(){
        String string = "I have enough resources, making you espresso";
        System.out.println(string);
        return string;
    }

    public String makingYouLatte(){
        String string = "I have enough resources, making you latte";
        System.out.println(string);
        return string;
    }

    public String makingYouCappuccino(){
        String string = "I have enough resources, making you cappuccino";
        System.out.println(string);
        return string;
    }

    public void sorryNotEnoughWater(){
        System.out.println("Sorry, not enough water!");
    }

    public void sorryNotEnoughMilk(){
        System.out.println("Sorry, not enough milk!");
    }

    public void sorryNotEnoughBeans(){
        System.out.println("Sorry, not enough coffee beans!");
    }

    public void sorryNotEnoughCups(){
        System.out.println("Sorry, not enough cups!");
    }

    public String wrongInputPleaseWriteInteger() {
        String string = "Invalid input. Please enter a valid integer.";
        System.out.println(string + "\n");
        return string;
    }

    public String iNeedCleaning(){
        String string = "I need cleaning!";
        System.out.println(string + "\n");
        return string;
    }

    public String iAmClean(){
        String string = "I have been cleaned!";
        System.out.println(string + "\n");
        return string;
    }

    public void unknownCoffeeType(){
        System.out.println("Unknown coffee type.");
    }

    public String iGaveYou$(){
        String string = "I gave you $" +  + CoffeeResource.money;
        System.out.println(string);
        return  string;
    }

    public  void cannotReadFile(String message){
        System.out.println("Cannot read file: " +  message);
    }

    public  void anExceptionOccurred(String message) {
        System.out.println("An exception occurred " + message);
    }

    public void errorReadingFile(String message){
        System.out.println("Error reading file: " + message);
    }

    public void fileDoesNotExist(String message){
        System.out.println("File does not exist at: " + message);
    }

    public String fill(){
        return " fill ";
    }

    public void fileContentLoadedSuccessfully(){
        System.out.println("File content loaded successfully!");
    }

    public void writeMessage(String message){
        System.out.println(message);
    }

    public  void loginSuccess(){
        System.out.println("Login Success! \n Welcome admin user.");
    }
}
