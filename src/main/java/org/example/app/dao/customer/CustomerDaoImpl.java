package org.example.app.dao.customer;

import org.example.app.dto.CustomerDtoRequest;
import org.example.app.entity.Customer;
import org.example.app.entity.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
@Component

public class CustomerDaoImpl implements CustomerDao {

    /*
        Spring NamedParameterJdbcTemplate — це клас шаблону
        з базовим набором операцій JDBC, що дозволяє використовувати
        іменовані параметри замість традиційного '?'
        (заповнювачі/placeholders).
        Після заміни іменованих параметрів на стиль JDBC,
        NamedParameterJdbcTemplate делегує обернутому JdbcTemplate
        свою роботу.
    */
    NamedParameterJdbcTemplate template;

    @Autowired
    public CustomerDaoImpl(DataSource dataSource) {
        template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public boolean create(CustomerDtoRequest request) {
        String sql = "INSERT INTO customers (full_name, address) " +
                "VALUES (:fullName, :address)";
        SqlParameterSource paramSource =
                new MapSqlParameterSource()
                        .addValue("fullName", request.fullName())
                        .addValue("address", request.address());
        return template.update(sql, paramSource) > 0;
    }

    @Override
    public Optional<List<Customer>> fetchAll() {
        String sql = "SELECT * FROM customers";
        Optional<List<Customer>> optional;
        try {
            optional = Optional.of(template
                    .query(sql, new CustomerMapper()));
        } catch (Exception ex) {
            optional = Optional.empty();
        }
        return optional;
    }

    @Override
    public Optional<Customer> fetchById(Long id) {
        String sql = "SELECT * FROM customers " +
                "WHERE id = :id LIMIT 1";
        SqlParameterSource paramSource =
                new MapSqlParameterSource("id", id);
        Optional<Customer> optional;
        try {
            optional = Optional.ofNullable(template
                    .queryForObject(sql, paramSource, new CustomerMapper()));
        } catch (Exception e) {
            optional = Optional.empty();
        }
        return optional;
    }

    @Override
    public boolean updateById(Long id, CustomerDtoRequest request) {
        String sql = "UPDATE customers " +
                "SET full_name = :fullName, address = :address"+
                "WHERE id = :id";
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("fullName", request.fullName())
                .addValue("address", request.address())
                .addValue("id", id);
        return template.update(sql, paramSource) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM customers WHERE id = :id";
        SqlParameterSource paramSource =
                new MapSqlParameterSource("id", id);
        return template.update(sql, paramSource) > 0;
    }

    @Override
    public Optional<Customer> getLastEntity() {
        String sql = "SELECT * FROM customers " +
                "ORDER BY id DESC LIMIT 1";
        SqlParameterSource paramSource =
                new MapSqlParameterSource();
        Optional<Customer> optional;
        try {
            optional = Optional.ofNullable(template
                    .queryForObject(sql, paramSource, new CustomerMapper()));
        } catch (Exception e) {
            optional = Optional.empty();
        }
        return optional;
    }

    // ---- Query Params ----------------------

    public Optional<List<Customer>> fetchByFullName(String fullName) {
        String sql = "SELECT * FROM customers WHERE full_name = :fullName";
        SqlParameterSource paramSource =
                new MapSqlParameterSource("fullName", fullName);
        Optional<List<Customer>> optional;
        try {
            optional = Optional.of(template
                    .query(sql,  paramSource, new CustomerMapper()));
        } catch (Exception e) {
            optional = Optional.empty();
        }
        return optional;
    }

    public Optional<List<Customer>> fetchByAddress(String address) {
        String sql = "SELECT * FROM customers WHERE address = :address";
        SqlParameterSource paramSource =
                new MapSqlParameterSource("address", address);
        Optional<List<Customer>> optional;
        try {
            optional = Optional.of(template
                    .query(sql,  paramSource, new CustomerMapper()));
        } catch (Exception e) {
            optional = Optional.empty();
        }
        return optional;
    }

}
