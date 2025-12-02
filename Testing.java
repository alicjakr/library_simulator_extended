import org.junit.Test;

import java.time.LocalDate;

public class Testing {

    Library library = new Library();
    library.createUsers();


    @Test
    public void testCreateUsers() {
        assert(library.users.size()==100);
    }

    @Test
    public void testDaysOverdue() {

    }

    @Test
    public void testBorrowItem() {
        User student=new Student();
        Book book=new Book();
        LocalDate borrowDate=LocalDate.now();
        library.borrowItem(student,book,borrowDate);
        if(student.returnsOnTime) {
            assert(
        } else {

        }
    }

    @Test
    public void returnItem() {
    }
}