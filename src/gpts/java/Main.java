/* Gitaş - Obarey Inc 2018 */
package gpts.java;

import gpts.java.interfaces.ActionCallback;
import gpts.java.interfaces.NoParamCallback;
import gpts.java.ui.LoginScreen;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.Map;

public class Main extends Application {

    // we use this access it from MainController
    public static Stage stage = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Common.checkStaticFileLocation(new NoParamCallback() {
            @Override
            public void action() {
                ApiUser.checkLocalLoginData(new ActionCallback() {
                    @Override
                    public void onSuccess(String... params) {
                        try {
                            ApiUser.checkDevice(new ActionCallback() {
                                @Override
                                public void onSuccess(String... params) {
                                    // prevent ending the process when last window is closed.
                                    // process is killed when terminate buttons is clicked
                                    // https://docs.oracle.com/javase/8/javafx/api/javafx/application/Platform.html#setImplicitExit-boolean-
                                    Platform.setImplicitExit(false);
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            initMainUI( new Stage() );
                                        }
                                    });
                                }
                                @Override
                                public void onError(int type) {
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            Alert alert = new Alert(Alert.AlertType.ERROR);
                                            alert.setTitle("Gitaş Filo Personel Takip Sistemi");
                                            alert.setHeaderText("Hata oluştu. Kod: KYB_1");
                                            alert.setContentText("Sistem yöneticisine hatayı bildirin.");
                                            ButtonType cancelBtn = new ButtonType("İptal", ButtonBar.ButtonData.CANCEL_CLOSE);
                                            alert.getButtonTypes().setAll(cancelBtn );
                                            alert.showAndWait();
                                        }
                                    });
                                }
                            });
                        } catch( Exception e ){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(int type) {
                        initLoginUI();
                    }
                });
            }
        });

    }

    private void initMainUI( Stage primaryStage ){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gpts/res/fxml/main.fxml"));
            Parent content = loader.load();
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setTitle("Gitaş PTS");
            Map<String, Double> resData = Common.calculateAppWindowSize();
            primaryStage.setScene(new Scene(content, resData.get("W"), resData.get("H") ));
            stage = primaryStage;
            stage.getIcons().add(new Image(getClass().getResource("/gpts/res/img/gpts_ico.png").toExternalForm()));
            primaryStage.show();
            ServerSync.start();
        } catch( Exception e ){
            e.printStackTrace();
        }

    }
    private void initLoginUI(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // again set the 'dont kill the process' flag
                    Platform.setImplicitExit(false);
                    LoginScreen loginScreen = new LoginScreen();
                    loginScreen.start( new Stage() );
                } catch( Exception e ){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }


}
