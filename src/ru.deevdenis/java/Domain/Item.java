package Domain;

import java.util.Objects;

public class Item {
    private String title;
    private Double price;

    public Item(String title, Double price) {
        this.title = title;
        this.price = price;
    }

    public Item() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(title, item.title) && Objects.equals(price, item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, price);
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}
