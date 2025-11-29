public class Book extends LibraryItem {
    String title;
    String author;
    String genre;
    String publisher;

    //double borrowProbability=0.05;

    @Override    
    int getLoanPeriodDays() {
        return 14;
    }

    @Override    
    double getDailyFine() {
        return 0.5;
    }
}