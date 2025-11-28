public class Film extends LibraryItem {
    String title;
    String genre;
    String director;
    int year;
    int runtime;
    double ratingEntries;

    //double borrowProbability=0.05;

    @Override
    int getLoanPeriodDays() {
        return 2;
    }
    @Override
    double getDailyFine() {
        return 5.0;
    }
}
