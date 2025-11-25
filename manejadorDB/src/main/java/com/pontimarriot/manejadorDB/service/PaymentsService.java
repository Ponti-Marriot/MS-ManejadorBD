package com.pontimarriot.manejadorDB.service;

import com.pontimarriot.manejadorDB.model.Payment;
import com.pontimarriot.manejadorDB.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentsService {

    private final PaymentRepository paymentRepository;

    public PaymentsService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // Get all payments
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Get payment by id
    public Optional<Payment> getPaymentById(UUID paymentId) {
        return paymentRepository.findById(paymentId);
    }

    // Get payments by reservation
    public List<Payment> getPaymentsByReservation(UUID reservationId) {
        return paymentRepository.findByReservationId(reservationId);
    }

    // Create new payment
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    // Update payment
    public Optional<Payment> updatePayment(UUID paymentId, Payment paymentDetails) {
        return paymentRepository.findById(paymentId).map(payment -> {
            payment.setAmount(paymentDetails.getAmount());
            payment.setTransactionId(paymentDetails.getTransactionId());
            payment.setPaymentStatus(paymentDetails.getPaymentStatus());
            payment.setPaymentUrl(paymentDetails.getPaymentUrl());
            return paymentRepository.save(payment);
        });
    }

    // Update payment status
    public Optional<Payment> updatePaymentStatus(UUID paymentId, String status) {
        return paymentRepository.findById(paymentId).map(payment -> {
            payment.setPaymentStatus(status);
            return paymentRepository.save(payment);
        });
    }

    // Delete payment
    public boolean deletePayment(UUID paymentId) {
        return paymentRepository.findById(paymentId).map(payment -> {
            paymentRepository.delete(payment);
            return true;
        }).orElse(false);
    }

    // Calculate total amount by reservation
    public BigDecimal getTotalAmountByReservation(UUID reservationId) {
        List<Payment> payments = paymentRepository.findByReservationId(reservationId);
        return payments.stream()
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Check if payment exists
    public boolean paymentExists(UUID paymentId) {
        return paymentRepository.existsById(paymentId);
    }
}