package com.jmc.HelloTbank.Controllers;

import com.jmc.HelloTbank.Models.Model;
import com.jmc.HelloTbank.Views.AccountType;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.util.Optional;


import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public ChoiceBox<AccountType> acc_selector;
    public Label payee_address_lbl;
    public TextField payee_address_fld;
    public TextField password_fld;
    public Button login_btn;
    public Label error_lbl;

    @Override
    public void initialize (URL url, ResourceBundle resourcebundle){
        acc_selector.setItems(FXCollections.observableArrayList(AccountType.CLIENT, AccountType.ADMIN));
        acc_selector.setValue(Model.getInstance().getViewFactory().getLoginAccountType());
        acc_selector.valueProperty().addListener(observable -> setAcc_selector());
        login_btn.setOnAction(event -> onLogin());
    }

    private void onLogin () {
        Stage stage = (Stage) error_lbl.getScene().getWindow();
        if (Model.getInstance().getViewFactory().getLoginAccountType() == AccountType.CLIENT) {

            //phỏng đoán thông tin đăng nhập của khách hàng

            Model.getInstance().evaluateClientCred(payee_address_fld.getText(), password_fld.getText());
            if (Model.getInstance().getClientLoginSuccessFlag()){
                Model.getInstance().getViewFactory().showClientWindow();
                //đóng stage đăng nhập
                Model.getInstance().getViewFactory().closeStage(stage);
            } else {
                payee_address_fld.setText("");
                password_fld.setText("");
                error_lbl.setText("Tên tài khoản hoặc mật khẩu không đúng");

        } else {
            // phỏng đoán cho admin
            Model.getInstance().evaluateAdminCred(payee_address_fld.getText(), password_fld.getText());
            if (Model.getInstance().getAdminLoginSuccessFlag()){
                Model.getInstance().getViewFactory().showAdminWindow();
                //đóng stage đăng nhập
                Model.getInstance().getViewFactory().closeStage(stage);
            } else {
                payee_address_fld.setText("");
                password_fld.setText("");
                error_lbl.setText("Tên tài khoản hoặc mật khẩu không đúng");
            }
        }
    }

    private void setAcc_selector (){
        Model.getInstance().getViewFactory().setLoginAccountType(acc_selector.getValue());
        if (acc_selector.getValue() == AccountType.ADMIN){
            payee_address_lbl.setText("Tên tài khoản:");
        } else {
            payee_address_lbl.setText("Địa chỉ nhận tiền:");
        }
    }
}
