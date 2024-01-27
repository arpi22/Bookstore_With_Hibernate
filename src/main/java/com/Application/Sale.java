package com.Application;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sale_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "date_of_sale")
    private LocalDateTime purchaseDate;

    @Column(name = "quantity_sold")
    private int quantitySold;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    public Sale() {
    }

    public Sale(Book book, Customer customer, LocalDateTime purchaseDate, int quantitySold, BigDecimal totalPrice) {
        this.book = book;
        this.customer = customer;
        this.purchaseDate = purchaseDate;
        this.quantitySold = quantitySold;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
