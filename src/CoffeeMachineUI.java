import java.util.Scanner;

public class CoffeeMachineUI {
    private final Scanner scanner = new Scanner(System.in);
    CoffeeType[] coffeeTypes = {new CoffeeType("espresso", 250, 1, 16, 4, 1),
            new CoffeeType("latte", 350, 75, 20, 7, 1),
            new CoffeeType("cappuccino", 200, 100, 12, 6, 1)};
    CoffeeMachineLogic logic;

    public CoffeeMachineUI() {
        // load data to program
        logic = new CoffeeMachineLogic();
    }

    public void runCoffeeMachine(){

        boolean running = true;

        // Run Coffee Machine until user exits the program
        while (running) {
            if (!isCoffeeMachineRunning()) {
                running = false;
                // save data to a file
                logic.writeDataToFile();
            }
        }
    }

    public boolean isCoffeeMachineRunning() {
        // Display menu, get user input
        displayMenu();
        String userInput = getUserChoice();

        switch (userInput) {
            case "buy" -> {
                logic.addToLog("User: " + validUserInput(userInput));
                System.out.println("User: " + validUserInput(userInput));
                buyCoffee();
            }
            case "login" -> {
                logic.addToLog("User: " + validUserInput(userInput));
                System.out.println("User: " + validUserInput(userInput));
                adminLogin();
            }
            case "exit" -> {
                logic.addToLog("User: " + validUserInput(userInput));
                System.out.println("User: " + validUserInput(userInput));
                return false;
            }
            default -> {
                logic.addToLog("User: " + wrongInput(userInput));
                System.out.println("User: " + wrongInput(userInput));
            }
        }
        return true;
    }

    public void buyCoffee() {
        // Sell coffee until need cleaning
        if (logic.isCoffeeMachineClean()) {
            boolean isUserInputTrue = true;
            String userInput;

            // loop until user input correct
            do {
                displayWhatTypeYouBuy();
                userInput = getUserChoice();
                if (userInput.equals("1") || userInput.equals("2") || userInput.equals("3") || userInput.equals("back")) {
                    isUserInputTrue = false;
                } else {
                    logic.addToLog("User: " + wrongInput(userInput));
                    System.out.println("User: " + wrongInput(userInput));
                }
            } while (isUserInputTrue);

            // make coffee or write what is missing
            switch (userInput) {
                case "1" -> {
                    logic.addToLog("User: " + validUserInput(userInput));
                    writeMessage(logic.makeCoffeeType(coffeeTypes[0]));
                }
                case "2" -> {
                    logic.addToLog("User: " + validUserInput(userInput));
                    writeMessage(logic.makeCoffeeType(coffeeTypes[1]));
                }
                case "3" -> {
                    logic.addToLog("User: " + validUserInput(userInput));
                    writeMessage(logic.makeCoffeeType(coffeeTypes[2]));
                }
                case "back" -> {
                    logic.addToLog("User: " + validUserInput(userInput));
                    writeMessage("User: " + validUserInput(userInput));
                }
            }
        } else {
            logic.addToLog(iNeedCleaning());
            logic.clean(iAmClean());
        }
    }

    private void adminLogin() {
        // loop until user exit
        boolean running = true;
        String user = getUsername();
        String pass = getPassword();
        if (!user.equals(logic.getAdminUser()) || !pass.equals(logic.getAdminPass())) {
            logic.loginFail();
            logic.addToLog(wrongUserOrPass());
            System.out.println(wrongUserOrPass());
        } else {
            logic.loginSuccess();
            logic.addToLog(loginSuccess());
            System.out.println(loginSuccess());
            do {
                // Display menu, get user input
                displayAdminMenu();
                String userInput = getUserChoice();

                switch (userInput) {
                    case "fill" -> {
                        logic.addToLog("Admin: " + validUserInput(userInput));
                        writeMessage("Admin: " + validUserInput(userInput));
                        fillStorage();
                    }
                    case "take" -> {
                        logic.addToLog("Admin: " + validUserInput(userInput));
                        writeMessage("Admin: " + validUserInput(userInput));
                        takeMoney();
                    }
                    case "remaining" -> {
                        logic.addToLog("Admin: " + validUserInput(userInput));
                        writeMessage("Admin: " + validUserInput(userInput));
                        showStorage();
                    }
                    case "log" -> {
                        logic.addToLog("Admin: " + validUserInput(userInput));
                        writeMessage("Admin: " + validUserInput(userInput));
                        showLog();
                    }
                    case "exit" -> {
                        logic.addToLog("Admin: " + validUserInput(userInput));
                        writeMessage("Admin: " + validUserInput(userInput));
                        running = false;
                    }
                    default -> {
                        logic.addToLog("Admin: " + wrongInput(userInput));
                        System.out.println("Admin: " + wrongInput(userInput));
                    }
                }
            } while (running);
        }
    }

