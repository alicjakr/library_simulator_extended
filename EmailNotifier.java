public final class EmailNotifier implements OverdueObserver {
    //prints overdue warnings (simulated email notifications)
    @Override
    public void notifyOverdue(User user, LibraryItem item, int daysLate) {
        String userType=(user instanceof Student) ? "Student" : "Faculty Member";
        System.out.println(user+"'s item "+item.getTitle()+" is "+daysLate+" days overdue.");
    }
}
