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

public class FieldSceneController extends GrowGameController implements Initializable, ControlledScreen {
    private enum Mode{
        SOW,POUR,HARVEST,NONE
    }
    private Mode nowMode;
    private static Timeline timer;

    @FXML
    private Label dateLabel;//畑画面左上に表示したいです

    ScreensController myController;

    static{
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
        System.out.println("FieldSceneControllerほぼ未実装");     
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String date = getDispDate();
        //dateLabel.setText(date);
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

    //種まきボタン
    @FXML
    private void sowButtonAction(ActionEvent event){
        nowMode = Mode.SOW;
    }

    //水やりボタン
    @FXML
    private void pourButtonAction(ActionEvent event){
        nowMode = Mode.POUR;
    }

    //収穫ボタン
    @FXML
    private void harvestButtonAction(ActionEvent event){
        nowMode = Mode.HARVEST;
    }

    //畑のどれかをクリック
    @FXML
    private void posTLButtonAction(ActionEvent event){
        fieldClickAction(FieldPos.TOP_LEFT);
    }
    @FXML
    private void posTCButtonAction(ActionEvent event){
        fieldClickAction(FieldPos.TOP_CENTER);
    }
    @FXML
    private void posTRButtonAction(ActionEvent event){
        fieldClickAction(FieldPos.TOP_RIGHT);
    }
    @FXML
    private void posULButtonAction(ActionEvent event){
        fieldClickAction(FieldPos.UNDER_LEFT);
    }
    @FXML
    private void posUCButtonAction(ActionEvent event){
        fieldClickAction(FieldPos.UNDER_CENTER);
    }
    @FXML
    private void posURButtonAction(ActionEvent event){
        fieldClickAction(FieldPos.UNDER_RIGHT);
    }
    //クリックした場所にいずれかの処理を実施
    private void fieldClickAction(FieldPos pos){
        if(nowMode == Mode.SOW){
            sow(pos);
        }else if(nowMode == Mode.POUR){
            pour(pos);
        }else if(nowMode == Mode.HARVEST){
            harvest(pos);
        }
        changeImage(pos);
    }

    private void sow(FieldPos pos){
    }
    private void pour(FieldPos pos){
        //まず選択した飲み物の種類を取得する
    }
    private void harvest(FieldPos pos){
    }

    //畑の画像が変わる
    private void changeImage(FieldPos pos){
    }
}
