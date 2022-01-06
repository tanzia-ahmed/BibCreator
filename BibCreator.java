/*
* @author Ahmed-40166924
* */

import java.io.*;
import java.util.*;

public class BibCreator {
    private static ArrayList<ArrayList<Article>> articlesByFiles;
    private static ArrayList<Integer> invalidFileNum;
    private static int N = 10;
    public static void main (String []args){
        System.out.println("Welcome to BibCreator!");
        System.out.println();
        articlesByFiles = new ArrayList<>();
        articlesByFiles.add(null);
        boolean allFilesOpened = true;
        invalidFileNum = new ArrayList<>();
        Scanner scanner=null;
        String file = "Latex";
        //Reading files
        for(int i=1; i<=N; i++) {
            try {
                scanner = new Scanner(new FileInputStream(file+i+".bib"));

                System.out.println();

            } catch (FileNotFoundException e) {
                allFilesOpened = false;
                System.out.println("Could not open input file "+file+i+".bib for reading. \n" +
                        "Please check if file exists! Program will terminate after closing any opened files.");
                System.exit(0);
            }catch(NoSuchElementException e){
                //System.out.println(e.getMessage());
            }
            finally {
                scanner.close();
            }
        }

        //checking input file validity
        for(int count=1; count<=N; count++){
            Article article = null;
            BufferedReader bf = null;
            try {
                bf = new BufferedReader(new FileReader(file+count+".bib"));
                boolean result = processFilesForArticles(bf, article, count);
                if(!result){
                    invalidFileNum.add(count);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        System.out.println();
        System.out.println("A total of "+invalidFileNum.size()+" files were invalid, and could not be processed." +
                "All other "+(10-invalidFileNum.size())+" valid files have been created.");

        //Creating files
//        if(allFilesOpened){
//            PrintWriter pw = null;
//            for(int j=1; j<=N; j++) {
//                try {
//                    if(!invalidFileNum.contains(j))
//                        pw = new PrintWriter(new FileOutputStream("IEEE" +j+".json"));
//                } catch (FileNotFoundException e) {
//                    System.out.println("Could not open output file IEEE"+j+".json for writing. \n");
//                }
//                finally {
//                    pw.close();
//                }
//            }
//        }

        processFilesForValidation();

        System.out.println("Enter name of file that you want to review: ");
        Scanner sc = new Scanner(System.in);

        String file_name = sc.next();
        Scanner scanner2 =null;
        try {
            scanner2 = new Scanner(new FileInputStream(file_name));

            System.out.println();
            //do something
            while(scanner2.hasNextLine()){
                System.out.println(scanner2.nextLine());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Could not open input file "+file_name+" for reading. \n" +
                    "Please check if file exists!");
            System.out.println();
            System.out.println("However you get another chance to enter file name. \n"+
                    "Enter name of file that you want to review: ");

            try {
                 file_name = sc.next();
                scanner2 = new Scanner(new FileInputStream(file_name));

                System.out.println();
                //do something
                while(scanner2.hasNextLine()){
                    System.out.println(scanner2.nextLine());
                }

            } catch (FileNotFoundException ex) {
                System.out.println("Could not open input file "+file_name+" for reading. \n" +
                        "Please check if file exists!");
                System.exit(0);
            }

        }

    }

    private static void processFilesForValidation() {
        String iee_file = "IEEE";
        String acm_file = "ACM";
        String nj_file = "NJ";
        PrintWriter pw1 = null;
        PrintWriter pw2 = null;
        PrintWriter pw3 = null;
        for(int c=1; c<=N; c++){
            try {
                if(!invalidFileNum.contains(c)) {
                    pw1 = new PrintWriter(new FileOutputStream(iee_file + c + ".json"));
                    pw2 = new PrintWriter(new FileOutputStream(acm_file+c+".json"));
                    pw3= new PrintWriter(new FileOutputStream(nj_file+c+".json"));

                    writeinIEEEformat(pw1, c);
                    writeinACMformat(pw2, c);
                    writeinNJformat(pw3, c);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                pw1.close();
                pw2.close();
                pw3.close();
            }
        }

    }

    private static void writeinNJformat(PrintWriter pw, int j) {
        if(!invalidFileNum.contains(j)) {
            for (int i = 0; i < articlesByFiles.get(j).size(); i++) {
                Article article = articlesByFiles.get(j).get(i);
                String authStr = article.getAuthor();
                authStr = authStr.replace("and", "&");

                String nj = authStr.trim();

                nj = nj +  ". "+article.getTitle().trim() + ". " + article.getJournal().trim() + ". " +
                        article.getVolume().trim() + ", " + article.getPages().trim() + "("+article.getYear()+").";
                pw.println(nj);
                pw.println();
            }
        }
    }

    private static void writeinACMformat(PrintWriter pw, int j) {
        if(!invalidFileNum.contains(j)) {
            for (int i = 0; i < articlesByFiles.get(j).size(); i++) {
                Article article = articlesByFiles.get(j).get(i);
                String authStr = article.getAuthor();
                String []authStrs = authStr.split("and");
                ArrayList<String> authors = new ArrayList();
                for(int k=0; k<authStrs.length;k++){
                    authors.add(authStrs[k].trim());
                }
                String acm = "["+i+"] ";
                if(authStrs.length>1){
                    acm = acm+authStrs[0]+" et al";
                }else{
                    acm=acm+authStrs[0];
                }
                acm = acm +  ". " + article.getYear() +  ". "+article.getTitle().trim() + ". " + article.getJournal().trim() + ". " +
                         article.getVolume().trim() + ", " + article.getNumber() + " ("+article.getYear()+"),"+ article.getPages().trim()
                        + ". DOI:https://doi.org/" + article.getDoi().trim() +  ".";
                pw.println(acm);
                pw.println();
            }
        }
    }

    private static void writeinIEEEformat(PrintWriter pw, int j) {
        if(!invalidFileNum.contains(j)) {
            for (int i = 0; i < articlesByFiles.get(j).size(); i++) {
                Article article = articlesByFiles.get(j).get(i);
                String authStr = article.getAuthor();
                String []authStrs = authStr.split("and");
                ArrayList<String> authors = new ArrayList();
                for(int k=0; k<authStrs.length;k++){
                    authors.add(authStrs[k].trim());
                }
                String ieee = "";
                for (int c = 0; c < authors.size() - 1; c++) {
                    ieee = ieee + authors.get(c).trim() + ", ";
                }
                ieee = ieee + authors.get(authors.size() - 1).trim() + ". \"" + article.getTitle().trim() + "\", " + article.getJournal().trim() + ", " +
                        "vol. " + article.getVolume().trim() + ", no. " + article.getNumber() + ", p. " + article.getPages().trim()
                        + ", " + article.getMonth().trim() + " " + article.getYear() + ".";
                pw.println(ieee);
                pw.println();
            }
        }
    }
    
    private static boolean processFilesForArticles(BufferedReader bf, Article article, int i) throws IOException {
        boolean valid = true;
        ArrayList<Article> temp_articles = new ArrayList<>();
        String obj = bf.readLine();
        try {
            while (!obj.equals(null) || !obj.equals("")) {
//
                if (obj.contains("\n")) {

                } else if (obj.contains("@ARTICLE{")) {
                    article = new Article();
                    try {
                        readBody(bf, article);
                        if (valid) {
                            temp_articles.add(article);
                        }
//                    System.out.println(temp_articles.toString());
                    } catch (FileInvalidException e) {
                        valid = false;
                        System.out.println("Error: Detected Empty Field!");
                        System.out.println("============================");
                        System.out.println();
                        System.out.println("Problem detected with input file: Latex" + i + ".bib");
                        System.out.println(e.getMessage());
                        break;
                    } catch (IOException io) {
                        System.out.println(io.getMessage());
                    }
                }
                obj = bf.readLine();
            }
            bf.close();
            if (valid) {
                articlesByFiles.add(temp_articles);

            } else {
                articlesByFiles.add(null);

            }
            return valid;
        }catch (NullPointerException ne){
            if (valid) {
                articlesByFiles.add(temp_articles);

            } else {
                articlesByFiles.add(null);

            }
            bf.close();
            return valid;
        }
    }

    private static void readBody(BufferedReader bf, Article article) throws FileInvalidException, IOException {
        String obj = bf.readLine();
        while(!obj.equals(null)) {

            if( !obj.equals("}")) {
                if (obj.equals("\n")) {
                } else if (obj.contains("author=")) {
                    StringTokenizer st = new StringTokenizer(obj, "={");
                    st.nextToken();
                    String author = st.nextToken().replace("},", "");
                    if(author.equals(null) || author.equals("") || author.equals(" ")) {
                        throw (new FileInvalidException("File is invalid: Field \"author\" " +
                                "is Empty. Processing stopped at this point. Other empty fields may be present as well!"));

                    }else{
                        article.setAuthor(author);
                        //System.out.println(author);
                    }
                }else if (obj.contains("journal=")) {
                    StringTokenizer st = new StringTokenizer(obj, "={");
                    st.nextToken();
                    String journal = st.nextToken().replace("},", "");
                    if(journal.equals(null) || journal.equals("") || journal.equals(" ")) {
                        throw (new FileInvalidException("File is invalid: Field \"journal\" " +
                                "is Empty. Processing stopped at this point. Other empty fields may be present as well!"));

                    }else{
                        article.setJournal(journal);
                        //System.out.println(journal);
                    }
                }
                else if (obj.contains("title=")) {
                    StringTokenizer st = new StringTokenizer(obj, "={");
                    st.nextToken();
                    String title = st.nextToken().replace("},", "");
                    if(title.equals(null) || title.equals("") || title.equals(" ")) {
                        throw (new FileInvalidException("File is invalid: Field \"title\" " +
                                "is Empty. Processing stopped at this point. Other empty fields may be present as well!"));

                    }else{
                        article.setTitle(title);
                        //System.out.println(title);
                    }
                }
                else if (obj.contains("year=")) {
                    StringTokenizer st = new StringTokenizer(obj, "={");
                    st.nextToken();
                    String year = st.nextToken().replace("},", "");
                    if(year.contains(" "))
                        year = year.replace(" ","");
                    if(year.equals("") || year.equals(null) || year.equals(" ")) {
                        throw (new FileInvalidException("File is invalid: Field \"year\" " +
                                "is Empty. Processing stopped at this point. Other empty fields may be present as well!"));

                    }else{
                        article.setYear(Integer.parseInt(year));
                        //System.out.println(year);
                    }
                }
                else if (obj.contains("volume=")) {
                    StringTokenizer st = new StringTokenizer(obj, "={");
                    st.nextToken();
                    String volume = st.nextToken().replace("},", "");
                    if(volume.equals(null) || volume.equals("") || volume.equals(" ")) {
                        throw (new FileInvalidException("File is invalid: Field \"volume\" " +
                                "is Empty. Processing stopped at this point. Other empty fields may be present as well!"));

                    }else{
                        article.setVolume(volume);
                        //System.out.println(volume);
                    }
                }
                else if (obj.contains("number=")) {
                    StringTokenizer st = new StringTokenizer(obj, "={");
                    st.nextToken();
                    String number = st.nextToken().replace("},", "");
                    if(number.contains(" "))
                        number = number.replace(" ","");
                    if(number.equals(null) ||number.equals("")) {
                        throw (new FileInvalidException("File is invalid: Field \"number\" " +
                                "is Empty. Processing stopped at this point. Other empty fields may be present as well!"));

                    }else{
                        article.setNumber(Long.parseLong(number));
                        //System.out.println(number);
                    }
                }
                else if (obj.contains("pages=")) {
                    StringTokenizer st = new StringTokenizer(obj, "={");
                    st.nextToken();
                    String pages = st.nextToken().replace("},", "");
                    if(pages.equals(null) || pages.equals("") || pages.equals(" ")) {
                        throw (new FileInvalidException("File is invalid: Field \"pages\" " +
                                "is Empty. Processing stopped at this point. Other empty fields may be present as well!"));
                    }else{
                        article.setPages(pages);
                        //System.out.println(pages);
                    }
                }
                else if (obj.contains("keywords=")) {
                    StringTokenizer st = new StringTokenizer(obj, "={");
                    st.nextToken();
                    String keywords = st.nextToken().replace("},", "");
                    if(keywords.equals(null) || keywords.equals("") || keywords.equals(" ")) {
                        throw (new FileInvalidException("File is invalid: Field \"keywords\" " +
                                "is Empty. Processing stopped at this point. Other empty fields may be present as well!"));

                    }else{
                        article.setKeywords(keywords);
                        //System.out.println(keywords);
                    }
                }
                else if (obj.contains("doi=")) {
                    StringTokenizer st = new StringTokenizer(obj, "={");
                    st.nextToken();
                    String doi = st.nextToken().replace("},", "");
                    if(doi.equals(null) || doi.equals("") || doi.equals(" ")) {
                        throw (new FileInvalidException("File is invalid: Field \"DOI\" " +
                                "is Empty. Processing stopped at this point. Other empty fields may be present as well!"));

                    }else{
                        article.setDoi(doi);
                        //System.out.println(doi);
                    }
                }
                else if (obj.contains("ISSN=")) {
                    StringTokenizer st = new StringTokenizer(obj, "={");
                    st.nextToken();
                    String ISSN = st.nextToken().replace("},", "");

                    if(ISSN.equals(null) || ISSN.equals("") || ISSN.equals(" ")) {
                        throw (new FileInvalidException("File is invalid: Field \"ISSN\" " +
                                "is Empty. Processing stopped at this point. Other empty fields may be present as well!"));

                    }else{
                        article.setISSN(ISSN);
                        //System.out.println(ISSN);
                    }
                }
                else if (obj.contains("month=")) {
                    StringTokenizer st = new StringTokenizer(obj, "={");
                    st.nextToken();
                    String month = st.nextToken().replace("},", "");
                    if(month.equals(null) || month.equals("") || month.equals(" ")) {
                        throw (new FileInvalidException("File is invalid: Field \"month\" " +
                                "is Empty. Processing stopped at this point. Other empty fields may be present as well!"));

                    }else{
                        article.setMonth(month);
                        //System.out.println(month);
                    }
                }
                obj = bf.readLine();
            }else{
                break;
            }
        }
    }
}
