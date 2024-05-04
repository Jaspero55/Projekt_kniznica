package Knihy;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		//DBConnection.getDBConnection();
		
		LibraryManagementSystem library = new LibraryManagementSystem();
		//library.loadFromDatabase();
        Scanner scanner = new Scanner(System.in);
        
        char choice;
        
        do {
            System.out.println("Vyberte možnosť:");
            System.out.println("a) Pridanie novej knihy");
            System.out.println("b) Úprava knihy");
            System.out.println("c) Odstránenie knihy");
            System.out.println("d) Označenie knihy ako vypůjčené/vrácené");
            System.out.println("e) Výpis kníh");
            System.out.println("f) Vyhľadanie knihy");
            System.out.println("g) Výpis všetkých kníh daného autora v chronologickom poradí");
            System.out.println("h) Výpis všetkých kníh podľa žánru");
            System.out.println("i) Výpis všetkých vypůjčených knih s informáciou o type");
            System.out.println("j) Uloženie informácií o vybrané knize do souboru");
            System.out.println("k) Načítanie informací o dané knize zo souboru");
            //System.out.println("l) Uloženie a načítanie všetkých informácií do/z SQL databázy");
            System.out.println("x) Koniec programu");
            choice = scanner.next().charAt(0);
            
            switch(choice) {
                case 'a':
                    // Pridanie novej knihy
                    System.out.println("Zadajte názov knihy:");
                    String title = scanner.next();
                    System.out.println("Zadajte autora knihy:");
                    String author = scanner.next();
                    System.out.println("Zadajte rok vydania knihy:");
                    int year = scanner.nextInt();
                    System.out.println("Je kniha k dispozícii? (Ano/Nie):");
                    
                    String availableInput = scanner.next().toLowerCase();
                    boolean available;
                    if (availableInput.equals("ano")) {
                        available = true;
                    } else if (availableInput.equals("nie")) {
                        available = false;
                    } else {
                        System.out.println("Neplatná voľba. Použije sa predvolená hodnota 'nie'.");
                        available = false;
                    }
                    
                    System.out.println("Aká je kategória knihy? (Roman/Ucebnica):");
                    String category = scanner.next();
                    if (category.equalsIgnoreCase("roman")) {
                        System.out.println("Zadajte žáner románu:");
                        String genre = scanner.next();
                        library.addBook(new Roman(title, author, year, available, genre));
                    } else if (category.equalsIgnoreCase("ucebnica")) {
                        System.out.println("Zadajte ročník učebnice:");
                        String grade = scanner.next();
                        library.addBook(new Ucebnica(title, author, year, available, grade));
                    }
                    break;
                case 'b':
                    // Úprava knihy
                    System.out.println("Zadajte názov knihy:");
                    String editTitle = scanner.next();
                    System.out.println("Zadajte nového autora knihy:");
                    String editAuthor = scanner.next();
                    System.out.println("Zadajte nový rok vydania knihy:");
                    int editYear = scanner.nextInt();
                    System.out.println("Je kniha k dispozícii? (ano/nie):");
                    
                    String editAvailableInput = scanner.next().toLowerCase();
                    boolean editAvailable;
                    if (editAvailableInput.equals("ano")) {
                        editAvailable = true;
                    } else if (editAvailableInput.equals("nie")) {
                        editAvailable = false;
                    } else {
                        System.out.println("Neplatná voľba. Použije sa predvolená hodnota 'false'.");
                        editAvailable = false;
                    }
                    
                    
                    library.editBook(editTitle, editAuthor, editYear, editAvailable);
                    break;
                case 'c':
                    // Odstránenie knihy
                    System.out.println("Zadajte názov knihy, ktorú chcete odstrániť:");
                    String removeTitle = scanner.next();
                    library.removeBook(removeTitle);
                    break;
                case 'd':
                    // Označenie knihy ako vypůjčené/vrácené
                    System.out.println("Zadajte názov knihy, ktorú chcete označiť:");
                    String markTitle = scanner.next();
                    System.out.println("Je kniha k dispozícii? (Ano/Nie):");
                    
                    String markAvailableInput = scanner.next().toLowerCase();
                    boolean markAvailable;
                    if (markAvailableInput.equals("ano")) {
                        markAvailable = true;
                    } else if (markAvailableInput.equals("nie")) {
                        markAvailable = false;
                    } else {
                        System.out.println("Neplatná voľba. Použije sa predvolená hodnota 'false'.");
                        markAvailable = false;
                    }
                    
;
                    library.markBook(markTitle, markAvailable);
                    break;
                case 'e':
                    // Výpis kníh
                    library.listBooks();
                    break;
                case 'f':
                    // Vyhľadanie knihy
                    System.out.println("Zadajte názov knihy, ktorú chcete vyhľadať:");
                    String findTitle = scanner.next();
                    library.findBook(findTitle);
                    break;
                case 'g':
                    // Výpis všetkých kníh daného autora v chronologickom poradí
                    System.out.println("Zadajte autora knihy:");
                    String authorBooks = scanner.next();
                    library.listBooksByAuthor(authorBooks);
                    break;
                case 'h':
                    // Výpis všetkých kníh podľa žánru
                    System.out.println("Zadajte žáner knihy:");
                    String genreBooks = scanner.next();
                    library.listBooksByGenre(genreBooks);
                    break;
                case 'i':
                    // Výpis všetkých vypůjčených knih s informáciou o type
                    library.listBorrowedBooks();
                    break;
                case 'j':
                    // Uloženie informácií o vybrané knize do souboru
                    System.out.println("Zadajte názov knihy, ktorú chcete uložiť do súboru:");
                    String saveTitle = scanner.next();
                    library.saveToFile(saveTitle);
                    break;
                case 'k':
                    // Načítanie informací o dané knize zo souboru
                    System.out.println("Zadajte názov súboru, z ktorého chcete načítať informácie o knihe:");
                    String loadFile = scanner.next();
                    library.loadFromFile(loadFile);
                    
                    break;
                case 'l':
                    // Uloženie a načítanie všetkých informácií do/z SQL databázy
                    //library.saveToDatabase();
                    //library.loadFromDatabase();
                    break;
                case 'x':
                    System.out.println("Program ukončený.");
                    break;
                default:
                    System.out.println("Neplatná voľba. Prosím, skúste znova");
            }
        } while (choice != 'x');
        
        scanner.close();
	}
}