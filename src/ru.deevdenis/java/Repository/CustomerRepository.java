package Repository;

import Domain.Customer;
import Domain.Purchase;
import com.sun.istack.internal.NotNull;

import java.util.List;

public interface CustomerRepository {
    List<Customer> makeQuery(String sql);
    List<Customer> findAllCustomersByLastname(@NotNull String lastname);
    List<Customer> findAllCustomersByProduct(@NotNull String productName, int countMin);
    List<Customer> findAllCustomersByMinAndMaxPrice(@NotNull int min, @NotNull int max);
    List<Customer> findAllBadCustomers(@NotNull int minCount);
}
