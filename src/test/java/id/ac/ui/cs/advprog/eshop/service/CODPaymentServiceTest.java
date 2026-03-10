package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CodPaymentServiceTest {

    @InjectMocks
    CodPaymentServiceImpl codService;

    @Mock
    PaymentRepository paymentRepository;

    Order order;

    @BeforeEach
    void setUp() {
        this.order = new Order("id-order", new ArrayList<>(), 0L, "WAITING_PAYMENT");
    }

    @Test
    void testAddPaymentCodValid() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "Jalan Margonda");
        data.put("deliveryFee", "10000");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);
        Payment result = codService.addPayment(order, "COD", data);

        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testAddPaymentCodInvalidEmptyAddress() {
        Map<String, String> data = new HashMap<>();
        data.put("address", ""); // Kosong sesuai aturan dokumen
        data.put("deliveryFee", "10000");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);
        Payment result = codService.addPayment(order, "COD", data);

        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void testAddPaymentCodInvalidNullFee() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "Jalan Margonda");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);
        Payment result = codService.addPayment(order, "COD", data);

        assertEquals("REJECTED", result.getStatus());
    }
}