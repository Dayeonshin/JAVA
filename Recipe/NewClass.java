
import java.util.Arrays;





/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dayeonshin
 */
public class NewClass {
    public static void main(String[] args) {
        String[] item = {"salted butter", "white sugar", "brown sugar", "vanilla extract", "large eggs", "all-purpose flour", "baking soda", "baking powder", "sea salt", "chocolate chips"};
	String[] unit = {"cup", "cup", "cup", "tsp", "", "cups", "tsp", "tsp", "tsp", "cups"};
	double[] measurement = {1, 1, 1, 2, 2, 3, 1, 0.5, 1, 2};
        Recipe cookie = new Recipe(item, measurement, unit);
    }
}

class Ingredient {
    
    double measurement;
    String item;
    String unit;
    public Ingredient(String item, double measurement, String unit) {
        this.item = item;
        this.measurement = measurement;
        this.unit = unit;
    }
    public double  getMeasurement() {
        return this.measurement;
    }
    public String getItem() {
        return this.item;
    }
    public String getUnit() {
        return this.unit;
    }
    @Override
    public String toString()
    {
	return this.measurement + " " + this.unit + " of " + this.item;
    }
}

class Recipe {
    Ingredient[] ingredients;
    public Recipe(String[] item, double[] measurement, String[] unit) {
        this.ingredients = new Ingredient[item.length];
        System.out.println("Let's start baking the Chocolate Chip Cookie\r\n\r\n");
        for (int i = 0; i < ingredients.length; i++) {
            this.ingredients[i] = new Ingredient(item[i], measurement[i], unit[i]);
            System.out.println("mix " + ingredients[i] + "\r\n\r\n");
        }
        System.out.println("Bake in oven for 10 minutes! And it's finished!");
    }
}