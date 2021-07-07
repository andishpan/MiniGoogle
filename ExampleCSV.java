
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;


import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExampleCSV {

    private final static String delimitter= ",";

   public static ArrayList<String> dataURL = new ArrayList<>();
   public static  ArrayList<String> altData= new ArrayList<>();
    public static ArrayList<String> titleData= new ArrayList<>();





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
            dataURL.add(nextLine[0]);
            altData.add(nextLine[1]);
            titleData.add(nextLine[2]);

        }
    }
}


