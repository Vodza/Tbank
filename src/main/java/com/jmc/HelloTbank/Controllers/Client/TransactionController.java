package com.jmc.HelloTbank.Controllers.Client;

import com.jmc.HelloTbank.Models.Model;
import com.jmc.HelloTbank.Models.Transaction;
import com.jmc.HelloTbank.Views.TransactionCellFactory;
import javafx.scene.control.ListView;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {
    public ListView<Transaction> transaction_listview;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initAllTransactionsList();
        transaction_listview.setItems(Model.getInstance().getAllTransactions());
        transaction_listview.setCellFactory(e -> new TransactionCellFactory());
    }

    private void initAllTransactionsList(){
        if(Model.getInstance().getAllTransactions().isEmpty()){
            Model.getInstance().setAllTransactions();
        }
    }
}
