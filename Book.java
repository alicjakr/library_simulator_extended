public final class Book extends LibraryItem implements Loanable {
    String title;
    String author;
    String genre;
    String publisher;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getYear() {
        return 0;
    }

    /*@Override
    int getLoanPeriodDays() {
        return 14;
    }*/

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
        if(keyword==null || keyword.isEmpty()) {
            return false;
        }

        String lower=keyword.toLowerCase();

        return (title!=null && title.toLowerCase().contains(lower)) || (author!=null && author.toLowerCase().contains(lower)) ||
                (genre!=null && genre.toLowerCase().contains(lower)) || (publisher!=null && publisher.toLowerCase().contains(lower));
    }
}