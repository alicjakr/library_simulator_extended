import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

abstract class LibraryItem {
    int libraryID;
    LocalDate borrowDate;
    boolean borrowed;
    User borrower;

    //check if logic correct - for this and separate ones for inherited classes
    double returnProbablity=0.02;
    String type;

    //for each subclass
    abstract int getLoanPeriodDays();


    //returns num of days item is overdue -> if negative, item can still be out for that many days
    public int daysOverdue(LocalDate currentDate) throws Exception {
        if(!borrowed || borrowDate==null) {
            throw new Exception("Not valid ask");
        }
        long daysBorrowed=ChronoUnit.DAYS.between(borrowDate, currentDate);//currentdate-borroweddate
        return (int)daysBorrowed-getLoanPeriodDays();
    }

    //return whether item is overdue on given day
    public boolean isOverdue(LocalDate currentDate) throws Exception {
        return daysOverdue(currentDate)>0;
    }

    //for each subclass
    abstract double getDailyFine();

    //returns computed fine if item is returned with delay
    public double computeFine(LibraryItem item) {
        double fine=0.0;
        int overdueDays=daysOverdue(LocalDate currentDate);

        if(overdueDays>0) {
            return overdueDays*getDailyFine();
        }
        return 0.0;
    }
}