import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Library {
    //80 students, 20 faculty members
    int students=80;
    int facultyMembers=20;
    List<User> users=new ArrayList<>();
    List<Book> books=new ArrayList<>();
    List<Journal> journals=new ArrayList<>();
    List<Film> films=new ArrayList<>();

    public void createUsers() {
        for(int i=0; i<students; i++) {
            users.add(new Student());
        }
        for(int i=0; i<facultyMembers; i++) {
            users.add(new FacultyMember());
        }

        Random r=new Random();
        //assign returning the book on time
        for(User user : users) {
            user.returnsOnTime= r.nextDouble() < 0.67;
        }
    }

    public void readCSVfiles() {
        final String DELIMITER=";";

        //books
        int bookID=1;
        try(BufferedReader br=new BufferedReader(new FileReader("books.csv"))) {
            String line;
            boolean firstline=true;
            while((line=br.readLine())!=null) {
                if(firstline) {
                    firstline=false;
                    continue;
                }

                String[] valuesbook=line.split(DELIMITER);

                Book book=new Book();
                book.libraryID=bookID++;
                book.title=valuesbook.length>0 ? valuesbook[0] : "";
                book.author=valuesbook.length>1 ? valuesbook[1] : "";
                book.genre=valuesbook.length>2 ? valuesbook[2] : "";
                book.publisher=valuesbook.length>4 ? valuesbook[4] : "";

                this.books.add(book);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
                
        //journals
        int journalID=423;
        try(BufferedReader brr=new BufferedReader(new FileReader("jlist.csv"))) {
            String linee;
            boolean firstlinee=true;
            while((linee=brr.readLine())!=null) {
                if(firstlinee) {
                    firstlinee=false;
                    continue;
                }

                String[] valuesjournal=linee.split(DELIMITER);

                Journal journal=new Journal();
                journal.libraryID=journalID++;
                journal.title=valuesjournal.length>0 ? valuesjournal[0] : "";
                journal.eISSN=valuesjournal.length>3 ? valuesjournal[3] : "";
                journal.publisher=valuesjournal.length>4 ? valuesjournal[4] : "";
                journal.latestIssue=valuesjournal.length>6 ? valuesjournal[6] : "";
                journal.journalURL=valuesjournal.length>12 ? valuesjournal[12] : "";

                this.journals.add(journal);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        //films
        int filmID=4384;
        try(BufferedReader brrr=new BufferedReader(new FileReader("movies.csv"))) {
            String lineee;
            boolean firstlineee=true;
            while((lineee=brrr.readLine())!=null) {
                if(firstlineee) {
                    firstlineee=false;
                    continue;
                }

                //handle quotes
                List<String> valuesmovies = getStrings(lineee);
                Film film=new Film();
                film.libraryID=filmID++;
                film.title=valuesmovies.size()>1 ? valuesmovies.get(1) : "";
                film.genre=valuesmovies.size()>2 ? valuesmovies.get(2) : "";
                film.director=valuesmovies.size()>4 ? valuesmovies.get(4) : "";
                if(valuesmovies.size()>6 && !valuesmovies.get(6).isEmpty()) {
                    film.year = Integer.parseInt(valuesmovies.get(6).trim());
                } else {
                    film.year = 0;  // Default for missing year
                }
                if(valuesmovies.size()>7 && !valuesmovies.get(7).isEmpty()) {
                    film.runtime=Integer.parseInt(valuesmovies.get(7).trim());
                } else {
                    film.runtime = 0;
                }
                if(valuesmovies.size()>8 && !valuesmovies.get(8).isEmpty()) {
                    film.ratingEntries=Double.parseDouble(valuesmovies.get(8).trim());
                } else {
                    film.ratingEntries = 0.0;
                }

                this.films.add(film);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> getStrings(String lineee) {
        List<String> valuesmovies=new ArrayList<>();
        boolean quotes=false;
        StringBuilder builder=new StringBuilder();
        for(int i = 0; i< lineee.length(); i++) {
            char c= lineee.charAt(i);
            if(c=='"') {
                quotes=!quotes;
            } else if(c==';' && !quotes) {
                valuesmovies.add(builder.toString());
                builder=new StringBuilder();
            } else {
                builder.append(c);
            }
        }
        valuesmovies.add(builder.toString());
        return valuesmovies;
    }

    //allows to borrow any item on any day
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
    public double returnItem(User user, LibraryItem item, LocalDate date) throws Exception {
        if(!item.borrowed || item.borrower!=user) {
            return 0.0;
        }
        double fine=item.computeFine(date);

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

    public List<LibraryItem> search(String keyword) {
        List<LibraryItem> items=new ArrayList<>();
        items.addAll(books);
        items.addAll(journals);
        items.addAll(films);

        return items.stream().filter(item->item.matches(keyword)).collect(Collectors.toList());
    }

    public List<LibraryItem> getSortedCatalog() {
        List<LibraryItem> items=new ArrayList<>();
        items.addAll(books);
        items.addAll(journals);
        items.addAll(films);
        items.sort(null);
        return items;
    }

    private List<OverdueObserver> observers=new ArrayList<>();
    public void addOverdueObserver(OverdueObserver o) {
        observers.add(o);
    }
    public void removeOverdueObserver(OverdueObserver o) {
        observers.remove(o);
    }
    void notifyObserver(User user, LibraryItem item, int daysLate) {
        for(OverdueObserver o: observers) {
            o.notifyOverdue(user, item, daysLate);
        }
    }
}