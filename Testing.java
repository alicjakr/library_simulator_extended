import org.junit.Test;
import java.time.LocalDate;
import static org.junit.Assert.*;

public class Testing {
    Library library = new Library();

    //tests for isOverdue method
    @Test
    public void testIsOverdue_NotOverdue() throws Exception{
        Student student = new Student();
        Book book = new Book();
        book.libraryID=1;
        LocalDate borrowDate=LocalDate.of(2024,1,1);
        library.borrowItem(student, book, borrowDate);
        LocalDate checkDate=LocalDate.of(2024,1,10);
        assertFalse(book.isOverdue(checkDate));
    }

    @Test
    public void testIsOverdue_Overdue() throws Exception{
        Student student = new Student();
        Book book = new Book();
        book.libraryID=1;
        LocalDate borrowDate=LocalDate.of(2024,1,1);
        library.borrowItem(student, book, borrowDate);
        LocalDate checkDate=LocalDate.of(2024,1,18);
        assertTrue(book.isOverdue(checkDate));
    }


    //test fir daysOverdue method
    @Test
    public void testDaysOverdue_NotOverdue() throws Exception{
        Student student = new Student();
        Book book = new Book();
        book.libraryID=1;
        LocalDate borrowDate=LocalDate.of(2024,1,1);
        LocalDate returnDate=LocalDate.of(2024,1,10);
        //borrowed for 9 days, should return -5
        library.borrowItem(student, book, borrowDate);
        int overdue=book.daysOverdue(returnDate);
        assertEquals(-5, overdue);
    }

    @Test
    public void testDaysOverdue_exact() throws Exception{
        Student student = new Student();
        Book book = new Book();
        book.libraryID=1;
        LocalDate borrowDate=LocalDate.of(2024,1,1);
        LocalDate returnDate=LocalDate.of(2024,1,15);
        //borrowed for 14 days, should return 0
        library.borrowItem(student, book, borrowDate);
        int overdue=book.daysOverdue(returnDate);
        assertEquals(0, overdue);
    }

    @Test
    public void testDaysOverdue_overdue() throws Exception{
        Student student = new Student();
        Book book = new Book();
        book.libraryID=1;
        LocalDate borrowDate=LocalDate.of(2024,1,1);
        LocalDate returnDate=LocalDate.of(2024,1,19);
        //borrowed for 18 days, should return 4
        library.borrowItem(student, book, borrowDate);
        int overdue=book.daysOverdue(returnDate);
        assertEquals(4, overdue);
    }

    //tests for borrowItem and returnItem
    @Test
    public void testBorrowItem() {
        User student=new Student();
        Book book=new Book();
        LocalDate borrowDate=LocalDate.now();
        library.borrowItem(student, book, borrowDate);
        boolean isBorrowed=book.borrowed;
        //should return true
        assertTrue(isBorrowed);
    }

    @Test
    public void testBorrowItem_Success() throws Exception {
        User student=new Student();
        Book book=new Book();
        LocalDate borrowDate=LocalDate.of(2024,1,1);
        library.borrowItem(student,book,borrowDate);
        LocalDate returnDate=LocalDate.of(2024,1,15);
        library.returnItem(student, book, returnDate);
        library.borrowItem(student, book, returnDate);
        boolean borrowed=book.borrowed;
        boolean user=book.borrower==student;
        assertTrue(borrowed && user);
    }

    @Test
    public void testBorrowItem_AlreadyBorrowed() throws Exception {
        User student=new Student();
        User student2=new Student();
        Book book=new Book();
        LocalDate borrowDate=LocalDate.of(2024,1,1);
        library.borrowItem(student, book, borrowDate);
        LocalDate secondBorrowDate=LocalDate.of(2024,1,3);
        library.borrowItem(student2, book, secondBorrowDate);
        boolean borrowed=book.borrowed;
        boolean user=book.borrower==student2;
        assertFalse(borrowed && user);
    }

    @Test
    public void testReturnItem() throws Exception {
        User student=new Student();
        Book book=new Book();
        LocalDate borrowDate=LocalDate.of(2024,1,1);
        library.borrowItem(student, book, borrowDate);
        LocalDate returnDate=LocalDate.of(2024,1,19);
        library.returnItem(student, book, returnDate);
        boolean isBorrowed=book.borrowed;
        assertFalse(isBorrowed);
    }

    //test limits for items for users
    @Test
    public void testStudentBookLimit() throws Exception {
        User student=new Student();
        Book book=new Book();
        Book book2=new Book();
        Book book3=new Book();
        Book book4=new Book();
        LocalDate borrowDate=LocalDate.of(2024,1,1);
        library.borrowItem(student, book, borrowDate);
        library.borrowItem(student, book2, borrowDate);
        library.borrowItem(student, book3, borrowDate);
        library.borrowItem(student, book4, borrowDate);
        assertTrue(book.borrowed);
        assertTrue(book2.borrowed);
        assertTrue(book3.borrowed);
        assertFalse(book4.borrowed);
    }

    @Test
    public void testStudentJournalLimit() throws Exception {
        User student=new Student();
        Journal journal=new Journal();
        Journal journal2=new Journal();
        Journal journal3=new Journal();
        Journal journal4=new Journal();
        LocalDate borrowDate=LocalDate.of(2024,1,1);
        library.borrowItem(student ,journal, borrowDate);
        library.borrowItem(student, journal2, borrowDate);
        library.borrowItem(student, journal3, borrowDate);
        library.borrowItem(student, journal4, borrowDate);
        assertTrue(journal.borrowed);
        assertTrue(journal2.borrowed);
        assertTrue(journal3.borrowed);
        assertFalse(journal4.borrowed);
    }

