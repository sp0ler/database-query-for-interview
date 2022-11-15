package Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Purchase {
    private Customer customer;
    private List<Item> item = new ArrayList<>();

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void addItem(Item i) {
        item.add(i);
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return Objects.equals(customer, purchase.customer) && Objects.equals(item, purchase.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, item);
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "customer=" + customer +
                ", item=" + item +
                '}';
    }
}
