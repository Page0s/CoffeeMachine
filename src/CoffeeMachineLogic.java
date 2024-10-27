import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CoffeeMachineLogic {

    private final CoffeeMachineUI ui = new CoffeeMachineUI();
    private final CoffeeType coffeeType = new CoffeeType();
    private CoffeeMachineData coffeeMachineData;
    private int coffeeCupsSold = 0;

    public CoffeeMachineLogic() {
        startCoffeeMachine();
    }

    private void startCoffeeMachine() {

        boolean running = true;

        // Load data from a file
        coffeeMachineData = new CoffeeMachineData();

        // Run Coffee Machine until user exits the program
        while (running) {
            if (!isCoffeeMachineRunning()) {
                running = false;
                // save data to a file
                writeDataToFile();
            }
        }
    }

    private void writeDataToFile() {

        File file = new File(coffeeMachineData.getPathAndFileName());

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(CoffeeResource.water + "; " +
                    CoffeeResource.milk + "; " +
                    CoffeeResource.beans + "; " +
                    CoffeeResource.cups + "; " +
                    CoffeeResource.money + "\n" +
                    coffeeMachineData.getAdminUser() + "; " +
                    coffeeMachineData.getAdminPass() + "\n" +
                    coffeeMachineData.writeDataFormat() +
                    coffeeMachineData.getSystemLog());
        } catch (IOException e) {
            ui.anExceptionOccurred(e.getMessage());
        }
    }

    public boolean isCoffeeMachineRunning() {
        // Display menu, get user input
        ui.displayMenu();
        String userInput = ui.getUserChoice();

        switch (userInput) {
            case "buy" -> buyCoffee();
            case "login" -> adminLogin();
            case "exit" -> {
                return false;
            }
            default -> ui.wringInput();
        }

        return true;
    }

    private void adminLogin() {
        // loop until user exit
        boolean running = true;
        String user = ui.getUsername();
        String pass = ui.getPassword();
        if (!user.equals(coffeeMachineData.getAdminUser()) || !pass.equals(coffeeMachineData.getAdminPass())) {
            coffeeMachineData.loginFail();
            ui.wrongUserOrPass();
        } else {
            coffeeMachineData.loginSuccess();
            ui.loginSuccess();
            do {
                // Display menu, get user input
                ui.displayAdminMenu();
                String userInput = ui.getUserChoice();

                switch (userInput) {
                    case "fill" -> fillStorage();
                    case "take" -> takeMoney();
                    case "remaining" -> ui.showStorage();
                    case "log" -> showLog();
                    case "exit" -> {
                        running = false;
                    }
                    default -> ui.wringInput();
                }
            } while (running);
        }
    }

    private void showLog() {
        ui.writeMessage(coffeeMachineData.getSystemLog());
        ui.writeMessage(coffeeMachineData.toString());
    }

    private void buyCoffee() {
        // Sell coffee until need cleaning
        if (coffeeCupsSold < 10) {
            boolean isUserInputTrue = true;
            String userInput;

            // loop until user input correct
            do {
                ui.displayWhatTypeYouBuy();
                userInput = ui.getUserChoice();
                if (userInput.equals("1") || userInput.equals("2") || userInput.equals("3") || userInput.equals("back")) {
                    isUserInputTrue = false;
                } else {
                    ui.wringInput();
                }
            } while (isUserInputTrue);

            // make coffee or write what is missing
            switch (userInput) {
                case "1" -> makeCoffeeType(coffeeType.getEspresso());
                case "2" -> makeCoffeeType(coffeeType.getLatte());
                case "3" -> makeCoffeeType(coffeeType.getCappuccino());
            }
        } else {
            addToLog(ui.iNeedCleaning());
            clean();
        }
    }

    private void clean() {
        coffeeCupsSold = 0;
        coffeeMachineData.cleanedMachine();
        addToLog(ui.iAmClean());
    }

    private void makeCoffeeType(CoffeeType coffeeType) {

        int sellAmountWater = CoffeeResource.water / coffeeType.water;
        int sellAmountMilk = CoffeeResource.milk / coffeeType.milk;
        int sellAmountBeans = CoffeeResource.beans / coffeeType.beans;
        int sellAmountCups = CoffeeResource.cups / coffeeType.cup;

        int amountCoffeeToSell;

        // Assume waterCups is the smallest initially
        amountCoffeeToSell = sellAmountWater;

        // special case espresso
        if (coffeeType.milk == 1) {
            // Compare with beansCups
            if (sellAmountBeans < amountCoffeeToSell) {
                amountCoffeeToSell = sellAmountBeans;
            }
            // Compare with Cups
            if (sellAmountCups < amountCoffeeToSell) {
                amountCoffeeToSell = sellAmountCups;
            }
            // latte and cappuccino
        } else {
            // Compare with beansCups
            if (sellAmountMilk < amountCoffeeToSell) {
                amountCoffeeToSell = sellAmountMilk;
            }
            // Compare with milkCups
            if (sellAmountBeans < amountCoffeeToSell) {
                amountCoffeeToSell = sellAmountBeans;
            }
            // Compare with cpus
            if (sellAmountCups < amountCoffeeToSell) {
                amountCoffeeToSell = sellAmountCups;
            }
        }

        // make coffee and reduce the storage
        if (amountCoffeeToSell > 0) {
            // reduce storage
            CoffeeResource.water -= coffeeType.water;
            CoffeeResource.milk -= coffeeType.milk;
            CoffeeResource.beans -= coffeeType.beans;
            CoffeeResource.money += coffeeType.cost;
            CoffeeResource.cups -= coffeeType.cup;

            coffeeCupsSold++;
            coffeeMachineData.totalCoffeeCupsSold();
            switch (coffeeType.name) {
                case "espresso" -> {
                    coffeeMachineData.espressoSold();
                    addToLog(ui.makingYouEspresso());
                }
                case "latte" -> {
                    coffeeMachineData.latteSold();
                    addToLog(ui.makingYouLatte());
                }
                case "cappuccino" -> {
                    coffeeMachineData.cappuccinoSold();
                    addToLog(ui.makingYouCappuccino());
                }
                default -> ui.unknownCoffeeType();
            }

        } else {
            if (sellAmountWater < 1) {
                ui.sorryNotEnoughWater();
            }
            if (sellAmountMilk < 1) {
                ui.sorryNotEnoughMilk();
            }
            if (sellAmountBeans < 1) {
                ui.sorryNotEnoughBeans();
            }
            if (sellAmountCups < 1) {
                ui.sorryNotEnoughCups();
            }
        }
    }

    private void addToLog(String nextLine) {
        String systemLog = coffeeMachineData.getSystemLog() + ("\n" + nextLine);
        coffeeMachineData.setSystemLog(systemLog);
        writeDataToFile();
    }

    private void addToLogWithNoWriteData(String nextLine) {
        String systemLog = coffeeMachineData.getSystemLog() + ("\n" + nextLine);
        coffeeMachineData.setSystemLog(systemLog);
    }

    private void takeMoney() {
        coffeeMachineData.takeMoney();
        addToLogWithNoWriteData(ui.iGaveYouMoney());
        CoffeeResource.money -= CoffeeResource.money;
        writeDataToFile();
    }

    private void fillStorage() {

        int waterAmount = checkUserInput(IngredientType.WATER);
        CoffeeResource.water += waterAmount;
        addToLog(waterAmount + ui.fill() + IngredientType.WATER);

        int milkAmount = checkUserInput(IngredientType.MILK);
        CoffeeResource.milk += milkAmount;
        addToLog(milkAmount + ui.fill() + IngredientType.MILK);

        int beansAmount = checkUserInput(IngredientType.BEANS);
        CoffeeResource.beans += beansAmount;
        addToLog(beansAmount + ui.fill() + IngredientType.BEANS);

        int cups = checkUserInput(IngredientType.CUPS);
        CoffeeResource.cups += cups;
        addToLog(cups + ui.fill() + IngredientType.CUPS);

        writeDataToFile();
    }

    private int checkUserInput(IngredientType ingredientType) {
        int amount = 0;
        boolean noInteger = true;
        String userInput;

        do {
            switch (ingredientType) {
                case WATER -> userInput = ui.writeHowMuchWater();
                case MILK -> userInput = ui.writeHowMuchMilk();
                case BEANS -> userInput = ui.writeHowMuchBeans();
                case CUPS -> userInput = ui.writeHowMuchCups();
                default -> userInput = null;
            }
            // Check user input
            if (isInteger(userInput)) {
                amount = Integer.parseInt(userInput); // Safe to parse since it's valid
                noInteger = false;
            } else {
                addToLog(ui.wrongInputPleaseWriteInteger());
            }
        } while (noInteger);
        return amount;
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
}