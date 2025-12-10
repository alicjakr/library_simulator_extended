import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

sealed abstract class LibraryItem implements Searchable, Comparable<LibraryItem> permits Book, Journal, Film {
    int libraryID;
    LocalDate borrowDate;
    boolean borrowed;
    User borrower;

    //abstract getters
    public abstract String getTitle();
    public abstract int getYear();

    //for each subclass
    //abstract int getLoanPeriodDays();

    //returns num of days item is overdue -> if negative, item can still be out for that many days
    public int daysOverdue(LocalDate currentDate) throws Exception {
        if(!borrowed || borrowDate==null) {
            throw new Exception("Not valid ask");
        }
        long daysBorrowed=ChronoUnit.DAYS.between(borrowDate, currentDate);//currentdate-borroweddate
        return (int)daysBorrowed-((Loanable)this).getLoanPeriod(borrower);
    }

    //return whether item is overdue on given day
    public boolean isOverdue(LocalDate currentDate) throws Exception {
        return daysOverdue(currentDate)>0;
    }

    //for each subclass
    abstract double getDailyFine();

    //returns computed fine if item is returned with delay
    public double computeFine(LocalDate returnDate) throws Exception {
        int overdueDays=daysOverdue(returnDate);
        if(overdueDays>0) {
            return overdueDays*getDailyFine();
        }
        return 0.0;
    }

    //compareTo
    @Override
    public int compareTo(LibraryItem o) {
        //compare titles
        int result=this.getTitle().compareToIgnoreCase(o.getTitle());
        if(result==0) {
            //compare what item type it is -> if film, highest, if not -> between journal and book, if journal -> higher than book
            if(o.getTypeOrder()!=this.getTypeOrder()) return this.getTypeOrder()-o.getTypeOrder();
            //compare years
            if(this.getYear()!=0 && o.getYear()!=0) return Integer.compare(this.getYear(), o.getYear());
            return 0;
        }
        return result;
    }

    private int getTypeOrder() {
        return switch (this) {
            case Book book -> 1;
            case Journal journal -> 2;
            case Film film -> 3;
            default -> 0;
        };
    }

    /*
   if two Jedis are named the same, then you can use their age to decide which goes first and which goes second.
    public int compareTo(Jedi jedi){
        int result = this.name.compareTo(jedi.getName());
        if(result == 0){
            result = this.age > jedi.age ? 1 : this.age < jedi.age ? -1 : 0;
        }
        return result;
    }*/
}