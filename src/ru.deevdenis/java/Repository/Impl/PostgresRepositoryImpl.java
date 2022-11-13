package Repository.Impl;

import Configuration.PostgreSQLJDBC;
import Domain.Customer;
import Domain.Purchase;
import Repository.PostgresRepository;
import com.sun.istack.internal.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostgresRepositoryImpl implements PostgresRepository {

    private final Connection connection;

    public PostgresRepositoryImpl() {
        connection = PostgreSQLJDBC.sessionFactory();
    }

    private List<Customer> makeQuery(String sql) {
        ResultSet resultSet;
        List<Customer> customerList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Customer customer = new Customer(firstName, lastName);
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerList;
    }

    @Override
    public List<Customer> findAllCustomersByLastname(String name) {
        String sql = String.format(
            "select first_name, last_name from Customers where last_name='%s' ;",
            name
        );

        return makeQuery(sql);
    }

    @Override
    public List<Customer> findAllCustomersByProduct(String productName, int countMin) {
        String sql = String.format(
            "select cus.last_name, cus.first_name, i.title " +
            "from Purchases pur " +
            "join Customers cus on pur.customer_id=cus.customer_id " +
            "join Items i on pur.item_id=i.item_id " +
            "where i.title='%s' " +
            "limit %d",
            productName,
            countMin
        );

        return makeQuery(sql);

    }

    @Override
    public List<Customer> findAllCustomersByMinAndMaxPrice(int min, int max) {
        String sql = String.format(
            "select cus.last_name, cus.first_name, sum(i.price) " +
            "from Purchases pur " +
            "join Customers cus on pur.customer_id=cus.customer_id " +
            "join Items i on pur.item_id=i.item_id " +
            "group by cus.last_name, cus.first_name " +
            "having sum(i.price) > %d and sum(i.price) < %d;",
            min,
            max
        );

        return makeQuery(sql);
    }

    @Override
    public List<Customer> findAllBadCustomers(int minCount) {
        String sql = String.format(
            "select cus.last_name, cus.first_name, count(i.price) " +
            "from Purchases pur " +
            "join Customers cus on pur.customer_id=cus.customer_id " +
            "join Items i on pur.item_id=i.item_id " +
            "group by cus.last_name, cus.first_name " +
            "order by count(i.price) asc " +
            "limit %d;",
            minCount
        );

        return makeQuery(sql);
    }

}
