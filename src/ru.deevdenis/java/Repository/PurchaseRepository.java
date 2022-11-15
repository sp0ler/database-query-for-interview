package Repository;

import Domain.Customer;
import Domain.Purchase;
import com.sun.istack.internal.NotNull;

import java.util.List;

public interface PurchaseRepository {
    Purchase findPurchaseByCustomer(String startTime, String endTime, String lastName, String firstName);
    List<Customer> findAllCustomerByDate(String startTime, String endTime);
}
