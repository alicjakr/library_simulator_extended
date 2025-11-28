public class Journal extends LibraryItem {
    String title;
    String eISSN; //2190-5738
    String publisher;
    String latestIssue; //v.12(12):Dec 2022
    String journalURL; //http://www.ncbi.nlm.nih.gov/pmc/journals/1811/

    //double borrowProbability=0.08;

    @Override
    int getLoanPeriodDays() {
        if(borrower instanceof Student) {
            return 3;
        } else {
            return 7;
        }
    }

    @Override
    double getDailyFine() {
        return 2.0;
    }
}