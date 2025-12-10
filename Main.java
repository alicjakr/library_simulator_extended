import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception {
        //create library
        Library library=new Library();
        library.createUsers();
        System.out.println("Amount of users: "+library.users.size());
        library.readCSVfiles();
        System.out.println("Amount of books: "+library.books.size());
        System.out.println("Amount of journals: "+library.journals.size());
        System.out.println("Amount of films: "+library.films.size());

        //add observes
        library.addOverdueObserver(new EmailNotifier());
        //anonymous inner class - attaching a temporary event observer
        library.addOverdueObserver(new OverdueObserver() {
            private final Set<String> notifiedLoans=new HashSet<>();
            @Override
            public void notifyOverdue(User user, LibraryItem item, int daysLate) {
                String key=System.identityHashCode(user)+"-"+item.getTitle();
                if(!notifiedLoans.contains(key)) {
                    System.out.println("[FIRST OVERDUE ALERT] for item "+item.getTitle()+" is - "+daysLate+" days overdue.");
                    notifiedLoans.add(key);
                }
            }
        });

        System.out.println("\nStarting the simulation...\n");
        Simulation simulation=new Simulation(library);
        long startTime = System.nanoTime();
        simulation.simulateYear();
        long endTime = System.nanoTime();
        System.out.println("Simulation finished...\n");
        long executionTime=(endTime-startTime)/1000000;
        System.out.println("Simulating the library for a year takes "+executionTime+"ms\n");
        printStatistics(library);
    }

    private static void printStatistics(Library library) {
        int currentBooks=library.books.size();
        int currentJournals=library.journals.size();
        int currentFilms=library.films.size();

        for(Book book:library.books) {
            if(book.borrowed) {
                currentBooks++;
            }
        }
        for(Journal journal:library.journals) {
            if(journal.borrowed) {
                currentJournals++;
            }
        }
        for(Film film:library.films) {
            if(film.borrowed) {
                currentFilms++;
            }
        }

        System.out.println("Final statistics:");
        System.out.println("Books borrowed: "+currentBooks);
        System.out.println("Journals borrowed: "+currentJournals);
        System.out.println("Films borrowed: "+currentFilms);

    }
}
