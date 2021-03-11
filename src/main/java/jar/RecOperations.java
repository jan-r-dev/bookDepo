package jar;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.UUID;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RecOperations {

    public static ArrayList<String> createRecommendedOutputList(HashMap<String, String> retrievedBook) {
        ArrayList<String> composedLine = new ArrayList<String>();

        String title = retrievedBook.get("title");
        String author = retrievedBook.get("author");
        String recommendation = retrievedBook.get("recommendation");

        composedLine.add(title);
        composedLine.add(author);
        composedLine.add(recommendation);

        return composedLine;
    }


    public static ArrayList<UUID> retrieveRecommended(HashMap<UUID, HashMap<String, String>> bookDepo) {
        ArrayList<UUID> recommendedBooks = new ArrayList<UUID>();

        for (UUID book : bookDepo.keySet()) {
            boolean isRecommended = (bookDepo.get(book).get("recommendation") == null) ? false : true;

            if (isRecommended)
                recommendedBooks.add(book);
        }

        return recommendedBooks;
    }

    public static void printRecommendedToTerminal(ArrayList<UUID> recommendedBooks, HashMap<UUID, HashMap<String, String>> bookDepo) {
        Instructions.printDividingLine();
        for (UUID bookID : recommendedBooks) {
            HashMap<String, String> retrievedBook = bookDepo.get(bookID);
            ArrayList<String> composedLine = createRecommendedOutputList(retrievedBook);

            System.out.println("Title: " + composedLine.get(0));
            System.out.println("Author: " + composedLine.get(1));
            System.out.println("Recommendation: " + composedLine.get(2));
            Instructions.printDividingLine();
        }
    }

    public static void printRecommendedToFile(ArrayList<UUID> recommendedBooks, HashMap<UUID, HashMap<String, String>> bookDepo) {
        String filename = "recommendations.csv";
        File fileObj = new File(filename);
        Writer.createFileWriter(filename, false);  // Wipe file before writing
        FileWriter fileWriterObj = Writer.createFileWriter(filename, true);
        int listSize = recommendedBooks.size();
        int counter = 0;

        for (UUID bookID : recommendedBooks) {
            counter += 1;
            HashMap<String, String> retrievedBook = bookDepo.get(bookID);
            ArrayList<String> composedLine = createRecommendedOutputList(retrievedBook);

            if (!fileObj.exists()) {
                Writer.createTextFile(fileObj);
            }

            if (counter != listSize) {
                Writer.writeToFile(fileWriterObj, composedLine.get(0) + ";" + composedLine.get(1) + ";" + composedLine.get(2) + "\n");
            } else {
                Writer.writeToFile(fileWriterObj, composedLine.get(0) + ";" + composedLine.get(1) + ";" + composedLine.get(2));
            }
        }
        try {
            fileWriterObj.close();
        } catch (IOException e) {
            // pass
        }
    }


    
}
