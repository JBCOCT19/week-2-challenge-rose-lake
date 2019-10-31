/**
 * Week 2 Challenge: Chiptole Jr +
 * Ksenia Lake
 * Thursday, October 31, 2019
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;

public class ChipotleApp {

    // master list to choose from. top-level.
    private static ArrayList<String> allIngredients = new ArrayList<>(Arrays.asList("rice", "meat", "beans", "salsa",
            "veggies", "cheese", "guac", "queso", "sour cream"));

    // Ingredients with options lists
    private static ArrayList<String> riceOptions = new ArrayList<>(Arrays.asList("white", "brown", "no", "all"));
    private static ArrayList<String> meatOptions = new ArrayList<>(Arrays.asList("chicken", "steak", "carnitas",
            "chorizo", "sofritas", "veggies (as meat option)", "no", "all"));
    private static ArrayList<String> beansOptions = new ArrayList<>(Arrays.asList("pinto", "black", "no", "all"));
    private static ArrayList<String> salsaOptions = new ArrayList<>(Arrays.asList("mild", "medium", "hot", "no",
            "all"));
    private static ArrayList<String> veggiesOptions = new ArrayList<>(Arrays.asList("lettuce", "fajita veggies",
            "no", "all"));

    // Initialize counters for ingredients with options
    // we subtract 2 to account for the "no" and "all" options, which we're not tracking as individual ingredients
    // these counters default to zero
    private static int[] riceCount = new int[riceOptions.size() - 2];
    private static int[] meatCount = new int[meatOptions.size() - 2];
    private static int[] beansCount = new int[beansOptions.size() - 2];
    private static int[] salsaCount = new int[salsaOptions.size() - 2];
    private static int[] veggiesCount = new int[veggiesOptions.size() - 2];

    // Initialize counters for yes/no ingredients
    private static int cheeseCount = 0;
    private static int guacCount = 0;
    private static int quesoCount = 0;
    private static int sourCreamCount = 0;

    // This will store our burrito!
    // We re-initialize it as "" (in main) before each new burrito is made.
    private static String currentBurrito;

    // This will store the number of ingredients in our current burrito
    // will be used to calculate the cost
    private static int currentBurritoCount;

    // this tracks how many ingredient categories we've used so far.
    // assignment requires 5 to 9 are used each time.
    // ("all" counts as one hit).
    private static int ingredientCategories;

    // this flags whether this ingredient has already been included or not...
    // used to try to get the burrito up to the requested number of ingredients.
    // these flags default to false
    private static boolean[] flagCategories = new boolean[allIngredients.size()];

    public static void main(String[] args) {

        // *********************************************
        // wrap everything in a loop that runs 25 times
        //**********************************************
        for (int i = 0; i < 25; i++) {

            //***************************************************
            // randomly select 5 through 9 ingredient categories
            //***************************************************
            // range size is 5 (aka 9-5+1) and range start is 5
            Random randomGenerator = new Random();
            int numCategories = randomGenerator.nextInt(5) + 5;

            // initialize class-level variables before each new burrito... very important!...
            currentBurrito = "";
            currentBurritoCount = 0;
            ingredientCategories = 0;
            Arrays.fill(flagCategories, false);

            System.out.println("flagCategories: " + Arrays.toString(flagCategories));

            // build one burrito
            loopIngredients(numCategories);
            System.out.println("flagCategories: " + Arrays.toString(flagCategories));
            System.out.println("Burrito " + (i+1) +": " + currentBurrito);
            System.out.println("current Burrito's ingredients count: " + currentBurritoCount);
            System.out.println("numCategories robot requested: " + numCategories);
            System.out.println("categories in this burrito: " + ingredientCategories + "\n");

            // check if requested # of ingredients is there, if not loop again
            if (ingredientCategories < numCategories) {
                loopIngredients(numCategories);
            };
            System.out.println("flagCategories: " + Arrays.toString(flagCategories));
            System.out.println("Burrito " + (i+1) +" AGAIN: " + currentBurrito);
            System.out.println("current Burrito's ingredients count: " + currentBurritoCount);
            System.out.println("numCategories robot requested: " + numCategories);
            System.out.println("categories in this burrito: " + ingredientCategories+ "\n");
            // highly doubtful that more than two ingredient loops are needed!

        } // end 25 times loop
        //****************************************************************

    } // end main


    private static void loopIngredients(int numCategories) {
        // this takes ingredients from a category and adds them to the burrito
        // as long as the category is not already included in the burrito!
        for (int i = 0; i < allIngredients.size(); i++) {
            switch (allIngredients.get(i)) {
                case "rice":
                    if (!flagCategories[i]) {
                        flagCategories[i] = chooseRice();
                    }
                    break;
                case "meat":
                    if (!flagCategories[i]) {
                        flagCategories[i] = chooseMeat();
                    }
                    break;
                case "beans":
                    if (!flagCategories[i]) {
                        flagCategories[i] = chooseBeans();
                    }
                    break;
                case "salsa":
                    if (!flagCategories[i]) {
                        flagCategories[i] = chooseSalsa();
                    }
                    break;
                case "veggies":
                    if (!flagCategories[i]) {
                        flagCategories[i] = chooseVeggies();
                    }
                    break;
                case "cheese":
                    if (!flagCategories[i]) {
                        flagCategories[i] = chooseCheese();
                    }
                    break;
                case "guac":
                    if (!flagCategories[i]) {
                        flagCategories[i] = chooseGuac();
                    }
                    break;
                case "queso":
                    if (!flagCategories[i]) {
                        flagCategories[i] = chooseQueso();
                    }
                    break;
                case "sour cream":
                    if (!flagCategories[i]) {
                        flagCategories[i] = chooseSourCream();
                    }
                    break;
            }

            // once we hit the requested number of ingredient categories, break! (and exit function)
            if (ingredientCategories == numCategories) {
                break;
            }

        } // end for each ingredient in allIngredients

    } // end method

    private static boolean chooseSourCream() {

        // randomly select ZERO (no) or ONE (yes)
        Random randomGenerator = new Random();
        int yesNo = randomGenerator.nextInt(2);     // range of size two, starts at zero, goes to 1.
        if (yesNo == 1) {
            currentBurrito += "sour cream, ";
            currentBurritoCount += 1;
            sourCreamCount += 1;
            ingredientCategories +=1;
            return true;
        } else {
            // do not add anything on to the current burrito order
            // do not increment sour cream count
            return false;
        }

    } // end chooseSourCream()

    private static boolean chooseQueso() {

        // randomly select ZERO (no) or ONE (yes)
        Random randomGenerator = new Random();
        int yesNo = randomGenerator.nextInt(2);     // range of size two, starts at zero, goes to 1.
        if (yesNo == 1) {
            currentBurrito += "queso, ";
            currentBurritoCount += 1;
            quesoCount += 1;
            ingredientCategories +=1;
            return true;
        } else {
            // do not add anything on to the current burrito order
            // do not increment queso count
            return false;
        }

    } // end chooseQueso()

    private static boolean chooseGuac() {

        // randomly select ZERO (no) or ONE (yes)
        Random randomGenerator = new Random();
        int yesNo = randomGenerator.nextInt(2);     // range of size two, starts at zero, goes to 1.
        if (yesNo == 1) {
            currentBurrito += "guac, ";
            currentBurritoCount += 1;
            guacCount += 1;
            ingredientCategories +=1;
            return true;
        } else {
            // do not add anything on to the current burrito order
            // do not increment guac count
            return false;
        }

    } // end chooseGuac()


    private static boolean chooseCheese() {

        // randomly select ZERO (no) or ONE (yes)
        Random randomGenerator = new Random();
        int yesNo = randomGenerator.nextInt(2);     // range of size two, starts at zero, goes to 1.
        if (yesNo == 1) {
            currentBurrito += "cheese, ";
            currentBurritoCount += 1;
            cheeseCount += 1;
            ingredientCategories +=1;
            return true;
        } else {
            // do not add anything on to the current burrito order
            // do not increment cheese count
            return false;
        }

    } // end chooseCheese()

    private static boolean chooseVeggies() {

        // get the randomly selected option from the veggies options
        Random randomGenerator = new Random();
        int randomIndex = randomGenerator.nextInt(veggiesOptions.size());
        String choice = veggiesOptions.get(randomIndex);

        // process the choice
        if (choice.equalsIgnoreCase("no")) {
            // do not update any counter
            currentBurrito += choice + " veggies, ";
            return false;
        } else if (choice.equalsIgnoreCase("all")) {
            for (int i = 0; i < veggiesCount.length; i++) {
                currentBurrito += veggiesOptions.get(i) + ", ";
                currentBurritoCount += 1;
                veggiesCount[i] += 1;
            }
            ingredientCategories +=1;
            return true;
        } else {
            currentBurrito += choice + ", ";
            currentBurritoCount += 1;
            veggiesCount[randomIndex] += 1;        // randomIndex can be used as index b/c it's not "no" or "all"
            ingredientCategories +=1;
            return true;
        }

    } // end chooseVeggies()

    private static boolean chooseSalsa() {

        // get the randomly selected option from the salsa options
        Random randomGenerator = new Random();
        int randomIndex = randomGenerator.nextInt(salsaOptions.size());
        String choice = salsaOptions.get(randomIndex);

        // process the choice
        if (choice.equalsIgnoreCase("no")) {
            // do not update any counter
            currentBurrito += choice + " salsa, ";
            return false;
        } else if (choice.equalsIgnoreCase("all")) {
            for (int i = 0; i < salsaCount.length; i++) {
                currentBurrito += salsaOptions.get(i) + " salsa, ";
                currentBurritoCount += 1;
                salsaCount[i] += 1;
            }
            ingredientCategories +=1;
            return true;
        } else {
            currentBurrito += choice + " salsa, ";
            currentBurritoCount += 1;
            salsaCount[randomIndex] += 1;        // randomIndex can be used as index b/c it's not "no" or "all"
            ingredientCategories +=1;
            return true;
        }

    } // end chooseSalsa()

    private static boolean chooseBeans() {

        // get the randomly selected option from the beans options
        Random randomGenerator = new Random();
        int randomIndex = randomGenerator.nextInt(beansOptions.size());
        String choice = beansOptions.get(randomIndex);

        // process the choice
        if (choice.equalsIgnoreCase("no")) {
            // do not update any counter
            currentBurrito += choice + " beans, ";
            return false;
        } else if (choice.equalsIgnoreCase("all")) {
            for (int i = 0; i < beansCount.length; i++) {
                currentBurrito += beansOptions.get(i) + " beans, ";
                currentBurritoCount += 1;
                beansCount[i] += 1;
            }
            ingredientCategories +=1;
            return true;
        } else {
            currentBurrito += choice + " beans, ";
            currentBurritoCount += 1;
            beansCount[randomIndex] += 1;        // randomIndex can be used as index b/c it's not "no" or "all"
            ingredientCategories +=1;
            return true;
        }

    } // end chooseBeans()

    private static boolean chooseMeat() {

        // get the randomly selected option from the meat options
        Random randomGenerator = new Random();
        int randomIndex = randomGenerator.nextInt(meatOptions.size());
        String choice = meatOptions.get(randomIndex);

        // process the choice
        if (choice.equalsIgnoreCase("no")) {
            // do not update any counter
            currentBurrito += choice + " meat, ";
            return false;
        } else if (choice.equalsIgnoreCase("all")) {
            for (int i = 0; i < meatCount.length; i++) {
                currentBurrito += meatOptions.get(i) + ", ";
                currentBurritoCount += 1;
                meatCount[i] += 1;
            }
            ingredientCategories +=1;
            return true;
        } else {
            currentBurrito += choice + ", ";
            currentBurritoCount += 1;
            meatCount[randomIndex] += 1;        // randomIndex can be used as index b/c it's not "no" or "all"
            ingredientCategories +=1;
            return true;
        }

    } // end chooseMeat()

    private static boolean chooseRice() {

        // get the randomly selected option from the rice options
        Random randomGenerator = new Random();
        int randomIndex = randomGenerator.nextInt(riceOptions.size());
        String choice = riceOptions.get(randomIndex);

        // process the choice
        if (choice.equalsIgnoreCase("no")) {
            // do not update any counter
            currentBurrito += choice + " rice, ";
            return false;
        } else if (choice.equalsIgnoreCase("all")) {
            for (int i = 0; i < riceCount.length; i++) {
                currentBurrito += riceOptions.get(i) + " rice, ";
                currentBurritoCount += 1;
                riceCount[i] += 1;
            }
            ingredientCategories +=1;
            return true;
        } else {
            currentBurrito += choice + " rice, ";
            currentBurritoCount += 1;
            riceCount[randomIndex] += 1;        // randomIndex can be used as index b/c it's not "no" or "all"
            ingredientCategories +=1;
            return true;
        }

    } // end chooseRice()

} // end class


// **************************************************
// testing code... insert at the beginning of main()
// **************************************************

//// testing choosing for boolean toppings ... and options, too!... code
//        for (int i = 0; i < 10; i++) {
//
//        currentBurrito = "";
//        chooseRice();
//        chooseMeat();
//        chooseBeans();
//        chooseSalsa();
//        chooseVeggies();
//        chooseCheese();
//        chooseGuac();
//        chooseQueso();
//        chooseSourCream();
//
//        System.out.println("TEST ORDER #" + (i + 1) + " is: " + currentBurrito);
//        System.out.println("the current counts are:  " +
//        "\nriceCount: " + Arrays.toString(riceCount) +
//        "\nmeatCount: " + Arrays.toString(meatCount) +
//        "\nbeansCount: " + Arrays.toString(beansCount) +
//        "\nveggiesCount: " + Arrays.toString(veggiesCount) +
//        "\ncheeseCount: " + cheeseCount +
//        "\nguacCount: " + guacCount +
//        "\nquesoCount: " + quesoCount +
//        "\nsourCreamCount: " + sourCreamCount + "\n");
//
//        } // end test loop



//// testing choosing with options code
//        for (int i = 0; i < 10; i ++) {
//        currentBurrito = "";
//
//        chooseRice();
//        chooseMeat();
//        chooseBeans();
//        chooseSalsa();
//        chooseVeggies();
//
//        System.out.println("order #" + (i+1) + " is: " + currentBurrito);
//        System.out.println("the current counts are:  " +
//        "\nriceCount: " + Arrays.toString(riceCount) +
//        "\nmeatCount: " + Arrays.toString(meatCount) +
//        "\nbeansCount: " + Arrays.toString(beansCount) +
//        "\nveggiesCount: " + Arrays.toString(veggiesCount));
//        }