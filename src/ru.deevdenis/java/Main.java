import Domain.Customer;
import Domain.Purchase;
import Repository.Impl.CustomerRepositoryImpl;
import Repository.Impl.PurchaseRepositoryImpl;
import Repository.CustomerRepository;
import Util.AnswerWriter;
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
        new AnswerWriter();
    }
}
