package com.Application;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.hibernate.query.Query;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    private static final SessionFactory SESSION_FACTORY =
            HibernateConfig.getSessionFactory();

    public static void main(String[] args) {
        Book book = new Book("title", "Author", "genre 1", BigDecimal.ONE, 123);
        Session session = SESSION_FACTORY.openSession();
        Transaction transaction = session.beginTransaction();
        while (true) {
            Scanner input = new Scanner(System.in);

            System.out.println("Press 1 if you want to update book details. ");
            System.out.println("Press 2 if you want to update Customer contact information. ");
            System.out.println("Press 3 if you want to see List Books by Genre. ");
            System.out.println("Press 4 if you want to see List Books by Author. ");
            System.out.println("Press 5 if you want to PURCHASE HISTORY OF Customer. ");
            System.out.println("Press 6 if you want to handle new sales. ");
            System.out.println("Press 7 if you want to total revenue by genre. ");
            System.out.println("Press 8 if you want to see books sold report. ");
            System.out.println("Press 0 if you want to exit. ");

            switch (input.nextLine()) {
                case "1": {
                    System.out.println("please insert book_id");
                    Long book_id = input.nextLong();
                    Book bookSelected =  getBookById(book_id);
                    if(bookSelected != null){
                    while (true){
                        System.out.println("Press 1 if you want to update book title. ");
                        System.out.println("Press 2 if you want to update book genre. ");
                        System.out.println("Press 3 if you want to update book price. ");
                        System.out.println("Press 4 if you want to exit. ");
                        switch (input.nextLine()) {
                            case "1": {
                                String title = input.nextLine();
                                bookSelected.setTitle(title);
                                break;
                            }
                            case "2": {
                                String genre = input.nextLine();
                                bookSelected.setGenre(genre);
                                break;
                            }
                            case "3": {
                                BigDecimal price = input.nextBigDecimal();
                                bookSelected.setPrice(price);
                                break;
                            }
                            case "4": {
                                break;
                            }

                        }
                        updateBookDetails(bookSelected);
                    }
                    }else System.out.println("don't exist book");

                    break;
                }
                case "2": {System.out.println("please insert customer_id");
                    Long customer_id = input.nextLong();
                    Customers customerSelected =  getCustomerById(customer_id);
                    if(customerSelected != null){
                        while (true){
                            System.out.println("Press 1 if you want to update Customer email. ");
                            System.out.println("Press 2 if you want to update Customer phone. ");
                            System.out.println("Press 3 if you want to exit. ");
                            switch (input.nextLine()) {
                                case "1": {
                                    String email = input.nextLine();
                                    customerSelected.setEmail(email);
                                    break;
                                }
                                case "2": {
                                    String phone = input.nextLine();
                                    customerSelected.setPhone(phone);
                                    break;
                                }
                                case "3": {
                                    break;
                                }

                            }
                            customerManagementUpdate(customerSelected);
                        }
                    }else System.out.println("don't exist customer");

                    break;

                }
                case "3": {

                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Enter genre to list books: ");
                    String genre = scanner.nextLine();
                    List<Book> booksByGenre = getBooksByGenre(genre);
                    if(booksByGenre.size()==0)
                        System.out.println("we don't have this genre");
                    else
                    {
                        System.out.println("Books by "+ genre +" Genre:");
                        booksByGenre.forEach(book1 -> System.out.println(book1.getTitle()));
                    }
                    break;
                }
                case "4": {
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Enter author to list books: ");
                    String author = scanner.nextLine();
                    List<Book> booksByAuthor = getBooksByAuthor(author);
                    if(booksByAuthor.size()==0)
                        System.out.println("we don't have this genre");
                    else {
                        System.out.println("Books by " + author + " Genre:");
                        booksByAuthor.forEach(book1 -> System.out.println(book1.getTitle()));
                    }
                    break;
                }
                case "5": {
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Enter author to list books: ");
                    Long customerId = Long.valueOf(scanner.nextLine());
                    List<Sales> purchaseHistory = getPurchaseHistory(customerId);
                    System.out.println("Purchase History for Customer " + customerId + ":");
                    purchaseHistory.forEach(purchase -> System.out.println(purchase.getBook().getTitle()));
                    break;

                }
                case "6": {
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Please enter customer_id: ");
                    Long customer_id = scanner.nextLong();
                    System.out.print("Please enter book_id: ");
                    Long book_id = scanner.nextLong();
                    processNewSale(getCustomerById(customer_id),getBookById(book_id));
                    System.out.println("Congratulations you have new book");
                    break;
                }
                case "7": {
                    generateRevenueReport();
                    break;
                }
                case "8": {
                    List<Sales> booksSoldReport = getBooksSoldReport();
                    displayBooksSoldReport(booksSoldReport);
                    break;
                }
                case "0": {
                    session.persist(book);
                    session.flush();
                    transaction.commit();
                    session.close();
                    return;
                }
                default:
                    System.out.println("You entered incorrectly , try again");

            }
        }





    }
    public static void updateBookDetails(Book updatedBook) {
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Retrieve the existing book from the database
            Book existingBook = session.get(Book.class, updatedBook.getBookID());

            // Update the book details
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setGenre(updatedBook.getGenre());
            existingBook.setPrice(updatedBook.getPrice());

            // Save the updated book
            session.update(existingBook);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void customerManagementUpdate(Customers updatedCustomer){
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Retrieve the existing customer from the database
            Customers existingCustomer = session.get(Customers.class, updatedCustomer.getId());

            // Update the customer details
            existingCustomer.setPhone(updatedCustomer.getPhone());
            existingCustomer.setEmail(updatedCustomer.getEmail());


            // Save the updated customer
            session.update(existingCustomer);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static List<Book> getBooksByGenre(String genre) {
        try (Session session = SESSION_FACTORY.openSession()) {
            String hql = "FROM books b WHERE b.genre = :genre";
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("genre", genre);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    public static List<Book> getBooksByAuthor(String author) {
        try (Session session = SESSION_FACTORY.openSession()) {
            String hql = "FROM Book b WHERE b.author = :author";
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("author", author);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static List<Sales> getPurchaseHistory(Long customerId) {
        try (Session session = SESSION_FACTORY.openSession()) {
            String hql = "FROM Sales p WHERE p.customers.id = :customerId";
            Query<Sales> query = session.createQuery(hql, Sales.class);
            query.setParameter("customerId", customerId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    public static void processNewSale(Customers customer, Book book) {
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Check if there is sufficient stock
            if (book.getQuantityInStock() > 0) {
                // Update stock
                book.setQuantityInStock(book.getQuantityInStock() - 1);
                session.update(book);

                // Record the sale
                Sales purchase = new Sales();
                purchase.setCustomer(customer);
                purchase.setBook(book);
                purchase.setPurchaseDate(LocalDateTime.now());
                session.persist(purchase);
                transaction.commit();

            } else {
                System.out.println("Sorry, the book is out of stock.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Map<String, BigDecimal> calculateTotalRevenueByGenre() {
        try (Session session = SESSION_FACTORY.openSession()) {
            String hql = "SELECT p.books.genre, SUM(p.price) FROM Sales p GROUP BY p.books.genre";
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            List<Object[]> result = query.getResultList();

            Map<String, BigDecimal> totalRevenueByGenre = new HashMap<>();
            for (Object[] row : result) {
                String genre = (String) row[0];
                BigDecimal totalRevenue = (BigDecimal) row[1];
                totalRevenueByGenre.put(genre, totalRevenue);
            }

            return totalRevenueByGenre;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    public static void generateRevenueReport() {
        Map<String, BigDecimal> totalRevenueByGenre = calculateTotalRevenueByGenre();

        // Display the revenue report
        System.out.println("Revenue Report by Genre:");
        for (Map.Entry<String, BigDecimal> entry : totalRevenueByGenre.entrySet()) {
            String genre = entry.getKey();
            BigDecimal totalRevenue = entry.getValue();
            System.out.println(genre + ": " + totalRevenue);
        }
    }

    public static List<Sales> getBooksSoldReport() {
        try (Session session = SESSION_FACTORY.openSession()) {
            String hql = "SELECT p FROM Sales p JOIN FETCH p.customers JOIN FETCH p.books";
            Query<Sales> query = session.createQuery(hql, Sales.class);
            return query.getResultList();
        } catch (HibernateException e) {
            // Log the exception or throw a custom exception
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    public static void displayBooksSoldReport(List<Sales> purchases) {
        System.out.println("Books Sold Report:");
        for (Sales purchase : purchases) {
            Book book = purchase.getBook();
            Customers customer = purchase.getCustomer();

            System.out.println("Book Title: " + (book != null ? book.getTitle() : "N/A"));
            System.out.println("Customer Name: " + (customer != null ? customer.getName() : "N/A"));
            System.out.println("Sale Date: " + purchase.getPurchaseDate());
            System.out.println("------------------------------");
        }
    }
    public static Book getBookById(Long id){

        try (Session session = SESSION_FACTORY.openSession()) {
            String hql = "FROM Book p WHERE p.bookID = :book_id";
            Query<Book> query = session.createQuery(hql, Book.class);
            query.setParameter("book_id", id);

            return query.getResultList().getFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return new Book();
        }
    }
    public static Customers getCustomerById(Long id){

        try (Session session = SESSION_FACTORY.openSession()) {
            String hql = "FROM Customers p WHERE p.customer_id = :customer_id";
            Query<Customers> query = session.createQuery(hql, Customers.class);
            query.setParameter("customer_id", id);

            return query.getResultList().getFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return new Customers();
        }
    }

}
