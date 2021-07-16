
package searchengine;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ExampleCSV {

    String title;
    String alt;
    String url;

    public ExampleCSV(String url, String alt, String title) {
        this.url = url;
        this.alt = alt;
        this.title = title;

    }

    public String getTitle() {
        return title;
    }

    public String getAlt() {
        return alt;
    }

    public String getURL() {
        return url;
    }

    public String toString(){
        return "Comic [url" + url + ", alt =" + alt + ", title =" + title + "]";
    }


    public Iterable<String> getAllComics() {
        return comics;
    }


   public static List<String> urlList = new ArrayList<String>();

    public static List<String> altList = new ArrayList<String>();

    public static List<String> titleList = new ArrayList<String>();

    public static HashMap<String, Object> data = new HashMap<String, Object>();

    private static final List<String> comics = new ArrayList<String>();


    public static void main(String[] args) throws IOException, CsvValidationException {

        List<List<String>> result = new ArrayList<>();


        CSVParser csvParser;
        Scanner scanner;
        //InputStream inputStream = ClassLoader.getSystemResourceAsStream("comics.csv");
        csvParser = new CSVParser();
        //scanner = new Scanner(inputStream);
        //String line = scanner.nextLine();
        //String[] words = csvParser.parseLine(line);
        // System.out.println("URL: " + words[0]);
        //System.out.println("alt: " + words[1]);
        //System.out.println("title: " + words[2]);

        String strFile = "C:\\Informatik 3\\Lab05\\minigoogle\\src\\main\\resources\\comics.csv";

        CSVReader reader = new CSVReader(new FileReader(strFile));
        String [] nextLine;
        int lineNumber = 0;
        while ((nextLine = reader.readNext()) != null) {
            lineNumber++;
            System.out.println("Line # " + lineNumber);

            // nextLine[] is an array of values from the line
            urlList.add(nextLine[0]);
            altList.add(nextLine[1]);
            titleList.add(nextLine[2]);

            comics.add(nextLine[2]);

        }

    }


}
