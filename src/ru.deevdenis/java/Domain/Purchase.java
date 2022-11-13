package Domain;

import java.time.Instant;
import java.util.Objects;

public class Purchase {
    private Customer customer;
    private Item item;
    private String time;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return Objects.equals(customer, purchase.customer) && Objects.equals(item, purchase.item) && Objects.equals(time, purchase.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, item, time);
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "customer=" + customer +
                ", item=" + item +
                ", time=" + time +
                '}';
    }
}
