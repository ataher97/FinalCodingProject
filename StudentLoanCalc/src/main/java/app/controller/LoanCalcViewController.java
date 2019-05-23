package app.controller;

import app.StudentCalc;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import org.apache.poi.ss.formula.functions.*;

public class LoanCalcViewController implements Initializable {
	private StudentCalc SC = null;
	@FXML
	private TableView<Entry> table;
	@FXML
	private TextField LoanAmount;
	@FXML
	private TextField InterestRate;
	@FXML
	private TextField NbrOfYears;
	@FXML
	private TextField AdditionalPayment;
	@FXML
	private Label lblTotalPayemnts;
	@FXML
	private Label lblTotalInterest;
	@FXML
	private DatePicker PaymentStartDate;
	@FXML
	private TableColumn<Entry, Integer> PaymentNbrCol;
	@FXML
	private TableColumn<Entry, LocalDate> DueDateCol;
	@FXML
	private TableColumn<Entry, Double> PaymentCol;
	@FXML
	private TableColumn<Entry, Double> AddPaymentCol;
	@FXML
	private TableColumn<Entry, Double> InterestCol;
	@FXML
	private TableColumn<Entry, Double> PrincipleCol;
	@FXML
	private TableColumn<Entry, Double> BalanceCol;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		PaymentNbrCol.setCellValueFactory(new PropertyValueFactory<Entry, Integer>("Payment #"));
		DueDateCol.setCellValueFactory(new PropertyValueFactory<Entry, LocalDate>("Due Date"));
		PaymentCol.setCellValueFactory(new PropertyValueFactory<Entry, Double>("Payment"));
		AddPaymentCol.setCellValueFactory(new PropertyValueFactory<Entry, Double>("Additonal Payment"));
		InterestCol.setCellValueFactory(new PropertyValueFactory<Entry, Double>("Interest"));
		PrincipleCol.setCellValueFactory(new PropertyValueFactory<Entry, Double>("Principle"));
		BalanceCol.setCellValueFactory(new PropertyValueFactory<Entry, Double>("Balance"));
	}

	public void setMainApp(StudentCalc sc) {
		this.SC = sc;
	}

	/**
	 * btnCalcLoan - Fire this event when the button clicks
	 * 
	 * @version 1.0
	 * @param event
	 */
	@FXML
	private void btnCalcLoan(ActionEvent event) {
		System.out.println("Payment #, Due Date, Payment, Additional Payment, Interest, Principle, Balance");
		ObservableList<Entry> Entries = FXCollections.observableArrayList();

		boolean off = false;
		double dLoanAmount = Double.parseDouble(LoanAmount.getText());
		double dInterestRate = Double.parseDouble(InterestRate.getText()) / 100;
		double dNbrOfYears = Double.parseDouble(NbrOfYears.getText());
		LocalDate localDate = PaymentStartDate.getValue().minusMonths(1);
		double dAddPayment = Double.parseDouble(AdditionalPayment.getText());

		double PMT = Math.abs(FinanceLib.pmt(dInterestRate / 12, dNbrOfYears * 12, dLoanAmount, 0, false));
		double TotalPayments = 0;
		double TotalInterest = 0;
		double Payment = PMT;
		double interest;
		double principle;
		double balance = dLoanAmount;

		for (int row = 1; row <= (dNbrOfYears * 12); row++) {
			interest = dInterestRate / 12 * balance;
			principle = PMT - interest + dAddPayment;
			if (balance - principle < 0) {
				principle = balance;
				off = true;
			}
			balance -= principle;
			localDate = localDate.plusMonths(1);
			TotalInterest += interest;

			Entries.add(new Entry(row, Math.round(Payment * 100.0) / 100.0, dAddPayment,
					Math.round(interest * 100.0) / 100.0, Math.round(principle * 100.0) / 100.0,
					Math.round(balance * 100.0) / 100.0, localDate));
			System.out.println(row + ",\t" + localDate + ", " + Math.round(Payment * 100.0) / 100.0 + ", " + dAddPayment
					+ ", " + Math.round(interest * 100.0) / 100.0 + ", " + Math.round(principle * 100.0) / 100.0 + ", "
					+ Math.round(balance * 100.0) / 100.0);
			if (off) {
				dNbrOfYears = row / 12;
				break;
			}
		}
		table.setItems(Entries);

		TotalPayments = TotalInterest + dLoanAmount;
		lblTotalPayemnts.setText(String.format("%.02f", TotalPayments));
		lblTotalInterest.setText(String.format("%.02f", TotalInterest));

		System.out.println("Loan Amount: " + dLoanAmount);
		System.out.println("Interest Rate: " + dInterestRate);
		System.out.println("Term: " + dNbrOfYears);
		System.out.println("Additional Payment: " + dAddPayment);
		System.out.println("Total Payments: " + TotalPayments);
		System.out.println("Total Interest: " + TotalInterest);
		System.out.println("Final Payment Date: " + localDate);
	}
}