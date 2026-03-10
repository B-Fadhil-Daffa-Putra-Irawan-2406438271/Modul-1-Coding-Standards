package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CodPaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String status = "REJECTED"; // Default
        String address = paymentData.get("address");
        String deliveryFee = paymentData.get("deliveryFee");

        if (address != null && !address.isEmpty() && deliveryFee != null && !deliveryFee.isEmpty()) {
            status = "SUCCESS";
        }

        Payment payment = new Payment(UUID.randomUUID().toString(), method, status, paymentData);
        this.setStatus(payment, order, status);

        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(String id) {
        return paymentRepository.findById(id);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAllPayments();
    }

    @Override
    public void setStatus(Payment payment, Order order, String status) {
        payment.setStatus(status);
        if ("SUCCESS".equals(status)) {
            order.setStatus("SUCCESS");
        } else if ("REJECTED".equals(status)) {
            order.setStatus("FAILED");
        }
    }
}