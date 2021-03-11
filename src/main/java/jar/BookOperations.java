package jar;

import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.UUID;

public class BookOperations {

    public static int insertionInterface(Scanner input, HashMap<String, String> book) {
        String bookTitle = book.get("title");
        boolean repetitionCheck = true;
        int mainFlow = 0;

        while (repetitionCheck) { // Valid input check

            System.out.println(
                    "Type 1 to add information about '" + bookTitle + "' or type 2 to return to the main menu.");
            String selection = input.nextLine(); // Choose whether to add property values to a book

            switch (selection) {
            case "1":
                insertNewBookPropertyValue(book, input);
                Instructions.printDividingLine();
                System.out.println("Book value entry for '" + bookTitle + "' finalised. Returning to main menu.");
                Instructions.printDividingLine();
                mainFlow = 0;
                repetitionCheck = false;
                break;
            case "2":
                mainFlow = 0;
                repetitionCheck = false;
                break;
            default:
                System.out.println("Incorrect input.");
            }

        }
        return mainFlow;
    }

    public static UUID bookUUIDFinder(HashMap<UUID, HashMap<String, String>> bookDepo, Scanner input) {
        boolean entryOngoing = true;
        boolean bookFound = false;
        UUID retrievedID = null;

        while (entryOngoing) {
            System.out.println("Please type the name of the book.");
            String selection = input.nextLine();
            retrievedID = bookTitlePresentCheck(bookDepo, selection);

            if (retrievedID == null) {
                System.out.println("Book not found.");
                entryOngoing = Generics.tryAgainCheck(input, true);
            } else {
                entryOngoing = false;
                bookFound = true;
            }
        }

        if (!bookFound)
            retrievedID = null;

        return retrievedID;
    }

    public static HashMap<String, String> bookCreationInterface(Scanner input, ArrayList<String> bookProperties,
            HashMap<UUID, HashMap<String, String>> testedBookDepo) {
        System.out.println("Type the title of the book you would like to enter.");
        HashMap<String, String> newBook = null;
        String newTitle = input.nextLine();
        boolean notDuplicate = (bookTitlePresentCheck(testedBookDepo, newTitle) == null) ? true : false;

        if (notDuplicate) {
            newBook = new HashMap<String, String>();

            for (String el : bookProperties) {
                if (el == "title") {
                    newBook.put(el, newTitle);
                } else {
                    newBook.put(el, null);
                }
            }
            return newBook;
        } else {
            System.out.println("Duplicate entry. Cannot enter title.");
            return newBook;
        }

    }

    public static ArrayList<String> initBookProperties() {
        String[] bookPropertiesInit = Writer.retrieveBookProperties();
        ArrayList<String> bookProperties = new ArrayList<String>();

        for (String i : bookPropertiesInit) {
            bookProperties.add(i);
        }

        return bookProperties;
    }

    public static void insertNewBookPropertyValue(HashMap<String, String> selectedBook, Scanner inputField) {
        boolean entryOngoing = true;

        while (entryOngoing) {

            System.out.println("'" + selectedBook.get("title") + "'" + " has the following properties:");
            bookPropertiesPrint(selectedBook);
            System.out.println("Type the name of the property you would like to modify.");

            while (true) {
                String inputProperty = inputField.nextLine();
                if (selectedBook.containsKey(inputProperty)) {
                    System.out.println("Type in the value of " + inputProperty);
                    String inputValue = inputField.nextLine();
                    selectedBook.put(inputProperty, inputValue);

                    entryOngoing = (Generics.tryAgainCheck(inputField, false)) ? true : false;
                    break;
                } else {
                    System.out.println("Property does not exist.");
                    System.out.println("Try again.");
                }
            }
        }
    }

    public static UUID bookTitlePresentCheck(HashMap<UUID, HashMap<String, String>> testedBookDepo, String testedString) {
        UUID presentUUID = null;

        for (UUID el : testedBookDepo.keySet()) {

            if (testedBookDepo.get(el).get("title").equals(testedString)) {
                presentUUID = el;
                break;
            }
        }

        return presentUUID;
    }

    public static void bookPropertiesPrint(HashMap<String, String> selectedBook) {
        Instructions.printDividingLine();
        for (String el : selectedBook.keySet()) {
            if (!el.equals("UUID")) System.out.println(el + " " + selectedBook.get(el));
        }
        Instructions.printDividingLine();
    }


    
}
