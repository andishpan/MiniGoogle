package bigigoogle;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import java.util.*;
import static spark.Spark.*;
import spark.template.jade.JadeTemplateEngine;
import spark.ModelAndView;

public class ExampleCSV {


    String title;
    String alt;
    String URL;


    public void setTitle(String title) {
        this.title = title;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }


    public static String strFile = "C:\\Files\\XKCD.csv";

    public static CSVReader reader;
    public static String[] nextLine;
    public static int lineNumber = 0;

    public ExampleCSV() {

    }

    public ExampleCSV(String URL, String alt, String title) {
        this.URL = URL;
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
        return URL;
    }

    @Override
    public String toString() {
        return "Comic [url" + URL + ", alt =" + alt + ", title =" + title + "]";
    }


    public Iterable<String> getAllComics() {
        return comics;
    }


    public static List<String> urlList = new ArrayList<String>();

    public static List<String> altList = new ArrayList<String>();

    public static List<String> titleList = new ArrayList<String>();

    public static HashMap<String, Object> data = new HashMap<String, Object>();

    private static final List<String> comics = new ArrayList<String>();

    private static List<ExampleCSV> readComicsFromCSV() throws FileNotFoundException {
        List<ExampleCSV> comics = new ArrayList<>();
        String strFile = "C:\\Files\\XKCD.csv";
        String line = "";

        BufferedReader br = new BufferedReader(new FileReader(strFile));


        try {

            while ((line = br.readLine()) != null) {


                String[] attributes = line.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");

                ExampleCSV comic = createComic(attributes);

                comics.add(comic);


            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return comics;
    }


    private static ExampleCSV createComic(String[] metadata) {
        String URL = metadata[0];
        String alt = metadata[1];
        String title = metadata[2];

        return new ExampleCSV(URL, alt, title);
    }


    public static void main(String[] args) throws IOException, CsvValidationException {


        List<ExampleCSV> comics = readComicsFromCSV();


     /*   for (ExampleCSV c : comics) {
            System.out.println(c);
        }   */



        staticFiles.location("/public");


        get("/search", (req, res) -> {

            Map<String, Object> mapa= new HashMap<>();



            return new JadeTemplateEngine().render(new ModelAndView(mapa, "search"))  ;
        });



        get("/query", (req, res) ->{

            Map<String, Object> model = new HashMap<String, Object>();

            for(ExampleCSV c : comics){
                model.put("comics", comics);
            }


            return new JadeTemplateEngine().render(new ModelAndView(model, "comics"))  ;


        });


    }





}
