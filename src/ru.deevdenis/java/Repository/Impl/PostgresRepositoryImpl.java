package Repository.Impl;

import Configuration.PostgreSQLJDBC;
import Repository.PostgresRepository;

import java.sql.Connection;

public class PostgresRepositoryImpl implements PostgresRepository {

    private Connection connection;

    public PostgresRepositoryImpl() {
        connection = PostgreSQLJDBC.sessionFactory();
    }


}
