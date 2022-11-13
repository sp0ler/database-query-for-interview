import Domain.Customer;
import Domain.Purchase;
import Repository.Impl.PostgresRepositoryImpl;
import Repository.Impl.PurchaseRepositoryImpl;
import Repository.PostgresRepository;
import Util.CommandLineFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {


        new CommandLineFactory(args);


        List<String> jsonList = readFile("C:\\dev\\database-query-for-interview\\src\\ru.deevdenis\\resources\\input.json");

        for (String s : jsonList) {
            System.out.println(s);
        }

        PostgresRepository repository = new PostgresRepositoryImpl();
        PurchaseRepositoryImpl purchaseRepository = new PurchaseRepositoryImpl();

        List<Customer> list = repository.findAllCustomersByLastname("Иванов");
        System.out.println(list);

        List<Customer> list1 = repository.findAllCustomersByProduct("Чай зеленый", 4);
        System.out.println(list1);

        List<Customer> list2 = repository.findAllCustomersByMinAndMaxPrice(100, 1000);
        System.out.println(list2);

        List<Customer> list3 = repository.findAllBadCustomers(4);
        System.out.println(list3);

        List<Purchase> list4 = purchaseRepository.findPurchaseByDate("2022-11-03", "2022-11-06");
        System.out.println(list4);

    }

    public static List<String> readFile(String pathFile) {
            List<String> stringList = new ArrayList<>();

        try {
            Path path = Paths.get(pathFile);
            stringList = Files.readAllLines(path).stream()
                    .map(it -> it.replaceAll("[\\[\\]\\{\\}\"]", ""))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringList;
    }
}
