package org.example.app.service.customer;

import org.example.app.dao.customer.CustomerDao;
import org.example.app.dto.CustomerDtoRequest;
import org.example.app.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service("CustomerServiceImpl")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerDao customerDao;

    @Override
    public Customer create(CustomerDtoRequest request) {
        Objects.requireNonNull(request,
                "Parameter [request] must not be null!");
        customerDao.create(request);
        return customerDao.getLastEntity()
                .orElse(null);
    }

    @Override
    public List<Customer> fetchAll() {
        return customerDao.fetchAll()
                .orElse(Collections.emptyList());
    }

    @Override
    public Customer fetchById(Long id) {
        Objects.requireNonNull(id,
                "Parameter [id] must not be null!");
        return customerDao.fetchById(id)
                .orElse(null);
    }

    @Override
    public Customer updateById(Long id, CustomerDtoRequest request) {
        Objects.requireNonNull(request,
                "Parameter [request] must not be null!");
        if (id == null) {
            throw new IllegalArgumentException("Id must be provided!");
        }
        if (customerDao.fetchById(id).isPresent()) {
            customerDao.updateById(id, request);
        }
        return customerDao.fetchById(id).orElse(null);
    }

    @Override
    public boolean deleteById(Long id) {
        Objects.requireNonNull(id,
                "Parameter [id] must not be null!");
        if (customerDao.fetchById(id).isPresent()) {
            customerDao.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Customer getLastEntity() {
        return customerDao.getLastEntity()
                .orElse(null);
    }

    // ---- Query Params ----------------------

    public List<Customer> fetchByFullName(String fullName) {
        return customerDao.fetchByFullName(fullName)
                .orElse(Collections.emptyList());
    }

    public List<Customer> fetchByAddress(String address) {
        return customerDao.fetchByAddress(address)
                .orElse(Collections.emptyList());
    }

}
