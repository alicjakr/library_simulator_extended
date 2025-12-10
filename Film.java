public final class Film extends LibraryItem implements Loanable {
    String title;
    String genre;
    String director;
    int year;
    int runtime;
    double ratingEntries;

    @Override
    int getLoanPeriodDays() {
        return 2;
    }

    @Override
    public int getLoanPeriod(User user) {
        return 2;
    }

    @Override
    public double getDailyFine() {
        return 5.0;
    }

    //implemented method from Searchable interface
    //returns true if the keyword appears in any relevant metadata field (title, author, director, publisher, genre, etc.)
    @Override
    public boolean matches(String keyword) {
        boolean match=false;
        int keywordInt = Integer.parseInt(keyword);
        double keywordDouble = Double.parseDouble(keyword);
        if(keyword.equals(title)) match=true;
        else if(keyword.equals(genre)) match=true;
        else if(keyword.equals(director)) match=true;
        else if(keywordInt==year) match=true;
        else if(keywordInt==runtime) match=true;
        else if(keywordDouble==ratingEntries) match=true;
        return match;
    }
}
