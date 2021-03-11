package jar;

import java.util.UUID;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;

public class Writer {

    public static JSONArray initJSON(String filepath) {
        JSONArray bookList = null;

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filepath)) {
            //Read JSON file
            Object parsedReader = jsonParser.parse(reader);

            bookList = (JSONArray) parsedReader;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return bookList;
    }

    public static String[] retrieveBookProperties() {
        String[] bookPropertiesInit = { "title", "author", "pagecount", "language", "UUID", "recommendation", "country"};

        return bookPropertiesInit;
    }

    public static String retrieveFilename() {
        String filename = "bookDepoTest.json";
        return filename;
    }

    @SuppressWarnings("unchecked")

    public static void writeToJSON() {
        String[] bookPropertiesInit = retrieveBookProperties();
        String[] books = { "Idiot", "The Unfettered Mind", "Kafka on the Shore" };

        // JSONObject bookDepo = new JSONObject();
        JSONArray bookArray = new JSONArray();

        for (String el : books) {
            UUID bookId = UUID.randomUUID();
            JSONObject book = new JSONObject();

            for (String property : bookPropertiesInit) {
                book.put(property, "null");
            }

            book.put("title", el);
            book.put("UUID", bookId.toString());
            bookArray.add(book);
        }

        try (FileWriter file = new FileWriter(retrieveFilename())) {
            file.write(bookArray.toJSONString());
            file.flush();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static void saveToJSON(HashMap<UUID, HashMap<String, String>> bookDepo, String filepath) {
        JSONArray bookArray = new JSONArray();

        for (UUID bookId : bookDepo.keySet()) {
            HashMap<String, String> retrievedBook = bookDepo.get(bookId);
            JSONObject book = new JSONObject();

            for (String property : retrievedBook.keySet()) {
                if (property.equals("UUID")) {
                    book.put(property, bookId.toString());
                } else {
                    book.put(property, retrievedBook.get(property));
                }
            }
            bookArray.add(book);
        }

        try (FileWriter file = new FileWriter(filepath)) {
            file.write(bookArray.toJSONString());
            file.flush();

        } catch (IOException e) {
            System.out.println(e);
        }

    }

    @SuppressWarnings("unchecked")
    public static void readJSON(String filename) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filename)) {
            //Read JSON file
            Object parsedReader = jsonParser.parse(reader);

            JSONArray bookList = (JSONArray) parsedReader;
            bookList.forEach(book -> {
                initBook((JSONObject) book);
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> initBook(JSONObject book) {
        HashMap<String, String> newBook = new HashMap<String, String>();

        for (String propertyName : retrieveBookProperties()) {

            String propertyValue = (book.get(propertyName) == null) ? null : book.get(propertyName).toString();
            newBook.put(propertyName, propertyValue);
        }

        return newBook;
    }

    @SuppressWarnings("unchecked")

    public static HashMap<UUID, HashMap<String, String>> initHashmap(JSONArray bookList) {
        HashMap<UUID, HashMap<String, String>> newHashMap = new HashMap<UUID, HashMap<String, String>>();

        bookList.forEach(book -> {
            HashMap<String, String> newBook = initBook((JSONObject) book);
            UUID hashKey = UUID.fromString(newBook.get("UUID"));

            newHashMap.put(hashKey, newBook);
        });

        return newHashMap;
    }

    public static FileWriter createFileWriter(String filename, boolean toAppend) {
        FileWriter myWriter = null;

        try {
            myWriter = new FileWriter(filename, toAppend);
            return myWriter;
        } catch (IOException e) {
            System.out.println("An error occurred during FileWriter creation.");
            e.printStackTrace();
            return myWriter;
        }
    }

    public static void createTextFile(File fileObj) {
        try {
            fileObj.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void writeToFile(FileWriter fileWriterObj, String textInput) {
        try {
            fileWriterObj.write(textInput);
        } catch (IOException e) {
            System.out.println("An error occurred during the writing process.");
            e.printStackTrace();
        }
    }



}
