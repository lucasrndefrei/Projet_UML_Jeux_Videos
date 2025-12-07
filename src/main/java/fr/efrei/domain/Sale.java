package fr.efrei.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Sale implements Serializable {
    private final String id;
    private final Customer customer;
    private final Game game;
    private final LocalDate date;
    private final double price;

    public Sale(String id, Customer customer, Game game, LocalDate date, double price) {
        this.id = id;
        this.customer = customer;
        this.game = game;
        this.date = date;
        this.price = price;
    }

    public String getId() { return id; }
    public Customer getCustomer() { return customer; }
    public Game getGame() { return game; }
    public LocalDate getDate() { return date; }
    public double getPrice() { return price; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return Objects.equals(id, sale.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id='" + id + '\'' +
                ", customer=" + customer.getName() +
                ", game=" + game.getTitle() +
                ", date=" + date +
                ", price=" + price +
                '}';
    }
}
