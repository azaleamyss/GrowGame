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
    private static Mode nowMode;
    private static Timeline timer;

    @FXML
    private Label dateLabel;//畑画面左上に表示したいです
    @FXML
    private ImageView weatherImageView;
    @FXML
    private ImageView drinkImageView;

    @FXML private ImageView fieldTopLeft;
    @FXML private ImageView fieldTopCenter;
    @FXML private ImageView fieldTopRight;
    @FXML private ImageView fieldUnderLeft;
    @FXML private ImageView fieldUnderCenter;
    @FXML private ImageView fieldUnderRight;

    private ImageView[] fieldAreaView;

    ScreensController myController;

    static{
        int passCicle = getDateDiff(passDate)/5;
        fieldArea = new ArrayList<Field>();
        for(FieldPos pos: FieldPos.values()){
            ArrayList<String> log = logController.getFieldLog(pos);
            Field logf = new Field(log);
            for(int i=0; i<passCicle; i++){
                logf.passTime();//プレイしてない分を進ませる
            }
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
    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<String> date = getNowDate();
        fieldAreaView = new ImageView();
        fieldAreaView[0] = fieldTopLeft;
        fieldAreaView[1] = fieldTopCenter;
        fieldAreaView[2] = fieldTopRight;
        fieldAreaView[3] = fieldUnderLeft;
        fieldAreaView[4] = fieldUnderCenter;
        fieldAreaView[5] = fieldUnderRight;
        //dateLabel.setText(date.get(1)+"月 "+date.get(2)+"日");
    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    
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
        int rest = Integer.parseInt(storage.getStoredSeeds().get(0)) -1;
        if(rest < 0){
            rest = 0;
        }
        storage.setStoredSeeds(storage.getStoredSeeds().set(0,rest));
        Field field = fieldArea.get(pos.id());
        field.setNewCarrot();//畑を再構築しない場合
        //fieldArea.set(pos.id(), new Field());//再構築する場合
        changeImage(field,pos);
    }
    private void pour(FieldPos pos){
        //まず選択した飲み物の種類を取得する
    }
    private void harvest(FieldPos pos){
    }

    //畑の画像が変わる
    private void changeFieldImage(Field field, FieldPos pos){
        if(field.getPassedTime() < 2){
            //植えたて
            fieldAreaView[pos.id()].setImage("image/veg/uetate.png");
        }else if(field.getPassedTime() < 6){
            //芽
            fieldAreaView[pos.id()].setImage("image/veg/hatuga.png");
            if(field.getCarrot().hasEvolved()){
                sleep(500);
                fieldAreaView[pos.id()].setImage("image/veg/uetate.png");
                sleep(500);
                fieldAreaView[pos.id()].setImage("image/veg/hatuga.png");
                sleep(500);
                field.getCarrot().evolve(false);
            }
        }else{
            //leaf.png
            fieldAreaView[pos.id()].setImage("image/veg/leaf.png");
            if(field.getCarrot().hasEvolved()){
                sleep(500);
                fieldAreaView[pos.id()].setImage("image/veg/hatuga.png");
                sleep(500);
                fieldAreaView[pos.id()].setImage("image/veg/leaf.png");
                sleep(500);
                field.getCarrot().evolve(false);
            }
        } 
    }
}