    @Test
    public void testStudentFilmLimit() throws Exception {
        User student=new Student();
        Film film=new Film();
        Film film2=new Film();
        LocalDate borrowDate=LocalDate.of(2024,1,1);
        library.borrowItem(student, film, borrowDate);
        library.borrowItem(student, film2, borrowDate);
        assertTrue(film.borrowed);
        assertFalse(film2.borrowed);
    }

    @Test
    public void testFacultyLimit() throws Exception {
        User faculty=new FacultyMember();
        //borrow multiple items
        Book book=new Book();
        Book book2=new Book();
        Book book3=new Book();
        Book book4=new Book();
        Journal journal=new Journal();
        Journal journal2=new Journal();
        Journal journal3=new Journal();
        Journal journal4=new Journal();
        Film film=new Film();
        Film film2=new Film();
        LocalDate borrowDate=LocalDate.of(2024,1,1);
        library.borrowItem(faculty, book, borrowDate);
        library.borrowItem(faculty, book2, borrowDate);
        library.borrowItem(faculty, book3, borrowDate);
        library.borrowItem(faculty, book4, borrowDate);
        library.borrowItem(faculty, journal, borrowDate);
        library.borrowItem(faculty, journal2, borrowDate);
        library.borrowItem(faculty, journal3, borrowDate);
        library.borrowItem(faculty, journal4, borrowDate);
        library.borrowItem(faculty, film, borrowDate);
        library.borrowItem(faculty, film2, borrowDate);
        assertTrue(book.borrowed);
        assertTrue(book2.borrowed);
        assertTrue(book3.borrowed);
        assertTrue(book4.borrowed);
        assertTrue(journal.borrowed);
        assertTrue(journal2.borrowed);
        assertTrue(journal3.borrowed);
        assertTrue(journal4.borrowed);
        assertTrue(film.borrowed);
        assertTrue(film2.borrowed);
    }

    //test loan periods
    @Test
    public void testJournalLoanStudent() throws Exception {
        User student=new Student();
        Journal journal=new Journal();
        LocalDate borrowDate=LocalDate.of(2024,1,1);
        library.borrowItem(student, journal, borrowDate);
        LocalDate checkDate=LocalDate.of(2024,1,5); //4 days here - limit is 3
        assertEquals(1, journal.daysOverdue(checkDate));
    }

    @Test
    public void testJournalLoanFaculty() throws Exception {
        User faculty=new FacultyMember();
        Journal journal=new Journal();
        LocalDate borrowDate=LocalDate.of(2024,1,1);
        library.borrowItem(faculty, journal, borrowDate);
        LocalDate checkDate=LocalDate.of(2024,1,9); //8 days here - limit is 7
        assertEquals(1, journal.daysOverdue(checkDate));
    }

    @Test
    public void testBookLoan() throws Exception {
        User student=new Student();
        Book bookS=new Book();
        User faculty=new FacultyMember();
        Book bookF=new Book();
        LocalDate borrowDate=LocalDate.of(2024,1,1);
        library.borrowItem(student, bookS, borrowDate);
        library.borrowItem(faculty, bookF, borrowDate);
        LocalDate checkDate=LocalDate.of(2024,1,16); //15 days here - limit is 14
        assertEquals(1, bookS.daysOverdue(checkDate));
        assertEquals(1, bookF.daysOverdue(checkDate));
    }

    @Test
    public void testFilmLoan() throws Exception {
        User student=new Student();
        Film filmS=new Film();
        User faculty=new FacultyMember();
        Film filmF=new Film();
        LocalDate borrowDate=LocalDate.of(2024,1,1);
        library.borrowItem(student, filmS, borrowDate);
        library.borrowItem(faculty, filmF, borrowDate);
        LocalDate checkDate=LocalDate.of(2024,1,4); //3 days here - limit is 2
        assertEquals(1, filmS.daysOverdue(checkDate));
        assertEquals(1, filmF.daysOverdue(checkDate));
    }

    @Test
    public void testComputeFine_NoFine() throws Exception {
        User student=new Student();
        Book book=new Book();
        LocalDate borrowDate=LocalDate.of(2024,1,1);
        library.borrowItem(student, book, borrowDate);
        LocalDate returnDate=LocalDate.of(2024,1,10);
        double fine=library.returnItem(student, book, returnDate);
        assertEquals(0.0, fine, 0.0);
    }
    //test fines
    @Test
    public void testBookFine() throws Exception {
        User student=new Student();
        Book book=new Book();
        LocalDate borrowDate=LocalDate.of(2024,1,1);
        library.borrowItem(student, book, borrowDate);
        LocalDate returnDate=LocalDate.of(2024,1,19); //4 days overdue
        double fine=library.returnItem(student, book, returnDate);
        assertEquals(2.0, fine, 0.0);
    }

    @Test
    public void testJournalFine() throws Exception {
        User student=new Student();
        Journal journal=new Journal();
        LocalDate borrowDate=LocalDate.of(2024,1,1);
        library.borrowItem(student, journal, borrowDate);
        LocalDate returnDate=LocalDate.of(2024,1,6); //2 days overdue
        double fine=library.returnItem(student, journal, returnDate);
        assertEquals(4.0, fine, 0.0);
    }

    @Test
    public void testFilmFine() throws Exception {
        User student=new Student();
        Film film=new Film();
        LocalDate borrowDate=LocalDate.of(2024,1,1);
        library.borrowItem(student, film, borrowDate);
        LocalDate returnDate=LocalDate.of(2024,1,5); //2 days overdue
        double fine=library.returnItem(student, film, returnDate);
        assertEquals(10.0, fine, 0.0);
    }
}