package jar;

import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import org.json.simple.JSONArray;

class App {

    public static void main(String[] args) {
        ArrayList<String> bookProperties = BookOperations.initBookProperties();

        JSONArray bookList = Writer.initJSON("bookDepoTest.json");
        HashMap<UUID, HashMap<String, String>> bookDepo = Writer.initHashmap(bookList);

        int mainFlow = 0;
        Scanner input = new Scanner(System.in);

        while (mainFlow != 99) {

            if (mainFlow == 0) { // Initial screen
                Instructions.instructionScreen();
                String selection = input.nextLine();
                String[] validOptions = { "1", "2", "3", "4" };

                if (Arrays.asList(validOptions).contains(selection)) {
                    mainFlow = Integer.parseInt(selection);
                } else if (selection.equals("exit")) {
                    mainFlow = 99;
                } else {
                    System.out.println("Incorrect input.");
                }

            } else if (mainFlow == 1) { // Book retrieval
                System.out.println("Entering retrieval menu.");
                UUID retrievedBook = BookOperations.bookUUIDFinder(bookDepo, input);

                if (retrievedBook == null) {
                    System.out.println("Aborting entry");
                    mainFlow = Generics.returnToMainMenuCheck(input);
                } else {
                    String newBookTitle = bookDepo.get(retrievedBook).get("title");
                    System.out.println("Book " + newBookTitle
                            + " has been found.\nPrinting available information about " + newBookTitle + ".");
                    BookOperations.bookPropertiesPrint(bookDepo.get(retrievedBook));
                    mainFlow = Generics.returnToMainMenuCheck(input);
                }

            } else if (mainFlow == 2) { // Book Insertion
                System.out.println("Entering insertion menu.");
                HashMap<String, String> newBook = BookOperations.bookCreationInterface(input, bookProperties, bookDepo);

                if (newBook == null) {
                    System.out.println("Aborting entry");
                    mainFlow = Generics.returnToMainMenuCheck(input);
                } else {
                    UUID newBookUUID = UUID.randomUUID(); // Use a unique id for books instead of their titles
                    newBook.put("UUID", newBookUUID.toString());
                    bookDepo.put(newBookUUID, newBook); // Inserts new book with properties and title

                    System.out.println("Book '" + newBook.get("title") + "' has been inserted.");
                    mainFlow = Generics.returnToMainMenuCheck(input);
                }

            } else if (mainFlow == 3) { // Book modification
                System.out.println("Entering modification menu.");
                UUID retrievedBook = BookOperations.bookUUIDFinder(bookDepo, input);

                if (retrievedBook == null) {
                    mainFlow = (Generics.returnToMainMenuCheck(input));
                } else {
                    BookOperations.insertNewBookPropertyValue(bookDepo.get(retrievedBook), input);
                    mainFlow = (Generics.returnToMainMenuCheck(input));
                }
            } else if (mainFlow == 4) { // Book recommendations listed 
                ArrayList<UUID> recommendedBooks = RecOperations.retrieveRecommended(bookDepo);
                boolean printingMenu = false;

                if (recommendedBooks.size() == 0) {
                    System.out.println("No recommendations available. Returning to main menu.");
                    mainFlow = 0;
                } else {
                    printingMenu = true;
                    mainFlow = 0;
                }

                if (printingMenu) {
                    boolean printToTextFile = Generics.chooseOutput(input);

                    if (printToTextFile) {
                        System.out.println("Recommendations found. Printing to text file now.");
                        RecOperations.printRecommendedToFile(recommendedBooks, bookDepo);
                    } else {
                        System.out.println("Recommendations found. Printing to command line now.");
                        RecOperations.printRecommendedToTerminal(recommendedBooks, bookDepo);
                    }

                    System.out.println("Completed. Returning to main menu.");
                }
            }
        }

        Writer.saveToJSON(bookDepo, "bookDepoTest.json");
        Instructions.instructionsExit();
        input.close();
    }
}