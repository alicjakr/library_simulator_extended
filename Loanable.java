public interface Loanable {
    int getLoanPeriod(User user);
    double getDailyFine(); //changing name from getDailyOverdueFee to getDailyFine
}
