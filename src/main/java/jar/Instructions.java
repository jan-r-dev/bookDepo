package jar;

public class Instructions {
    int choice;

    static void printDividingLine() {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    static void instructionScreen() {
        System.out.println(
                "Type 1 to retrieve an entry.\nType 2 to insert a new entry.\nType 3 to modify an entry.\nType 4 to retrieve recommendations.\nType exit to exit.");
    }

    static void instructionsRetrieveNameEntry() {
        System.out.println("Please type the name of the book you would like to retrieve or type exit to exit.");
    }

    static void instructionsInsertNameEntry() {
        System.out.println(
                "Please type the name of the book you would like to insert into the database or type exit to exit.");
    }

    static void instructionsRetrievePropertyEntry() {
        System.out.println("Please type the property of the book you would like to retrieve.");
        System.out.println("Choices are: title, language, author, pagecount");
    }

    static void instructionsRetrieveFinal(String title, String language, String author, int pageCount) {
        System.out.println("I am testing filler thing");
    }

    static void instructionsExit() {
        System.out.println("Thank you for partaking.");
    }

}
