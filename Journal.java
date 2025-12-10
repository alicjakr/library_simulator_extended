public final class Journal extends LibraryItem implements Loanable {
    String title;
    String eISSN;
    String publisher;
    String latestIssue;
    String journalURL;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getYear() {
        return 0;
    }

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
        if(keyword==null || keyword.isEmpty()) {
            return false;
        }

        String lower=keyword.toLowerCase();

        return (title!=null && title.toLowerCase().contains(lower)) || (eISSN!=null && eISSN.toLowerCase().contains(lower)) ||
                (publisher!=null && publisher.toLowerCase().contains(lower)) || (latestIssue!=null && latestIssue.toLowerCase().contains(lower)) ||
                (journalURL!=null && journalURL.toLowerCase().contains(lower));
    }
}