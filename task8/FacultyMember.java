public class FacultyMember extends User {
    @Override
    public boolean canBorrowBook() {
        return true;
    }

    @Override
    public boolean canBorrowJournal() {
        return true;
    }

    @Override
    public boolean canBorrowFilm() {
        return true;
    }
}