/**
 * Week 2 Challenge: Chiptole Jr +
 * Ksenia Lake
 * Thursday, October 31, 2019
 */

import java.util.*;

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

    // This will track our order's total cost, for part 3
    private static double totalOrderCost = 0;

    // ****************************************************************************
    // Very important! Each of these variables, below, and up to bottom "****"s,
    // must be re-initialized between uses... they are used on a per-burrito basis!
    // ****************************************************************************
    // This will store our burrito!
    private static String currentBurrito;

    // This will store the number of ingredients in our current burrito
    // will be used to calculate the burrito's cost
    private static int currentBurritoCount;

    // this tracks how many ingredient categories we've used so far.
    // assignment requires 5 to 9 are used each time. ("all" counts as one hit).
    private static int ingredientCategories;

    // this flags whether this ingredient has already been included or not...
    // used when relooping through categories to get the burrito up to at least 5
    private static boolean[] flagCategories = new boolean[allIngredients.size()];
    // ****************************************************************************

    public static void main(String[] args) {

        // *********************************************
        // wrap everything in a loop that runs 25 times
        //**********************************************
        for (int i = 0; i < 25; i++) {

            // initialize class-level variables before each new burrito... very important!...
            currentBurrito = "";
            currentBurritoCount = 0;
            ingredientCategories = 0;
            Arrays.fill(flagCategories, false);

            // make sure we always get at least 5 ingredient categories represented
            // the handling of maximum 9 categories is done inside loopIngredients()
            while(ingredientCategories < 5) {
                loopIngredients();
            }

            // ************************************************************************************
            // PARTS 1 + 2 : print out each burrito, its ingredients, and its price, using methods
            // ************************************************************************************
            System.out.print("Burrito " + (i+1) +": " + currentBurrito);
            System.out.printf("$%.2f%n%n", calculateBurritoPrice());


        } // end the make 25 burritos loop!
        //****************************************************************

        //****************************************************************
        // PART 3 : get all the ingredients from the order and display them
        //****************************************************************
        String fullReceipt = displayFullOrderReceipt();
        System.out.println(fullReceipt);

        //*****************************************************************
        // PART 4 : wrap at 50 characters, even if in the middle of a word
        //*****************************************************************
        String hardWrappedReceipt = wrapOutput(fullReceipt, 50);
        System.out.println("Hard Wrapped Receipt: ");
        System.out.println(hardWrappedReceipt);

        //****************************************************************
        // PART 5 : wrap at 50 characters, on space (e.g. between words)
        //****************************************************************
        String wordWrappedReceipt = wrapOutputWordWise(fullReceipt, 50);
        System.out.println("Word Wrapped Receipt: ");
        System.out.println(wordWrappedReceipt);

    } // end main

    //*****************************************************************
    // PART 4 : wrap at 50 characters, even if in the middle of a word
    // I built part 4 after finding part 5, so same credit once again to the base code
    //*****************************************************************
    private static String wrapOutput(String str, int wrapLength) {

        int inputLineLength = str.length();
        int extra = 32;  // this adds some extra length to our StringBuilder for new-line chars and etc...
        StringBuilder wrappedLine = new StringBuilder(inputLineLength + extra);

        int offset = 0;
        while (inputLineLength - offset > wrapLength) {

            // String.substring(int beginIndex, int endIndex) returns a String which starts at beginIndex
            // and extends to (endIndex - 1)
            wrappedLine.append(str.substring(offset, wrapLength + offset));
            wrappedLine.append("\n");

            // because substring is exclusive of the end bound,
            // we set the new offset to the old offset plus just wrapLength (not wrapLength + 1)
            offset = offset + wrapLength;
        }
        // this is the last append, once we have less than wrapLength chars left in the string
        wrappedLine.append(str.substring(offset));

        return wrappedLine.toString();

    }

    //****************************************************************
    // PART 5 : wrap at 50 characters, on space (e.g. between words)
    // credit to :
    // http://commons.apache.org/proper/commons-lang/javadocs/api-3.1/src-html/org/apache/commons/lang3/text/WordUtils.html#line.32
    // for being the source I based my code off of.
    //****************************************************************
    private static String wrapOutputWordWise(String str, int wrapLength) {
        int inputLineLength = str.length();
        int offset = 0;
        int extra = 32;  // this adds some extra length to our StringBuilder for new-line chars and etc...

        StringBuilder wrappedLine = new StringBuilder(inputLineLength + extra);

        while(inputLineLength - offset > wrapLength) {

            // String.lastIndexOf(String str, int fromIndex)
            // Returns the index within this string of the last occurrence of the specified substring,
            // searching backward starting at the specified index.
            //
            // So,
            //       str.lastIndexOf(' ', wrapLength + offset);
            // will search backwards from 50 + 0 on the first go-round.
            // It searches for the first space it encounters, searching backwards,
            // and returns the index number for that space.
            int spaceToWrapAt = str.lastIndexOf(' ', wrapLength + offset);

            // once you find the spot to wrap at, do so.
            wrappedLine.append(str.substring(offset, spaceToWrapAt));
            wrappedLine.append("\n");

            // this sets us up for the next loop iteration
            offset = spaceToWrapAt + 1;

        } // end of while

        // now, we've popped out of the while. This means that our string that's left is shorter than wrapLength.
        // String substring​(int beginIndex) :: Returns a string that is a substring of this string. The substring
            // begins with the character at the specified index and extends to the end of this string.
        wrappedLine.append(str.substring(offset));

        return wrappedLine.toString();

    }

    private static String displayFullOrderReceipt() {
        String fullReceipt = "";
        fullReceipt += "This order has ";
        for(int i = 0; i < riceCount.length; i++) {
            if (riceCount[i] > 0) {
                fullReceipt += riceCount[i] + " " + riceOptions.get(i) + " rice, ";
            }
        }
        for(int i = 0; i < meatCount.length; i++) {
            if (meatCount[i] > 0) {
                fullReceipt += meatCount[i] + " " + meatOptions.get(i) + ", ";
            }
        }
        for(int i = 0; i < beansCount.length; i++) {
            if (beansCount[i] > 0) {
                fullReceipt += beansCount[i] + " " + beansOptions.get(i) + " beans, ";
            }
        }
        for(int i = 0; i < salsaCount.length; i++) {
            if (salsaCount[i] > 0) {
                fullReceipt += salsaCount[i] + " " + salsaOptions.get(i) + " salsa, ";
            }
        }
        for(int i = 0; i < veggiesCount.length; i++) {
            if (veggiesCount[i] > 0) {
                fullReceipt += veggiesCount[i] + " " + veggiesOptions.get(i) + ", ";
            }
        }
        if (cheeseCount > 0) {
            fullReceipt += cheeseCount + " cheese, ";
        }
        if (guacCount > 0) {
            fullReceipt += guacCount + " guac, ";
        }
        if (quesoCount > 0) {
            fullReceipt += quesoCount + " queso, ";
        }
        if (sourCreamCount > 0) {
            fullReceipt += sourCreamCount + " sour cream, ";
        }
        fullReceipt += String.format("and the sum is $%.2f%n", totalOrderCost);

        return fullReceipt;

    } // end display full order receipt method

    private static double calculateBurritoPrice() {
        // calculates the price of the current burrito
        // references the class-level variable currentBurritoCount
        double currentBurritoCost = 3.00 + (currentBurritoCount * .50);

        // saves the current burrito's cost into the total order cost
        // note that we have only one order per instance of main() being run,
        // which is why this approach works :)
        totalOrderCost += currentBurritoCost;

        // returns current burrito's cost
        return currentBurritoCost;

    } // end calculate Burrito Price method

    private static void loopIngredients() {
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

            // in case we are at 9 categories :: don't try for more!
            if (ingredientCategories == 9) {
                break;
            }

        } // end loop

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
