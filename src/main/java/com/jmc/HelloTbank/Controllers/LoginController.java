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
                public class LoginController extends Application {
                    Button button;
                    public static void main(String[] args) {
                        launch(args);
                    }

                    @Override
                    public void start(Stage primaryStage) {
                        primaryStage.setTitle("Alert Example");
                        button = new Button();
                        button.setText("Close");
                        button.setOnAction(e -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Cofirmation");
                            alert.setHeaderText("Alert Information");
                            alert.setContentText("choose your option");

                            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
                            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);

                            Optional<ButtonType> result = alert.showAndWait();

                            if (result.get()== buttonTypeYes)
                                System.out.println("Code for yes");
                            else if (result.get().getButtonData() == ButtonBar.ButtonData.NO)
                                System.out.println("Code for no");
                            else
                                System.out.println("Code for cancel");
                            String message = result.get().getText();
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.setTitle("Information");
                            alert1.setHeaderText("Notification");
                            alert1.setContentText(message);
                            alert1.show();
                        });
                        StackPane layout = new StackPane();
                        layout.getChildren().add(button);
                        Scene scene = new Scene(layout, 300, 250);
                        primaryStage.setScene(scene);
                        primaryStage.show();
                    }
                }
                }

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