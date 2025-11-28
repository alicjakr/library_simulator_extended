import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Library {
    //80 students, 20 faculty members
    int students=80;
    int facultyMembers=20;
    List<User> users=new ArrayList<>();
    List<Book> books=new ArrayList<>();
    List<Journal> journals=new ArrayList<>();
    List<Film> films=new ArrayList<>();

    public void readCVSfiles() {

    }

    public void createUsers() {
        for(int i=0; i<students; i++) {
            users.add(new Student());
        }
        for(int i=0; i<facultyMembers; i++) {
            users.add(new FacultyMember());
        }

        Random r=new Random();
        //assign returnig the book on time
        for(User user : users) {
            if(r.nextDouble()<0.67) {
                user.returnsOnTime=true;
            } else {
                user.returnsOnTime=false;
            }

        }
    }

    //on time users always return item on last day of loan period if item not already returned
    //loan period doesnt count the day of borrowing but counts the last day

    //allows to borrow any item on any day (useful for tests)
    public void borrowItem(User user, LibraryItem item, LocalDate date) {
        if(item.borrowed) {
            return;
        }
        boolean canBorrow=false;
        if(user.canBorrowBook() && item instanceof Book) {
            user.borrowedBooks.add((Book) item);
            canBorrow=true;
        } else if(user.canBorrowJournal() && item instanceof Journal) {
            user.borrowedJournals.add((Journal)item);
            canBorrow=true;
        } else if(user.canBorrowFilm() && item instanceof Film) {
            user.borrowedFilms.add((Film) item);
            canBorrow=true;
        }if(canBorrow) {
            item.borrowed=true;
            item.borrowDate=date;
            item.borrower=user;
        }
    }

    //returning items
    public double returnItem(User user, LibraryItem item, LocalDate date) {
        if(!item.borrowed || item.borrower!=user) {
            return 0.0;
        }
        double fine=item.computeFine(item);

        if(item instanceof Book) {
            user.borrowedBooks.remove(item);
        } else if (item instanceof Journal) {
            user.borrowedJournals.remove(item);
        } else if(item instanceof Film) {
            user.borrowedFilms.remove(item);
        }

        item.borrowed=false;
        item.borrower=null;
        item.borrowDate=null;

        return fine;
    }
}