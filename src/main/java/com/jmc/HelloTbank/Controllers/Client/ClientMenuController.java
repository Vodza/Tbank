package com.jmc.HelloTbank.Controllers.Client;

import com.jmc.HelloTbank.Models.Model;
import com.jmc.HelloTbank.Views.ClientMenuOption;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable{
    public Button dashboard_btn;
    public Button transaction_btn;
    public Button accounts_btn;
    public Button profile_btn;
    public Button logout_btn;
    public Button report_btn;

    @Override
    public void initialize (URL url, ResourceBundle resourcebundle){
        addListeners();
    }

    public void addListeners () {
        dashboard_btn.setOnAction(event -> onDashboard());
        transaction_btn.setOnAction(event -> onTransaction());
        accounts_btn.setOnAction(event -> onAccounts());
        logout_btn.setOnAction(event -> onLogout());
    }

    private void onDashboard() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOption.DASHBOARD);
    }

    private void onTransaction() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOption.TRANSACTION);
    }

    private void onAccounts() {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set(ClientMenuOption.ACCOUNT);
    }

    private void onLogout () {
        //mở stage
        Stage stage = (Stage) dashboard_btn.getScene().getWindow();
        //đóng cửa sổ client
        Model.getInstance().getViewFactory().closeStage(stage);
        //hiện cửa sổ client
        Model.getInstance().getViewFactory().showLoginWindow();
        //thiết lập đăng nhập client
        Model.getInstance().setClientLoginSuccessFlag(false);
    }
}