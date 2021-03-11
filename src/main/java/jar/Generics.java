package jar;

import java.util.Scanner;

public class Generics {

    public static int returnToMainMenuCheck(Scanner input) {
        System.out.println("Type 1 to return to the main menu. Type exit to exit.");
        String selection = input.nextLine();

        int mainFlow = (selection.equals("1")) ? 0 : 99;

        return mainFlow;
    }


    public static boolean chooseOutput(Scanner input) {
        boolean entryOngoing = true;
        boolean printToText = false;

        while (entryOngoing) {
            System.out.println("Would you like to save output to a text file? Y/N");
            String selection = input.nextLine();

            if (selection.equals("Y") || selection.equals("y")) {
                entryOngoing = false;
                printToText = true;
                break;
            } else if (selection.equals("N") || selection.equals("n")) {
                entryOngoing = false;
                printToText = false;
                break;
            } else {
                System.out.println("Incorrect input. Restarting.");
                continue;
            }
        }

        return printToText;
    }


    public static boolean tryAgainCheck(Scanner inputField, boolean errorMessage) {
        boolean continueTrying = false;
        boolean looping = true;
        String repeatMessage = (errorMessage) ? "Would you like to try again? Y/N" : "Complete. Would you like to repeat? Y/N";

        while (looping) {
            System.out.println(repeatMessage);
            String selection = inputField.nextLine();

            if (selection.equals("Y") || selection.equals("y")) {
                continueTrying = true;
                looping = false;
                break;
            } else if (selection.equals("N") || selection.equals("n")) {
                looping = false;
                break;
            } else {
                System.out.println("Incorrect input. Restarting.");
                continue;
            }
        }
        return continueTrying; 
    }


}
