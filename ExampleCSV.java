



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
        String URL = metadata[0].replace("\"", "");      // ignore quotes for links
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

        //tested res.redirect("/hello");
        get("/hello", (req, res) -> {

            Map<String, Object> mapa = new HashMap<>();


            return "Hello";
        });


        get("/search", (req, res) -> {

            Map<String, Object> mapa = new HashMap<>();


            return new JadeTemplateEngine().render(new ModelAndView(mapa, "search"));
        });


        get("/query", (req, res) -> {

            Map<String, Object> model = new HashMap<String, Object>();
            MultipartConfigElement multipartConfigElement = new MultipartConfigElement("C:\\Files\\XKCD.csv");
            req.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);

            String string = req.queryParams("term");


            //System.out.println(string);

          /*  for(ExampleCSV c : testList){         // for each loop is not needed to get data
                model.put("comics", comics);
            }       */

            List<ExampleCSV> output = null;
            List<ExampleCSV> patt = null;
            String  caseSensitive = req.queryParams("sensitive");
            String wholeWord = req.queryParams("wholeWord");

            if(wholeWord != null){
             patt = comics.stream()
                        .filter(comic -> comic.getTitle().toLowerCase().matches(".*\\b" + string.toLowerCase() + "\\b.*"))   

                .collect(Collectors.toList());

               model.put("comics", patt);
            }


            if(wholeWord != null && caseSensitive != null){
                patt = comics.stream()
                        .filter(comic -> comic.getTitle().matches(".*\\b" + string + "\\b.*"))   

                        .collect(Collectors.toList());

                model.put("comics", patt);

            }


            if(caseSensitive != null ){


                output = comics.stream()
                        .filter(comic -> comic.getTitle().contains(string))
                        .collect(Collectors.toList());
                model.put("comics", output);

            }else{
                output = comics.stream()
                        .filter(comic -> comic.getTitle().toLowerCase().contains(string.toLowerCase()))
                        .collect(Collectors.toList());
                model.put("comics", output);

            }

            if(wholeWord != null){
                model.put("comics", patt);
            }





            // res.redirect("https://xkcd.com/2476/");     // this works

            return new JadeTemplateEngine().render(new ModelAndView(model, "comics"));

        });


       // redirect.post("/query", "/search", Redirect.Status.SEE_OTHER);






    }

}
