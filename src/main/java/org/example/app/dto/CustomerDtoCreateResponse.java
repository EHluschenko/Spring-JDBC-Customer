package org.example.app.dto;

import org.example.app.entity.Customer;
import org.springframework.http.HttpStatus;

public record CustomerDtoCreateResponse(
        int statusCode,
        String reasonPhrase,
        boolean success,
        String message,
        Customer customer) {

    public static final String SUCCESS_MESSAGE = "Customer has been created successfully.";
    public static final String FAILURE_MESSAGE = "Customer has not been created!";

    public static CustomerDtoCreateResponse of(boolean isCustomerCreated, Customer customer) {
        if (isCustomerCreated)
            return new CustomerDtoCreateResponse(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    true, SUCCESS_MESSAGE, customer);
        else
            return new CustomerDtoCreateResponse(
                    HttpStatus.NO_CONTENT.value(),
                    HttpStatus.NO_CONTENT.getReasonPhrase(),
                    false, FAILURE_MESSAGE, null);
    }
}
