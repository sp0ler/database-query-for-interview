package Repository.Impl;

import Configuration.PostgreSQLJDBC;
import Domain.Customer;
import Domain.Item;
import Domain.Purchase;
import Repository.CustomerRepository;
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

    private CustomerRepository customerRepository = new CustomerRepositoryImpl();

    public PurchaseRepositoryImpl() {
        connection = PostgreSQLJDBC.sessionFactory();
    }

    private Purchase makeQuery(String sql, String lastName, String firstName) {
        ResultSet resultSet;
        Purchase purchase;

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            Customer customer = new Customer(firstName, lastName);
            purchase = new Purchase();
            purchase.setCustomer(customer);


            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String price = resultSet.getString("price");
                Item item = new Item(title, Double.parseDouble(price));

                purchase.addItem(item);
            }
            System.out.println(purchase);
        } catch (SQLException e) {
            throw new Error("Невозможно выполнить запрос к БД. Проверьте ваши входные даные");
        } catch (NullPointerException e) {
            throw new Error("Невозможно выполнить подключение к БД. Проверьте данные подключения к БД");
        }

        return purchase;
    }

    public List<Customer> findAllCustomerByDate(String startTime, String endTime) {
        String sql = String.format(
                "select distinct cus.last_name, cus.first_name " +
                "from Purchases pur " +
                "join Customers cus on pur.customer_id=cus.customer_id " +
                "join Items i on pur.item_id=i.item_id " +
                "where pur.time_purchase >='%s' and pur.time_purchase <='%s';",
                startTime,
                endTime
        );

        return customerRepository.makeQuery(sql);
    }

    @Override
    public Purchase findPurchaseByCustomer(String startTime, String endTime, String lastName, String firstName) {
        String sql = String.format(
                "select cus.last_name, cus.first_name, i.title, i.price, pur.time_purchase " +
                "from Purchases pur " +
                "join Customers cus on pur.customer_id=cus.customer_id " +
                "join Items i on pur.item_id=i.item_id " +
                "where pur.time_purchase >='%s' and pur.time_purchase <='%s' " +
                "and cus.last_name='%s' and cus.first_name='%s' " +
                "order by pur.time_purchase asc;",
                startTime,
                endTime,
                lastName,
                firstName
        );

        return makeQuery(sql, lastName, firstName);
    }
}
