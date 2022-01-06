/*
 * @author Ahmed-40166924
 * */
public class Article {
    private String author;
    private String journal;
    private String title;
    private int year;
    private String volume;
    private long number;
    private String pages;
    private String keywords;
    private String doi;
    private String ISSN;
    private String month;

    public Article(){

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getISSN() {
        return ISSN;
    }

    public void setISSN(String ISSN) {
        this.ISSN = ISSN;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "Article{" +
                "author={'" + author + '\'' +
                "}, journal={'" + journal + '\'' +
                "}, title={'" + title + '\'' +
                "}, year={" + year +
                "}, volume=}'" + volume + '\'' +
                "}, number={" + number +
                "}, pages={'" + pages + '\'' +
                "}, keywords={'" + keywords + '\'' +
                "}, doi={" + doi + '\'' +
                "}, ISSN={" + ISSN + '\'' +
                "}, month={" + month + '\'' +
                "}'\''" +
                "}";

    }
}
