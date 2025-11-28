public class Student extends User {
    int maxBooks=3;
    int maxJournals=3;
    int maxFilms=1;

    @Override
    public boolean canBorrowBook() {
        if(borrowedBooks.size()>=maxBooks) {
            return false;
        }
        return true;
    }

    @Override
    public boolean canBorrowJournal() {
        if(borrowedJournals.size()>=maxJournals) {
            return false;
        }
        return true;
    }

    @Override
    public boolean canBorrowFilm() {
        if(borrowedFilms.size()>=maxFilms) {
            return false;
        }
        return true;
    }
}