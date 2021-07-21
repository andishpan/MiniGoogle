package bigigoogle;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.*;

import java.util.stream.Collectors;

import static spark.Spark.*;

import spark.template.jade.JadeTemplateEngine;
import spark.ModelAndView;

import javax.servlet.MultipartConfigElement;

public class MiniGoogle {


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

    public MiniGoogle() {

    }

    public MiniGoogle(String URL, String alt, String title) {
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


    public static  InputStream inputStream = ClassLoader.getSystemResourceAsStream("comics.csv");

    private static final List<String> comics = new ArrayList<String>();

    private static List<MiniGoogle> readComicsFromCSV() throws FileNotFoundException {
        CSVParser csvParser;
        Scanner scanner;

        csvParser = new CSVParser();
        scanner = new Scanner(inputStream);
        List<MiniGoogle> comics = new ArrayList<>();



        try {

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();

                String[] words = csvParser.parseLine(line);

                MiniGoogle comic = createComic(words);

                comics.add(comic);


            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return comics;
    }


    private static MiniGoogle createComic(String[] metadata) {
        String URL = metadata[0].replace("\"", "");      // ignore quotes for links
        String alt = metadata[1];
        String title = metadata[2];

        return new MiniGoogle(URL, alt, title);
    }


    public static void main(String[] args) throws IOException, CsvValidationException {


        List<MiniGoogle> comics = readComicsFromCSV();

        staticFiles.location("/public");

        get("/search", (req, res) -> {

            Map<String, Object> mapa = new HashMap<>();


            return new JadeTemplateEngine().render(new ModelAndView(mapa, "search"));
        });

        get("/hello", (req, res) -> {

         return "Hello ";



        });


        get("/query", (req, res) -> {

            Map<String, Object> model = new HashMap<String, Object>();
            MultipartConfigElement multipartConfigElement = new MultipartConfigElement("C:\\Files\\XKCD.csv");
            req.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);

            String comicName = req.queryParams("term");


            List<MiniGoogle> output = null;
            String  caseSensitive = req.queryParams("sensitive");
            String wholeWord = req.queryParams("wholeWord");


       // if the term exists, if the input field isn't empty
    if (comicName != null && !comicName.isEmpty() && comics.stream().anyMatch(c -> c.getTitle().contains(comicName))) {


        if(wholeWord != null && caseSensitive != null){
            output = comics.stream()
                    .filter(comic -> comic.getTitle().matches(".*\\b" + comicName + "\\b.*"))   // whole word with case sensitivity

                    .collect(Collectors.toList());

            model.put("comics", output);

        }


//ignores case and returns only whole words
        if(wholeWord != null){
            output = comics.stream()
                    .filter(comic -> comic.getTitle().matches("(?i).*" + ".*\\b" + comicName + "\\b.*" + ".*"))              //The matches() method takes a String to represent the regular expression.
                                                                                                                                   // (?i) enables case-insensitivity and .* uses every character except line breaks.
                                                                                                                     //  \\b is used for space, this way we get the whole word and not the word as a part of another word
                    .collect(Collectors.toList());

            model.put("comics", output);
        }




        if(caseSensitive != null ){



            output = comics.stream()
                    .filter(comic -> comic.getTitle().contains(comicName))
                    .collect(Collectors.toList());
            model.put("comics", output);


        }

        if (caseSensitive==null && wholeWord==null){
            output = comics.stream()
                    .filter(comic -> comic.getTitle().matches("(?i).*" + comicName + ".*"))           //The matches() method takes a String to represent the regular expression.
                                                                                                           // (?i) enables case-insensitivity and .* uses every character except line breaks.
                    .collect(Collectors.toList());
            model.put("comics", output);

        }

        if(wholeWord != null && caseSensitive != null){
            System.out.println("it works");
            output = comics.stream()
                    .filter(comic -> comic.getTitle().matches(".*\\b" + comicName + "\\b.*"))   // whole word with case sensitivity

                    .collect(Collectors.toList());

            model.put("comics", output);

        }

        if (output.size()==0){                        //if input field is empty show error page
            res.redirect("/fail.html");
        }



    }else{
        res.redirect("/fail.html");
    }

            if(wholeWord != null){
                return new JadeTemplateEngine().render(new ModelAndView(model, "comics")) + "Whole word results for : " + " " + comicName;
            }

            if(caseSensitive != null){
                return new JadeTemplateEngine().render(new ModelAndView(model, "comics")) + " Your Sensitive Case for : " + " " + comicName;
            }

            return new JadeTemplateEngine().render(new ModelAndView(model, "comics")) + "Your insensitive Case for : " + " " + comicName;



        });


// post for our error page
        post("/fail", (request, response) -> {
            response.redirect("/search");

            return null;
        });


    }

}
