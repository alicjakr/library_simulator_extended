public final class EmailNotifier implements OverdueObserver {
    //prints overdue warnings (simulated email notifications)
    //pseudocode so far
    public void notifyOverdue(User user, LibraryItem item, int daysLate) {
        daysLate=item.isOverdue(/*date of check*/); //what should go here?
        if(daysLate!=0) {
                System.out.println(user+"'s item "+item+" is "+daysLate+" days overdue.");
        }
    }
}
