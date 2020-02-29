package library.management.Class;

public class Book {
    private String Title, Author, Access_No, Call_No, Availability, Issue_Date, Returned_On, Publication, Edition;

    public Book() {
    }

    public Book(String title, String author, String access_No, String availability, String publication, String edition) {
        Title = title;
        Author = author;
        Access_No = access_No;
        Availability = availability;
        Publication = publication;
        Edition = edition;
    }

    public Book(String title, String access_No, String issue_Date, String returned_On) {
        Title = title;
        Access_No = access_No;
        Issue_Date = issue_Date;
        Returned_On = returned_On;
    }

    public Book(String title, String author, String access_No, String call_No, String availability) {
        Title = title;
        Author = author;
        Access_No = access_No;
        Call_No = call_No;
        Availability = availability;
    }

    public String getTitle() {
        return Title;
    }

    public String getAuthor() {
        return Author;
    }

    public String getAccess_No() {
        return Access_No;
    }

    public String getCall_No() {
        return Call_No;
    }

    public String getAvailability() {
        return Availability;
    }

    public String getIssue_Date() {
        return Issue_Date;
    }

    public String getReturned_On() {
        return Returned_On;
    }

    public String getPublication() {
        return Publication;
    }

    public String getEdition() {
        return Edition;
    }
}
