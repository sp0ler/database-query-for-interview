package Util;

import Domain.Customer;
import Domain.Item;
import Domain.Purchase;
import ExceptionHandler.Error;
import Repository.CustomerRepository;
import Repository.Impl.CustomerRepositoryImpl;
import Repository.Impl.PurchaseRepositoryImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AnswerWriter {

    private String inputPath;
    private String outputPath;
    private List<String> jsonList;
    private CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private PurchaseRepositoryImpl purchaseRepository = new PurchaseRepositoryImpl();

    private StringBuilder builder = new StringBuilder();

    private double finalTotalExpenses;

    public AnswerWriter() {
        outputPath = CommandLineFactory.argsMap.get("output");

        //if (inputPath == null) throw new Error("Файл input.json не найден");
        //if (outputPath == null) throw new Error("Файл output.json не найден");

        jsonParser();

        if (jsonList.size() > 10) {
            writeCriteriasAnswer();
            builder.append("   ]\n");
            builder.append("}");
        }
        else
            writeStatAnswer();


        FileUtils.writeFile(builder.toString(), outputPath);
    }

    private void jsonParser() {
        try {
            jsonList = readFile(CommandLineFactory.argsMap.get("input"));
        } catch (NullPointerException e) {
            throw new Error("Не указан путь input.json");
        }


        for (String s : jsonList) {
            System.out.println(s);
        }
    }

    private List<String> readFile(String pathFile) {
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

    // TODO: иначе!!!
    private void writeCriteriasAnswer() {
        List<Customer> resultList = null;

        String jsonString = "{\n" +
                "   \"type\": \"search\",\n" +
                "   \"results\": [\n";
        builder.append(jsonString);

        for (String json : jsonList) {
            if (json.contains("lastName")) {
                String lastName =
                        json.replace("lastName: ", "").replace(",", "").trim();
                resultList = customerRepository.findAllCustomersByLastname(lastName);

                builder.append("       {\n");
                builder.append(String.format("           \"criteria\": {\"lastName\": \"%s\"},\n", lastName));
                builder.append("          \"results\": [\n");
                addStringToBuilderForTypeSearch(resultList);
                builder.append("          ]\n");
                builder.append("       },\n");
            }

            else if (json.contains("productName") || json.contains("minTimes")) {
                json = json.replace("productName: ", "").replace("minTimes: ", "");
                String[] array = json.split(",");

                String productName;
                String minTimes;
                try {
                    productName = array[0].trim();
                    minTimes = array[1].trim();
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new Error(
                        "Неверный формат json. Пример, \"productName\": \"Чай зеленый\", \"minTimes\": \"4\""
                    );
                }

                resultList = customerRepository.findAllCustomersByProduct(productName, Integer.parseInt(minTimes));

                builder.append("       {\n");
                builder.append(
                        String.format("          \"criteria\": {\"productName\": \"%s\", \"minTimes\": %s},\n",
                                productName,
                                minTimes)
                );
                builder.append("          \"results\": [\n");
                addStringToBuilderForTypeSearch(resultList);
                builder.append("          ]\n");
                builder.append("       },\n");
            }

            else if (json.contains("minExpenses") || json.contains("maxExpenses")) {
                json = json.replace("minExpenses: ", "").replace("maxExpenses: ", "");
                String[] array = json.split(",");

                String minExpenses;
                String maxExpenses;
                try {
                    minExpenses = array[0].trim();
                    maxExpenses = array[1].trim();
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new Error(
                            "Неверный формат json. Пример, \"minExpenses\": \"100\", \"maxExpenses\": \"1000\""
                    );
                }

                resultList = customerRepository.findAllCustomersByMinAndMaxPrice(
                        Integer.parseInt(minExpenses),
                        Integer.parseInt(maxExpenses)
                );

                builder.append("       {\n");
                builder.append(
                        String.format("           \"criteria\": {\"minExpenses\": %s, \"maxExpenses\": %s},\n",
                                minExpenses,
                                maxExpenses)
                );
                builder.append("          \"results\": [\n");
                addStringToBuilderForTypeSearch(resultList);
                builder.append("          ]\n");
                builder.append("       },\n");
            }

            else if (json.contains("badCustomers")) {
                String badCustomers =
                        json.replace("badCustomers: ", "").replace(",", "").trim();
                resultList = customerRepository.findAllBadCustomers(Integer.parseInt(badCustomers));

                builder.append("       {\n");
                builder.append(String.format("           \"criteria\": {\"badCustomers\": %s},\n", badCustomers));
                builder.append("          \"results\": [\n");
                addStringToBuilderForTypeSearch(resultList);
                builder.append("          ]\n");
                builder.append("       }\n");
            }

        }
    }

    // TODO: иначе!!!
    private void writeStatAnswer() {
        String startDate = "";
        String endDate = "";
        for (String json : jsonList) {
            if (json.contains("startDate"))
                startDate = json.replace("startDate: ", "").replace(",", "").trim();
            else if (json.contains("endDate"))
                endDate = json.replace("endDate: ", "").replace(",", "").trim();
        }

        if (!startDate.equals("") && !endDate.equals("")) {
            List<Customer> allCustomers = purchaseRepository.findAllCustomerByDate(startDate, endDate);

            int date = Integer.parseInt(endDate.split("\\.")[0]) -
                    Integer.parseInt(startDate.split("\\.")[0]) + 1;

            String jsonString = "{\n" +
                    "   \"type\": \"stat\",\n" +
                    "   \"totalDays\": " +  date + ",\n" +
                    "   \"customers\": [\n";
            builder.append(jsonString);
            addStringToBuilderForTypeStat(allCustomers, startDate, endDate);

        }
        else {
            throw new Error("Неверный формат json.");
        }

    }

    // TODO: иначе!!!
    private void addStringToBuilderForTypeSearch(List<Customer> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            Customer customer = list.get(i);
            builder.append(String.format(
                    "              {\"lastName\": \"%s\", \"firstName\": \"%s\"}",
                    customer.getLastName(),
                    customer.getFirstName()
                    )
            );

            if (list.size() - 2 != i) {
                builder.append(",\n");
            } else builder.append("\n");
        }

    }

    // TODO: иначе!!!
    private void addStringToBuilderForTypeStat(List<Customer> list, String startTime, String endTime) {
        for (int i = 0; i < list.size() - 1; i++) {
            Customer customer = list.get(i);

            Purchase purchase = purchaseRepository.findPurchaseByCustomer(
                    startTime,
                    endTime,
                    customer.getLastName(),
                    customer.getFirstName()
            );

            String jsonString1 = "       {\n" +
                    String.format("           \"name\": \"%s %s\",\n",customer.getLastName(), customer.getFirstName()) +
                    "           \"purchases\": [\n";
            List<Item> itemList = purchase.getItem();
            double totalExpenses = 0.0;
            String jsonString2 = "";
            System.out.println(itemList.size());

            for(int j = 0; j < itemList.size() - 1; j++) {
                Item item = purchase.getItem().get(j);

                String price = String.format("%.02f", item.getPrice()).replace(",", ".");
                jsonString2 += String.format(
                        "               {\"name\": \"%s\", \"expenses\": %s}",
                        item.getTitle(),
                        price
                );

                if (itemList.size() - 2 != j)
                    jsonString2 += ",\n";
                else
                    jsonString2 += "\n";

                totalExpenses += item.getPrice();
                finalTotalExpenses += item.getPrice();
            }

            if (totalExpenses != 0.0) {
                builder.append(jsonString1);
                builder.append(jsonString2);

                builder.append("           ],\n");

                String jsonString3 = String.format(
                        "           \"totalExpenses\": \"%.02f\" \n",
                        totalExpenses
                ).replace(",", ".");
                builder.append(jsonString3);

                if (list.size() - 2 != i)
                    builder.append("        },\n");
                else
                    builder.append("        }\n");
            }
        }
        builder.append("    ],\n");
        String totalExpenses = String.format("%.02f", finalTotalExpenses).replace(",", ".");
        builder.append(String.format("  \"totalExpenses\": %s,\n", totalExpenses));

        String avgExpenses = String.format("%.02f", finalTotalExpenses/list.size()).replace(",", ".");
        builder.append(String.format("  \"avgExpenses\": %s\n", avgExpenses));

        builder.append("}");
    }

}
