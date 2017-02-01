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
    private Label field_timer;//畑画面左上に表示したいです
    @FXML
    private ImageView weatherImageView;
    @FXML
    private ImageView drinkImageView;

    @FXML private ImageView TLImageView0;
    @FXML private ImageView TLImageView1;
    @FXML private ImageView TLImageView2;
    @FXML private ImageView TRImageView0;
    @FXML private ImageView TRImageView1;
    @FXML private ImageView TRImageView2;
    @FXML private ImageView CLImageView0;
    @FXML private ImageView CLImageView1;
    @FXML private ImageView CLImageView2;
    @FXML private ImageView CRImageView0;
    @FXML private ImageView CRImageView1;
    @FXML private ImageView CRImageView2;
    @FXML private ImageView ULImageView0;
    @FXML private ImageView ULImageView1;
    @FXML private ImageView ULImageView2;
    @FXML private ImageView URImageView0;
    @FXML private ImageView URImageView1;
    @FXML private ImageView URImageView2;

    private ImageView[][] fieldAreaView;

    private static Image[] processImg;

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

        for(Field f: fieldArea){

        }

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

        //imgの読み込み
        processImg = new Image[3];
        processImg[0] = new Image("/image/process1.jpg");
        processImg[1] = new Image("/image/process2.jpg");
        processImg[2] = new Image("/image/process3.png");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<String> date = getNowDate();
        fieldAreaViewInit();
    }

    private void fieldAreaViewInit(){
        fieldAreaView = new ImageView[6][3];
        fieldAreaView[0][0] = TLImageView0;
        fieldAreaView[0][1] = TLImageView1;
        fieldAreaView[0][2] = TLImageView2;
        fieldAreaView[1][0] = TRImageView0;
        fieldAreaView[1][1] = TRImageView1;
        fieldAreaView[1][2] = TRImageView2;
        fieldAreaView[2][0] = CLImageView0;
        fieldAreaView[2][1] = CLImageView1;
        fieldAreaView[2][2] = CLImageView2;
        fieldAreaView[3][0] = CRImageView0;
        fieldAreaView[3][1] = CRImageView1;
        fieldAreaView[3][2] = CRImageView2;
        fieldAreaView[4][0] = ULImageView0;
        fieldAreaView[4][1] = ULImageView1;
        fieldAreaView[4][2] = ULImageView2;
        fieldAreaView[5][0] = URImageView0;
        fieldAreaView[5][1] = URImageView1;
        fieldAreaView[5][2] = URImageView2;

        for(int i=0; i<6; i++){
            for(int j=0; j<3; j++){
                fieldAreaView[i][j].setImage(processImg[j]);
            }
        }
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

    @FXML
    private void goToTitle(ActionEvent event){
       myController.setScreen(GrowGame.screen4ID);
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
    private void posTRButtonAction(ActionEvent event){
        fieldClickAction(FieldPos.TOP_RIGHT);
    }
    @FXML
    private void posCLButtonAction(ActionEvent event){
        fieldClickAction(FieldPos.CENTER_LEFT);
    }
    @FXML
    private void posCRButtonAction(ActionEvent event){
        fieldClickAction(FieldPos.CENTER_RIGHT);
    }
    @FXML
    private void posULButtonAction(ActionEvent event){
        fieldClickAction(FieldPos.UNDER_LEFT);
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
    }

    private void sow(FieldPos pos){
        int rest = Integer.parseInt(storage.getStoredSeeds().get(0)) -1;
        if(rest < 0){
            rest = 0;
        }
        storage.getStoredSeeds().set(0,rest+"");
        Field field = fieldArea.get(pos.ordinal());
        field.setNewCarrot();//畑を再構築しない場合
        //fieldArea.set(pos.id(), new Field());//再構築する場合
        changeFieldImage(field,pos);
    }

    private void pour(FieldPos pos){
        //まず選択した飲み物の種類を取得する
    }

    private void harvest(FieldPos pos){
    }

    //畑の画像が変わる
    private void changeFieldImage(Field field, FieldPos pos){
        /*
        if(field.getPassedTime() < 2){
            //植えたて
            fieldAreaView[pos.id()].setImage(statusImage[0]);
        }else if(field.getPassedTime() < 6){
            //芽
            fieldAreaView[pos.id()].setImage(statusImage[1]);
            if(field.getCarrot().hasEvolved()){
                fieldAreaView[pos.id()].setImage(statusImage[0]);
                fieldAreaView[pos.id()].setImage(statusImage[1]);
                field.getCarrot().evolve(false);
            }
        }else{
            //leaf.png
            fieldAreaView[pos.id()].setImage(statusImage[2]);
            if(field.getCarrot().hasEvolved()){
                fieldAreaView[pos.id()].setImage(statusImage[1]);
                fieldAreaView[pos.id()].setImage(statusImage[2]);
                field.getCarrot().evolve(false);
            }
        } 
        */
    }
}
