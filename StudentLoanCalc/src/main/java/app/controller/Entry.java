package app.controller;

import java.time.LocalDate;

public class Entry {

	private Integer PaymentNbr;
	private Double Payment, AddPayment, Interest, Principle, Balance;
	private LocalDate DueDate;

	public Entry(Integer paymentNbr, Double payment, Double addPayment, Double interest, Double principle,
			Double balance, LocalDate dueDate) {
		PaymentNbr = new Integer(paymentNbr);
		Payment = new Double(payment);
		AddPayment = new Double(addPayment);
		Interest = new Double(interest);
		Principle = new Double(principle);
		Balance = new Double(balance);
		DueDate = dueDate;
	}

	public Integer getPaymentNbr() {
		return PaymentNbr;
	}

	public void setPaymentNbr(Integer paymentNbr) {
		PaymentNbr = paymentNbr;
	}

	public Double getPayment() {
		return Payment;
	}

	public void setPayment(Double payment) {
		Payment = payment;
	}

	public Double getAddPayment() {
		return AddPayment;
	}

	public void setAddPayment(Double addPayment) {
		AddPayment = addPayment;
	}

	public Double getInterest() {
		return Interest;
	}

	public void setInterest(Double interest) {
		Interest = interest;
	}

	public Double getPrinciple() {
		return Principle;
	}

	public void setPrinciple(Double principle) {
		Principle = principle;
	}

	public Double getBalance() {
		return Balance;
	}

	public void setBalance(Double balance) {
		Balance = balance;
	}

	public LocalDate getDueDate() {
		return DueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		DueDate = dueDate;
	}

}
