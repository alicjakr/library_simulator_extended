public final class Film extends LibraryItem implements Loanable {
    String title;
    String genre;
    String director;
    int year;
    int runtime;
    double ratingEntries;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getYear() {
        return year;
    }

    /*@Override
    int getLoanPeriodDays() {
        return 2;
    }*/

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
        if(keyword==null || keyword.isEmpty()) {
            return false;
        }

        String lower=keyword.toLowerCase();

        return (title!=null && title.toLowerCase().contains(lower)) || (genre!=null && genre.toLowerCase().contains(lower)) ||
                (director!=null && director.toLowerCase().contains(lower)) || String.valueOf(year).contains(lower) ||
                String.valueOf(runtime).contains(lower) || String.valueOf(ratingEntries).contains(lower);
    }
}
