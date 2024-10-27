public class CoffeeType {

    String name;
    int water;
    int milk;
    int beans;
    int cost;
    int cup;

    public CoffeeType() {}

    public CoffeeType(String name, int water, int milk, int beans, int cost, int cup) {
        this.name = name;
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.cost = cost;
        this.cup = cup;
    }

    public CoffeeType getEspresso(){
        return new CoffeeType("espresso", 250, 1, 16, 4, 1);
    }

    public CoffeeType getLatte(){
        return new CoffeeType("latte", 350, 75, 20, 7, 1);
    }

    public CoffeeType getCappuccino(){
        return new CoffeeType("cappuccino", 200, 100, 12, 6, 1);
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
