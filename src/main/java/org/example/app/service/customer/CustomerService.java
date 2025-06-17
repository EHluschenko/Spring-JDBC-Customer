package org.example.app.service.customer;

import org.example.app.dto.CustomerDtoRequest;
import org.example.app.entity.Customer;
import org.example.app.service.BaseService;

import java.util.List;

public interface CustomerService extends BaseService<Customer, CustomerDtoRequest> {
    Customer create(CustomerDtoRequest request);
    List<Customer> fetchAll();
    Customer fetchById(Long id);
    Customer updateById(Long id, CustomerDtoRequest request);
    boolean deleteById(Long id);
    Customer getLastEntity();


    List<Customer> fetchByFullName(String fullName);
    List<Customer> fetchByAddress(String address);

}
