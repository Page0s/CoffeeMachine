import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CoffeeMachineLogic {

    private final CoffeeType coffeeType = new CoffeeType();
    private CoffeeMachineData data;
    private int coffeeCupsSold = 0;

    public CoffeeMachineLogic() {
        loadDataFromFile();
    }

    public void loadDataFromFile() {
        // Load data from a file
        data = new CoffeeMachineData();
    }

    public void writeDataToFile() {

        File file = new File(data.getPathAndFileName());

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(getWater() + "; " +
                    getMilk() + "; " +
                    getBeans() + "; " +
                    getCups() + "; " +
                    getMoney() + "\n" +
                    data.getAdminUser() + "; " +
                    data.getAdminPass() + "\n" +
                    data.writeDataFormat() +
                    data.getSystemLog());
        } catch (IOException e) {
            System.out.println("An exception occurred " + e.getMessage());
        }
    }

    public void clean(String iAmClean) {
        coffeeCupsSold = 0;
        data.cleanedMachine();
        addToLog(iAmClean);
    }

    public String makeCoffeeType(CoffeeType coffeeType) {

        int sellAmountWater = data.resource.getWater() / coffeeType.water;
        int sellAmountMilk = data.resource.getMilk() / coffeeType.milk;
        int sellAmountBeans = data.resource.getBeans() / coffeeType.beans;
        int sellAmountCups = data.resource.getCups() / coffeeType.cup;

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
            data.resource.setWater(data.resource.getWater() - coffeeType.water);
            data.resource.setMilk(data.resource.getMilk() - coffeeType.milk);
            data.resource.setBeans(data.resource.getBeans() - coffeeType.beans);
            data.resource.setMoney(data.resource.getMoney() + coffeeType.cost);
            data.resource.setCups(data.resource.getCups() - coffeeType.cup);

            coffeeCupsSold++;
            data.totalCoffeeCupsSold();
            switch (coffeeType.name) {
                case "espresso" -> {
                    data.espressoSold();
                    String espresso = "I have enough resources, making you espresso";
                    addToLog(espresso);
                    return espresso;
                }
                case "latte" -> {
                    data.latteSold();
                    String latte = "I have enough resources, making you latte";
                    addToLog(latte);
                    return latte;
                }
                case "cappuccino" -> {
                    data.cappuccinoSold();
                    String cappuccino = "I have enough resources, making you cappuccino";
                    addToLog(cappuccino);
                    return cappuccino;
                }
                default -> {
                    String unknown = "Unknown coffee type.";
                    addToLog(unknown);
                    return unknown;
                }
            }

        } else {
            if (sellAmountWater < 1) {
                String noWater = "Sorry, not enough water!";
                addToLog(noWater);
                return noWater;
            }
            if (sellAmountMilk < 1) {
                String noMilk = "Sorry, not enough milk!";
                addToLog(noMilk);
                return noMilk;
            }
            if (sellAmountBeans < 1) {
                String noBeans = "Sorry, not enough coffee beans!";
                addToLog(noBeans);
                return noBeans;
            }
            if (sellAmountCups < 1) {
                String noCups = "Sorry, not enough cups!";
                addToLog(noCups);
                return noCups;
            }
        }
        return null;
    }

    public void addToLog(String nextLine) {
        String formatedDate = getTimeFormat();
        String systemLog = data.getSystemLog() + ("\n" + formatedDate + ": " + nextLine);
        data.setSystemLog(systemLog);
        writeDataToFile();
    }

    public void addToLogWithNoWriteData(String nextLine) {
        String formatedDate = getTimeFormat();
        String systemLog = data.getSystemLog() + ("\n" + formatedDate + ": " + nextLine);
        data.setSystemLog(systemLog);
    }

    private String getTimeFormat(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentDateTime.format(formatter);
    }

    public void takeMoney(String iGaveYouMoney) {
        data.takeMoney();
        addToLogWithNoWriteData(iGaveYouMoney);
        data.resource.setMoney(0);
        writeDataToFile();
    }

    public int getWater() {
        return data.resource.getWater();
    }

    public int getMilk() {
        return data.resource.getMilk();
    }

    public int getBeans() {
        return data.resource.getBeans();
    }

    public int getCups() {
        return data.resource.getCups();
    }

    public int getMoney() {
        return data.resource.getMoney();
    }

    public String getAdminUser() {
        return data.getAdminUser();
    }

    public String getAdminPass() {
        return data.getAdminPass();
    }

    public void loginFail() {
        data.loginFail();
    }

    public void loginSuccess() {
        data.loginSuccess();
    }

    public String getSystemLog() {
        return data.getSystemLog();
    }

    public void addToWater(int waterAmount) {
        data.resource.setWater(data.resource.getWater() + waterAmount);
    }

    public void addToMilk(int milkAmount) {
        data.resource.setMilk(data.resource.getMilk() + milkAmount);
    }

    public void addToBeans(int beansAmount) {
        data.resource.setBeans(data.resource.getBeans() + beansAmount);
    }

    public void addToCups(int cups) {
        data.resource.setCups(data.resource.getCups() + cups);
    }

    public boolean isCoffeeMachineClean() {
        if (coffeeCupsSold < 10)
            return true;
        return false;
    }

    public CoffeeType getEspresso() {
        return coffeeType.getEspresso();
    }

    public CoffeeType getLatte() {
        return coffeeType.getLatte();
    }

    public CoffeeType getCappuccino() {
        return coffeeType.getCappuccino();
    }

    public String showStatistics(){
        return data.toString();
    }
}