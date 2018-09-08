package gpts.java.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import gpts.java.GWorkSubItem;
import gpts.java.interfaces.NoParamCallback;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GWorkSubItemBoxController implements Initializable {

    @FXML private VBox uiMainContainer;
    @FXML private JFXTextField uiNameInput;
    @FXML private JFXButton uiValidationBtn;
    @FXML private JFXTextArea uiDetailsInput;
    @FXML private JFXComboBox uiStatusInput;
    @FXML private JFXButton uiSaveBtn;
    @FXML private JFXButton uiDeleteBtn;

    private String[] mStatusList = { "Beklemede", "Aktif", "Onay Bekliyor", "İptal Edildi" };
    private NoParamCallback mDeleteListener;

    @Override
    public void initialize(URL url, ResourceBundle rb ){

        // add status data to comboBox
        for( int k = 0; k < mStatusList.length; k++ ) uiStatusInput.getItems().add( mStatusList[k] );

        uiDeleteBtn.setOnMouseClicked( ev -> {
           mDeleteListener.action();
        });

    }

    public void setData( GWorkSubItem data ){

    }

    public void addDeleteListener( NoParamCallback cb ){
        mDeleteListener = cb;
    }

    public void setStyleClassName( String className ){
        uiMainContainer.getStyleClass().add(className);
    }

}
