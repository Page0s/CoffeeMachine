import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CoffeeMachineData {

//    private final CoffeeType coffeeType = new CoffeeType();
private CoffeeType[] coffeeTypeArray = new CoffeeType[]{new CoffeeType("espresso", 250, 1, 16, 4, 1),
        new CoffeeType("latte", 350, 75, 20, 7, 1),
        new CoffeeType("cappuccino", 200, 100, 12, 6, 1)};
    CoffeeResource resource = new CoffeeResource();

    private final String pathAndFileName = "doc/coffee_machine_status.txt";
    private String adminUser = "admin";
    private String adminPass = "admin123";
    private String systemLog;
    private int totalCoffeeCupsSold = 0;
    private int totalAmountCleaned = 0;
    private int totalEspressoSold = 0;
    private int totalLatteSold = 0;
    private int totalCappuccinoSold = 0;
    private int totalMoneyEarned = 0;
    private int totalMoneyFromEspresso = 0;
    private int totalMoneyFromLatte = 0;
    private int totalMoneyFromCappuccino = 0;
    private int totalMoneyTaken = 0;
    private int totalTimesMoneyTaken = 0;
    private int totalLoginSuccess = 0;
    private int totalLoginFails = 0;

    public CoffeeMachineData() {
        // Load data from file
        try {
            readFileAsString();
        } catch (IOException e) {
            System.out.println("Cannot read file: " + e.getMessage());
        }
    }

    private void readFileAsString() throws IOException {

        String dataFromFile;

        // Check if file exists before reading
        Path path = Paths.get(pathAndFileName);
        if (Files.exists(path)) {
            try {
                dataFromFile = new String(Files.readAllBytes(path));
                System.out.println("File content loaded successfully!");

                parseLoadedStringData(dataFromFile);

            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        } else {
            System.out.println("File does not exist at: " + pathAndFileName);
        }
    }

    // Parse data to program variables
    private void parseLoadedStringData(String dataFromFile) {
        // Split content by lines
        String[] lines = dataFromFile.split("\n");

        // Parse the first line for CoffeeResource quantities
        String[] resourceValues = lines[0].trim().split("; ");

        // Load to values to program variables
        resource.setWater(Integer.parseInt(resourceValues[0]));
        resource.setMilk(Integer.parseInt(resourceValues[1]));
        resource.setBeans(Integer.parseInt(resourceValues[2]));
        resource.setCups(Integer.parseInt(resourceValues[3]));
        resource.setMoney(Integer.parseInt(resourceValues[4]));

        // Parse the second line for admin credentials
        String[] adminCredentials = lines[1].trim().split("; ");
        adminUser = adminCredentials[0];
        adminPass = adminCredentials[1];

        // Parse the third line for system variables
        String[] systemVariables = lines[2].trim().split("; ");
        totalLoginFails = Integer.parseInt(systemVariables[0]);
        totalLoginSuccess = Integer.parseInt(systemVariables[1]);
        totalTimesMoneyTaken = Integer.parseInt(systemVariables[2]);
        totalMoneyTaken = Integer.parseInt(systemVariables[3]);
        totalMoneyFromCappuccino = Integer.parseInt(systemVariables[4]);
        totalMoneyFromLatte = Integer.parseInt(systemVariables[5]);
        totalMoneyFromEspresso = Integer.parseInt(systemVariables[6]);
        totalMoneyEarned = Integer.parseInt(systemVariables[7]);
        totalCappuccinoSold = Integer.parseInt(systemVariables[8]);
        totalLatteSold = Integer.parseInt(systemVariables[9]);
        totalEspressoSold = Integer.parseInt(systemVariables[10]);
        totalAmountCleaned = Integer.parseInt(systemVariables[11]);
        totalCoffeeCupsSold = Integer.parseInt(systemVariables[12]);

        // Join lines starting from index 2 (skipping the first two lines)
        StringBuilder logBuilder = new StringBuilder();
        for (int i = 3; i < lines.length; i++) {
            logBuilder.append(lines[i]).append("\n");
        }

        systemLog = logBuilder.toString().trim(); // Trim to remove the trailing newline
    }

    public String getSystemLog() {
        return systemLog;
    }

    public String getAdminUser() {
        return adminUser;
    }

    public String getAdminPass() {
        return adminPass;
    }

    public String getPathAndFileName() {
        return pathAndFileName;
    }

    public void setSystemLog(String systemLog) {
        this.systemLog = systemLog;
    }

    public void totalCoffeeCupsSold() {
        totalCoffeeCupsSold++;
    }

    public void espressoSold() {
        totalEspressoSold++;
        totalMoneyEarned += coffeeTypeArray[0].getCost();
        totalMoneyFromEspresso += coffeeTypeArray[0].getCost();
    }

    public void latteSold() {
        totalLatteSold++;
        totalMoneyEarned += coffeeTypeArray[1].getCost();
        totalMoneyFromLatte += coffeeTypeArray[1].getCost();
    }

    public void cappuccinoSold() {
        totalCappuccinoSold++;
        totalMoneyEarned += coffeeTypeArray[2].getCost();
        totalMoneyFromCappuccino += coffeeTypeArray[2].getCost();
    }

    public void cleanedMachine() {
        totalAmountCleaned++;
    }

    public void takeMoney() {
        totalTimesMoneyTaken++;
        totalMoneyTaken += resource.getMoney();
    }

    public void loginSuccess() {
        totalLoginSuccess++;
    }

    public void loginFail() {
        totalLoginFails++;
    }

    public String writeDataFormat() {
        return totalLoginFails + "; " +
                totalLoginSuccess + "; " +
                totalTimesMoneyTaken + "; " +
                totalMoneyTaken + "; " +
                totalMoneyFromCappuccino + "; " +
                totalMoneyFromLatte + "; " +
                totalMoneyFromEspresso + "; " +
                totalMoneyEarned + "; " +
                totalCappuccinoSold + "; " +
                totalLatteSold + "; " +
                totalEspressoSold + "; " +
                totalAmountCleaned + "; " +
                totalCoffeeCupsSold + "\n";
    }

    @Override
    public String toString() {
        return "CoffeeMachineData{" + "\n" +
                "   totalLoginFails = " + totalLoginFails + "\n" +
                "   totalLoginSuccess = " + totalLoginSuccess + "\n" +
                "   totalTimesMoneyTaken = " + totalTimesMoneyTaken + "\n" +
                "   totalMoneyTaken = " + totalMoneyTaken + "\n" +
                "   totalMoneyFromCappuccino = " + totalMoneyFromCappuccino + "\n" +
                "   totalMoneyFromLatte = " + totalMoneyFromLatte + "\n" +
                "   totalMoneyFromEspresso = " + totalMoneyFromEspresso + "\n" +
                "   totalMoneyEarned = " + totalMoneyEarned + "\n" +
                "   totalCappuccinoSold = " + totalCappuccinoSold + "\n" +
                "   totalLatteSold = " + totalLatteSold + "\n" +
                "   totalEspressoSold = " + totalEspressoSold + "\n" +
                "   totalAmountCleaned = " + totalAmountCleaned + "\n" +
                "   totalCoffeeCupsSold = " + totalCoffeeCupsSold + "\n" +
                '}' + "\n";
    }
}
