package com.OrderTracking.History.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private static final String DUMMY_RESPONSE = "\"orders\":\"successful\"";

    @GetMapping("/track-order")
    public ResponseEntity<String> trackOrder(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String fromOrderDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String toOrderDate,
            @RequestParam String enterpriseCode,
            @RequestParam int maximumRecords,
            @RequestParam String customerContactId) {
        ResponseEntity<String> fromOrderDateValidationResult = validateFromOrderDate(fromOrderDate);
        if (fromOrderDateValidationResult != null) {
            return fromOrderDateValidationResult;
        }

        ResponseEntity<String> toOrderDateValidationResult = validateToOrderDate(toOrderDate);
        if (toOrderDateValidationResult != null) {
            return toOrderDateValidationResult;
        }

        ResponseEntity<String> enterpriseCodeValidationResult = validateEnterpriseCode(enterpriseCode);
        if (enterpriseCodeValidationResult != null) {
            return enterpriseCodeValidationResult;
        }

        ResponseEntity<String> maximumRecordsValidationResult = validateMaximumRecords(maximumRecords);
        if (maximumRecordsValidationResult != null) {
            return maximumRecordsValidationResult;
        }

        ResponseEntity<String> customerContactIdValidationResult = validateCustomerContactId(customerContactId);
        if (customerContactIdValidationResult != null) {
            return customerContactIdValidationResult;
        }

        return ResponseEntity.ok(DUMMY_RESPONSE);
    }

    public ResponseEntity<String> validateFromOrderDate(String fromOrderDate) {
        try {
            LocalDate.parse(fromOrderDate);
            return null; 
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Invalid format. Use YYYY-MM-DD.");
        }
    }

    public ResponseEntity<String> validateToOrderDate(String toOrderDate) {
        if (toOrderDate != null) {
            try {
                LocalDate.parse(toOrderDate);
                return null; 
            } catch (DateTimeParseException e) {
                return ResponseEntity.badRequest().body("Invalid format. Use YYYY-MM-DD.");
            }
        }
        return null; 
    }

    public ResponseEntity<String> validateEnterpriseCode(String enterpriseCode) {
        if (enterpriseCode == null || enterpriseCode.isEmpty()) {
            return ResponseEntity.badRequest().body("Enterprise code is required.");
        }
        return null; 
    }

    public ResponseEntity<String> validateMaximumRecords(int maximumRecords) {
        if (maximumRecords <= 0 || maximumRecords > 20) {
            return ResponseEntity.badRequest().body("Maximum records must be between 1 and 20.");
        }
        return null; 
    }

    public ResponseEntity<String> validateCustomerContactId(String customerContactId) {
        if (customerContactId == null || customerContactId.isEmpty()) {
            return ResponseEntity.badRequest().body("Customer contact ID is required.");
        }
        return null; 
    }
}
