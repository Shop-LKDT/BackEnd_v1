package com.project.shopapp.controllers;

import com.project.shopapp.responses.ResponseObject;
import com.project.shopapp.services.vnpay.VNPayService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class VNPayController {

    private final VNPayService vnpayService;

    public VNPayController(VNPayService vnpayService) {
        this.vnpayService = vnpayService;
    }

    @GetMapping("/api/v1/vnpay/payment")
    public ResponseObject createPayment(@RequestParam("amount") int amount,
            @RequestParam("orderInfo") String orderInfo,
            @RequestParam("returnUrl") String returnUrl,
            HttpServletRequest request) throws Exception {
        String paymentUrl = vnpayService.createOrder(request, amount, orderInfo, returnUrl);
        return ResponseObject.builder()
                .message("Payment order created successfully")
                .status(HttpStatus.OK)
                .data(paymentUrl)
                .build();
    }

    @GetMapping("/api/v1/vnpay/paymentReturn")
    public ResponseObject paymentReturn(HttpServletRequest request) {
        int result = vnpayService.orderReturn(request);
        String message;
        HttpStatus status;

        if (result == 1) {
            message = "Transaction successful!";
            status = HttpStatus.OK;
        } else if (result == 0) {
            message = "Transaction failed!";
            status = HttpStatus.BAD_REQUEST;
        } else {
            message = "Invalid transaction!";
            status = HttpStatus.BAD_REQUEST;
        }

        return ResponseObject.builder()
                .message(message)
                .status(status)
                .data(null)
                .build();
    }
}
