package gpts.java.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import gpts.java.GWork;
import gpts.java.interfaces.FormActionListener;
import gpts.java.ui.GWorkBox;
import gpts.java.ui.GWorksPage;
import gpts.java.ui.PopupLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GWorksController extends BaseContentController implements Initializable {

    @FXML private JFXButton uiAddBtn;
    private GWorksPage mPage;

    @Override
    public void initialize(URL url, ResourceBundle rb ){
        PopupLoader.show("Veri alınıyor..");

        uiAddBtn.setOnMouseClicked( ev -> {
            try {
                JFXDialog dialog = new JFXDialog();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/gpts/res/fxml/forms/work_form.fxml"));
                ScrollPane ui  = loader.load();
                GWorkFormController controller = loader.getController();
                controller.setTemplateFlag(true);
                controller.setFormActionListener(new FormActionListener() {
                    @Override
                    public void onFinish(Object object) {
                        GWork workData = (GWork)object;
                        mPage.addItem( String.valueOf(workData.getID()), new GWorkBox( workData ), true, true );
                    }
                });
                dialog.setContent( ui );
                dialog.setOverlayClose(false);
                dialog.show( (StackPane) ((Node) ev.getSource()).getScene().getRoot() );
                // pass the dialog to controller to trigger close form cancel button
                controller.setDialog( dialog );
            } catch( IOException e ){
                e.printStackTrace();
            }

        });

        // search
        uiSearchBtn.setOnMouseClicked( ev -> {
            int searchActionType = super.searchAction();
            if( searchActionType == BaseContentController.SEARCH ){
                String searchKeyword = uiSearchInput.getText().trim();
                mPage.search( searchKeyword );
            } else if( searchActionType == BaseContentController.SEARCHCANCEL ){
                mPage.cancelSearch();
            }
        });

        // load more rows
        uiMoreBtn.setOnMouseClicked( ev -> {
            mPage.downloadData();
        });


    }

    public void setPageObject( GWorksPage page ){
        mPage = page;
    }

}
