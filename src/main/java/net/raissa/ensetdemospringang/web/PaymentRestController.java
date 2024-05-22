package net.raissa.ensetdemospringang.web;

import net.raissa.ensetdemospringang.entities.Payment;
import net.raissa.ensetdemospringang.repository.PaymentRepository;
import net.raissa.ensetdemospringang.repository.StudentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class PaymentRestController {

    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;


    public PaymentRestController(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }

    @GetMapping(path = "/payments")
    public List<Payment> allPayments(){
        return paymentRepository.findAll();
    }
    @GetMapping(path = "/payments/{id}")
    public  Payment getPaymentById(@PathVariable Long id){
        return paymentRepository.findById(id).get();
    }
}
