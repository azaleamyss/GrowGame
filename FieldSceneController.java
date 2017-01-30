import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import java.util.ArrayList;
import javafx.fxml.FXML;

public class FieldSceneController extends ObjectField implements Initializable, ControlledScreen {
    private static Timeline timer;
    ScreensController myController;

    static{
        logController = new LogController();
        fieldArea = new ArrayList<Field>();
        for(FieldPos pos: FieldPos.values()){
            ArrayList<String> log = logController.getFieldLog(pos);
            Field logf = new Field(log);
            fieldArea.add(logf);
        }

        growthCondition = new GrowthCondition();

        //タイマーの設定
        timer = new Timeline(new KeyFrame(Duration.millis(300000), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                for(Field field: fieldArea){
                    field.passTime();
                }
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
        System.out.println("FieldSceneControllerほぼ未実装);     
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    /*
    public void exitButtonAction(){
        //ログ出力
        ArrayList<ArrayList<String>> log;

        log = storage.getLog();
        logController.setStorageLog(log);
        logController.updateStorageLog();

        log = null;
        for(Field f: fieldArea){
            log.add(f.getLog());
        }
        logController.setFieldLog(log);
        logController.updateFieldLog();

        System.exit(0);
    }
    */
    
    @FXML
    private void goToShop(ActionEvent event){
       myController.setScreen(GrowGame.screen2ID);
    }
    
    @FXML
    private void goToStorage(ActionEvent event){
       myController.setScreen(GrowGame.screen3ID);
    }
}
