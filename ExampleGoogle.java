


package bigigoogle;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static spark.Spark.*;
import spark.template.jade.JadeTemplateEngine;
import spark.ModelAndView;

import javax.servlet.MultipartConfigElement;

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


  /*  private static Map<String, Object> checkCSV() throws FileNotFoundException {

       // List<ExampleCSV> comics = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        String strFile = "C:\\Files\\XKCD.csv";
        String line = "";
        List<String> result = new ArrayList<String>();
        Map<String, Object> map = new HashMap<String, Object>();

        BufferedReader br = new BufferedReader(new FileReader(strFile));


        try {

            while ((line = br.readLine()) != null) {


                String[] attributes = line.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");



                for(int i = 0; i < attributes.length; i++) {
                    if (!map.containsKey(attributes[i])) {


                        result.add(line);
                        map.put(attributes[i], result);//assigns a key to an ArrayList that stores the value
                    } else {

                        result = (ArrayList<String>) map.get(attributes[i]);
                        result.add(line);
                    }



                }


            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        map.get(input.nextLine());

        return map;

    }    */


    private static ExampleCSV createComic(String[] metadata) {
        String URL = metadata[0];
        String alt = metadata[1];
        String title = metadata[2];

        return new ExampleCSV(URL, alt, title);
    }


    public static void main(String[] args) throws IOException, CsvValidationException {


        List<ExampleCSV> comics = readComicsFromCSV();






            // output.forEach(c -> System.out.println(c.getTitle()));




     /*   for (ExampleCSV c : comics) {
            System.out.println(c);
        }   */



       staticFiles.location("/public");


        get("/search", (req, res) -> {

            Map<String, Object> mapa= new HashMap<>();



            return new JadeTemplateEngine().render(new ModelAndView(mapa, "search"))  ;
        });





       post("/query", (req, res) ->{

            Map<String, Object> model = new HashMap<String, Object>();
            MultipartConfigElement multipartConfigElement = new MultipartConfigElement("C:\\Files\\XKCD.csv");
            req.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);

          String string =  req.queryParams("term");

//System.out.println(string);

          /*  for(ExampleCSV c : testList){         // for each loop is not needed to get data
                model.put("comics", comics);
            }       */

           List<ExampleCSV>  output = comics.stream()
                   .filter(comic -> comic.getTitle().contains(string) )
                   .collect(Collectors.toList());

            model.put("comics", output);


            return new JadeTemplateEngine().render(new ModelAndView(model, "comics"))    ;


        });


    }


