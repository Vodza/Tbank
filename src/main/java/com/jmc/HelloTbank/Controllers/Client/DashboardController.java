package com.jmc.HelloTbank.Controllers.Client;

import com.jmc.HelloTbank.Models.Model;
import com.jmc.HelloTbank.Models.Transaction;
import com.jmc.HelloTbank.Views.TransactionCellFactory;
import javafx.beans.binding.Bindings;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.fxml.Initializable;

import javax.naming.Binding;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Text user_name;
    public Label checking_bal;
    public Label checking_acc_num;
    public Label saving_bal;
    public Label saving_acc_num;
    public Label income_lbl;
    public Label expense_lbl;
    public ListView<Transaction> transaction_listview;
    public TextField payee_fld;
    public TextField amount_fld;
    public TextArea message_fld;
    public Button send_money_btn;

    @Override
    public void initialize (URL url, ResourceBundle resourcebundle){
        bindData();
        initTransactionsList();
        transaction_listview.setItems(Model.getInstance().getLatestTransactions());
        transaction_listview.setCellFactory(e -> new TransactionCellFactory());
        send_money_btn.setOnAction(e -> onSendMoney());
        accountSummary();
    }

    private void bindData(){
        user_name.textProperty().bind(Bindings.concat("Hi, ").concat(Model.getInstance().getClient().firstNameProperty()));
        checking_bal.textProperty().bind(Model.getInstance().getClient().checkingAccountProperty().get().balanceProperty().asString());
        checking_acc_num.textProperty().bind(Model.getInstance().getClient().checkingAccountProperty().get().accountNumberProperty());
        saving_bal.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().balanceProperty().asString());
        saving_acc_num.textProperty().bind(Model.getInstance().getClient().savingsAccountProperty().get().accountNumberProperty());
    }

    private void initTransactionsList(){
        if (Model.getInstance().getLatestTransactions().isEmpty()){
            Model.getInstance().setLatestTransactions();
        }
    }

    private void onSendMoney(){
        String receiver = payee_fld.getText();
        double amount = Double.parseDouble(amount_fld.getText());
        String message = message_fld.getText();
        String sender = Model.getInstance().getClient().pAddressProperty().get();
        ResultSet resultSet = Model.getInstance().getDatabaseDriver().searchClient(receiver);
        try {
            if (resultSet.isBeforeFirst()){
                Model.getInstance().getDatabaseDriver().updateBalance(receiver, amount, "ADD");
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        //trừ tiền từ người gửi tk tiết kiệm
        Model.getInstance().getDatabaseDriver().updateBalance(sender, amount, "ADD");
        //cập nhật số dư tài khoản tiết kiệm trong tk client
        Model.getInstance().getClient().savingsAccountProperty().get().setBalance(Model.getInstance().getDatabaseDriver().getSavingsAccountBalance(sender));
        //lưu gd mới
        Model.getInstance().getDatabaseDriver().newTransaction(sender, receiver, amount, message);
        //dọn dẹp trường
        payee_fld.setText("");
        amount_fld.setText("");
        message_fld.setText("");
    }

    //phương thức tính tất cả chi phis và thu nhap
    private void accountSummary(){
        double income = 0;
        double expense = 0;
        if (Model.getInstance().getAllTransactions().isEmpty()){
            Model.getInstance().setAllTransactions();
        }
        for (Transaction transaction : Model.getInstance().getAllTransactions()){
            if (transaction.senderProperty().get().equals(Model.getInstance().getClient().pAddressProperty().get())){
                expense = expense + transaction.amountProperty().get();
            } else {
                income = income + transaction.amountProperty().get();
            }
        }
        income_lbl.setText("+ VND" + income);
        expense_lbl.setText("- VND" + expense);
    }
}