    private void fillStorage() {

        int waterAmount = checkUserInput(IngredientType.WATER);
        logic.addToWater(waterAmount);
        logic.addToLog(waterAmount + fill() + IngredientType.WATER);

        int milkAmount = checkUserInput(IngredientType.MILK);
        logic.addToMilk(milkAmount);
        logic.addToLog(milkAmount + fill() + IngredientType.MILK);

        int beansAmount = checkUserInput(IngredientType.BEANS);
        logic.addToBeans(beansAmount);
        logic.addToLog(beansAmount + fill() + IngredientType.BEANS);

        int cups = checkUserInput(IngredientType.CUPS);
        logic.addToCups(cups);
        logic.addToLog(cups + fill() + IngredientType.CUPS);

        logic.writeDataToFile();
    }

    private int checkUserInput(IngredientType ingredientType) {
        int amount = 0;
        boolean noInteger = true;
        String userInput;

        do {
            switch (ingredientType) {
                case WATER -> userInput = writeHowMuchWater();
                case MILK -> userInput = writeHowMuchMilk();
                case BEANS -> userInput = writeHowMuchBeans();
                case CUPS -> userInput = writeHowMuchCups();
                default -> userInput = null;
            }
            // Check user input
            if (isInteger(userInput)) {
                amount = Integer.parseInt(userInput); // Safe to parse since it's valid
                noInteger = false;
            } else {
                logic.addToLog(wrongInputPleaseWriteInteger());
            }
        } while (noInteger);
        return amount;
    }

    private void showLog() {
        writeMessage(logic.getSystemLog());
        writeMessage(logic.showStatistics());
    }

    // Method to check if input is a valid integer
    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input); // Try to parse input as an integer
            return true; // If parsing succeeds, input is a valid integer
        } catch (NumberFormatException e) {
            return false; // If an exception is thrown, input is not a valid integer
        }
    }

    private void takeMoney(){
        logic.takeMoney(iGaveYouMoney());
    }

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

    public String wrongUserOrPass(){
        return "Wrong username or password!";
    }

    public void showStorage() {
        System.out.println("CoffeeMachine{ water=" + logic.getWater() +
                ", milk=" + logic.getMilk() +
                ", coffeeBeans=" + logic.getBeans() +
                ", cups=" + logic.getCups() +
                ", money=" + logic.getMoney() + " }");
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

    public String wrongInput(String userInput) {
        return userInput + " is a wrong input! Please try again!";
    }

    private String validUserInput(String userInput){
        return userInput + " is valid input.";
    }

    public void displayWhatTypeYouBuy() {
        System.out.println("What do you want to buy?\n1 - espresso\n2 - latte\n3 - cappuccino\nback - to main menu:");
        System.out.print("> ");
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
        String string = "I am running automatic cleaning service. Please stand buy.............\n" +
                ".............\n" +
                ".............\n" +
                "I have been cleaned!";
        System.out.println(string + "\n");
        return string;
    }

    public String iGaveYouMoney(){
        String string = "Admin: I gave you $" + logic.getMoney();
        System.out.println(string);
        return  string;
    }

    public String fill(){
        return " fill ";
    }

    public void writeMessage(String message){
        System.out.println(message);
    }

    public  String loginSuccess(){
        return "Login Success! *** Welcome admin user ***";
    }
}
