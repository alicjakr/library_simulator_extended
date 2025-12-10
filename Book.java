public final class Book extends LibraryItem implements Loanable {
    String title;
    String author;
    String genre;
    String publisher;

    @Override    
    int getLoanPeriodDays() {
        return 14;
    }

    @Override
    public int getLoanPeriod(User user) {
        return 14;
    }

    @Override
    public double getDailyFine() {
        return 0.5;
    }

    //implemented method from Searchable interface
    //returns true if the keyword appears in any relevant metadata field (title, author, director, publisher, genre, etc.)
    @Override
    public boolean matches(String keyword) {
        boolean match=false;
        if(keyword.equals(title)) match=true;
        else if(keyword.equals(author)) match=true;
        else if(keyword.equals(genre)) match=true;
        else if(keyword.equals(publisher)) match=true;
        return match;
    }
}