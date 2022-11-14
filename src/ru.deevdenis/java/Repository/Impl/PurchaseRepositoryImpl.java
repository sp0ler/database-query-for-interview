package Repository.Impl;

import Configuration.PostgreSQLJDBC;
import Domain.Customer;
import Domain.Item;
import Domain.Purchase;
import Repository.PurchaseRepository;
import Util.ErrorWriter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PurchaseRepositoryImpl implements PurchaseRepository {
    private final Connection connection;

    public PurchaseRepositoryImpl() {
        connection = PostgreSQLJDBC.sessionFactory();
    }

    private List<Purchase> makeQuery(String sql) {
        ResultSet resultSet;
        List<Purchase> customerList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Customer customer = new Customer(firstName, lastName);

                String title = resultSet.getString("title");
                String price = resultSet.getString("price");
                Item item = new Item(title, Double.parseDouble(price));

                String time = resultSet.getString("time_purchase");
                Purchase purchase = new Purchase();
                purchase.setCustomer(customer);
                purchase.setItem(item);
                purchase.setTime(time);

                customerList.add(purchase);
            }
        } catch (SQLException e) {
            throw new Error("Невозможно выполнить запрос к БД. Проверьте ваши входные даные");
        } catch (NullPointerException e) {
            throw new Error("Невозможно выполнить подключение к БД. Проверьте данные подключения к БД");
        }

        return customerList;
    }

    @Override
    public List<Purchase> findPurchaseByDate(String startTime, String endTime) {
        String sql = String.format(
                "select cus.last_name, cus.first_name, i.title, i.price, pur.time_purchase " +
                "from Purchases pur " +
                "join Customers cus on pur.customer_id=cus.customer_id " +
                "join Items i on pur.item_id=i.item_id " +
                "where pur.time_purchase >='%s' and pur.time_purchase <='%s' " +
                "order by pur.time_purchase asc;",
                startTime,
                endTime
        );

        return makeQuery(sql);
    }
}
