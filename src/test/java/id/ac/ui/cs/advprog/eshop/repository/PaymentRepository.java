package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    Payment payment;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        payment = new Payment("pay-001", "VOUCHER", "SUCCESS", paymentData);
    }

    @Test
    void testSaveAndFindById() {
        paymentRepository.save(payment);
        Payment foundPayment = paymentRepository.findById("pay-001");

        assertNotNull(foundPayment);
        assertEquals(payment.getId(), foundPayment.getId());
        assertEquals(payment.getMethod(), foundPayment.getMethod());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        paymentRepository.save(payment);
        Payment foundPayment = paymentRepository.findById("non-existent-id");
        assertNull(foundPayment);
    }

    @Test
    void testFindAll() {
        paymentRepository.save(payment);

        Map<String, String> data2 = new HashMap<>();
        data2.put("voucherCode", "ESHOP8765XYZ4321");
        Payment payment2 = new Payment("pay-002", "VOUCHER", "SUCCESS", data2);
        paymentRepository.save(payment2);

        List<Payment> allPayments = paymentRepository.findAll();
        assertEquals(2, allPayments.size());
    }
}