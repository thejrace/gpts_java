/* Gitaş - Obarey Inc 2018 */
package gpts.java.controllers;

import com.jfoenix.controls.JFXTextField;
import gpts.java.DailyPlan;
import gpts.java.ui.PopupLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class PlanFormController extends PopupFormBaseController implements Initializable {

    @FXML private JFXTextField uiNameInput;
    @FXML private JFXTextField uiStartInput;
    @FXML private JFXTextField uiEndInput;
    @FXML private JFXTextField uiIntervalInput;

    @Override
    public void initialize(URL url, ResourceBundle rb ){
        super.initCommonEvents();

        uiSaveBtn.setOnMouseClicked( ev -> {
            uiSaveBtn.setDisable(true);
            DailyPlan plan = new DailyPlan();
            if( !plan.add( uiNameInput.getText(), uiStartInput.getText(), uiEndInput.getText(), uiIntervalInput.getText() ) ){
                outputError(plan.getReturnText());
                uiSaveBtn.setDisable(false);
            } else {
                mParentDialog.close();
                PopupLoader.showMessage(plan.getReturnText(), PopupLoader.MESSAGE_SUCCESS );
                // todo -> eklenen, listeye dahil edilecek
            }
        });


    }

}