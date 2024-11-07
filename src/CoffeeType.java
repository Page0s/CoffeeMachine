public class CoffeeType {

    private String name;
    private int water;
    private int milk;
    private int beans;
    private int cost;
    private int cup;

    public CoffeeType(String name, int water, int milk, int beans, int cost, int cup) {
        this.name = name;
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.cost = cost;
        this.cup = cup;
    }

    public String getName() {
        return name;
    }

    public int getWater() {
        return water;
    }

    public int getMilk() {
        return milk;
    }

    public int getBeans() {
        return beans;
    }

    public int getCost() {
        return cost;
    }

    public int getCup() {
        return cup;
    }
    @Override
    public String toString() {
        return name + ": Water = " + water + "ml, Milk = " + milk + "ml, Beans = " + beans + "g, Cost = $" + cost + ", Cups = " + cup;
    }
}
