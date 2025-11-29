import java.util.ArrayList;
import java.util.List;

abstract class User {
    boolean returnsOnTime; //67% yes, 33% not on time

    //tracking current items
    List<Book> borrowedBooks = new ArrayList<>();
    List<Journal> borrowedJournals = new ArrayList<>();
    List<Film> borrowedFilms = new ArrayList<>();

    abstract boolean canBorrowBook();
    abstract boolean canBorrowJournal();
    abstract boolean canBorrowFilm();
}