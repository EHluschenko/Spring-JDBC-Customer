package org.example.app.dto;

import org.example.app.entity.Customer;
import org.springframework.http.HttpStatus;

public record CustomerDtoGetLastEntityResponse(
        int statusCode,
        String reasonPhrase,
        boolean success,
        String message,
        Customer customer) {

    public static final String SUCCESS_MESSAGE = "Customer has been fetched successfully.";
    public static final String FAILURE_MESSAGE = "Customer has not been found!";

    public static CustomerDtoGetLastEntityResponse of(boolean isCustomerFound, Customer customer) {
        if (isCustomerFound)
            return new CustomerDtoGetLastEntityResponse(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    true, SUCCESS_MESSAGE, customer);
        else
            return new CustomerDtoGetLastEntityResponse(
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    false, FAILURE_MESSAGE, null);
    }
}
