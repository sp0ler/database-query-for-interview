package Repository;

import Domain.Purchase;
import com.sun.istack.internal.NotNull;

import java.util.List;

public interface PurchaseRepository {
    List<Purchase> findPurchaseByDate(@NotNull String startTime, @NotNull String endTime);
}
