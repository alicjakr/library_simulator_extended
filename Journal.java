public final class Journal extends LibraryItem implements Loanable {
    String title;
    String eISSN;
    String publisher;
    String latestIssue;
    String journalURL;

    @Override
    int getLoanPeriodDays() {
        if(borrower instanceof Student) {
            return 3;
        } else {
            return 7;
        }
    }

    @Override
    public int getLoanPeriod(User user) {
        if(user instanceof Student) {
            return 3;
        } else {
            return 7;
        }
    }

    @Override
    public double getDailyFine() {
        return 2.0;
    }

    //implemented method from Searchable interface
    //returns true if the keyword appears in any relevant metadata field (title, author, director, publisher, genre, etc.)
    @Override
    public boolean matches(String keyword) {
        boolean match=false;
        if(keyword.equals(title)) match=true;
        else if(keyword.equals(eISSN)) match=true;
        else if(keyword.equals(publisher)) match=true;
        else if(keyword.equals(latestIssue)) match=true;
        else if(keyword.equals(journalURL)) match=true;
        return match;
    }
}