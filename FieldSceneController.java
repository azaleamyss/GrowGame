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
    @FXML
    private ImageView drinkButtonImageView;

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
    public static final int constraintTime = 60;//制約は1分
    private static int timer_cnt;

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

        //imgの読み込み
        /*
        processImg = new Image[3];
        processImg[0] = new Image("/image/leaf.jpg");
        processImg[1] = new Image("/image/me.jpg");
        processImg[2] = new Image("/image/sen.png");
        */

        timer_cnt = 0;
    }

    private ImageView[] getFieldAreaView(int idx){
        return fieldAreaView[idx];
    }


    //constraintTimeおきに時刻を取得
    private void updateDispTime(){
        ArrayList<String> now = getNowDate();
        field_timer.setText(now.get(1)+"月"+now.get(2)+"日 "+now.get(3)+"時"+now.get(4)+"分");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("change");
        ArrayList<String> date = getNowDate();
        fieldAreaViewInit();
        timerInit();
        updateDispTime();
        //drinkButtonImageView.setImage
    }

    private void timerInit(){
        timer = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                updateDispTime();
                for(Field f: fieldArea){
                    f.passTime();
                }
                updateFieldImage();
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
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

        /*
        for(int i=0; i<6; i++){
            for(int j=0; j<3; j++){
                fieldAreaView[i][j].setImage(processImg[j]);
            }
        }
        */

        updateFieldImage();
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
        System.out.println("種まき");
    }

    //水やりボタン
    @FXML
    private void pourButtonAction(ActionEvent event){
        nowMode = Mode.POUR;
        System.out.println("水やり");
    }

    //収穫ボタン
    @FXML
    private void harvestButtonAction(ActionEvent event){
        nowMode = Mode.HARVEST;
        System.out.println("収穫");
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
        Field field = fieldArea.get(pos.ordinal());
        if(field.isEmpty()){
            int rest = Integer.parseInt(storage.getStoredSeeds().get(0)) -1;
            if(rest < 0){
                rest = 0;
            }
            storage.getStoredSeeds().set(0,rest+"");
            field.setNewCarrot();//畑を再構築しない場合
        }else{
            System.out.println("無理です");
        }
        updateFieldImage();
    }

    private void pour(FieldPos pos){
        //まず選択した飲み物の種類を取得する

    }

    private void harvest(FieldPos pos){
        Field field = fieldArea.get(pos.ordinal());
        if(field.getCarrot().isRotten()){
            //腐った人参を取得
        }else{
            //人参の種類
        }
    }

    //畑の画像が変わる
    private void updateFieldImage(){
        for(FieldPos pos: FieldPos.values()){
            Field field = fieldArea.get(pos.ordinal());
            ImageView[] thisView = getFieldAreaView(pos.ordinal());
            if(field.isEmpty()){
                thisView[0].setOpacity(0.0d);
                thisView[1].setOpacity(0.0d);
                thisView[2].setOpacity(0.0d);
            }else{
                if(field.getPassedTime() < 120){
                    thisView[0].setOpacity(0.0d);
                    thisView[1].setOpacity(0.0d);
                    thisView[2].setOpacity(10d);
                }else if(field.getPassedTime() < 240){
                    thisView[0].setOpacity(0.0d);
                    thisView[1].setOpacity(10d);
                    thisView[2].setOpacity(0.0d);
                }else{
                    thisView[0].setOpacity(10d);
                    thisView[1].setOpacity(0.0d);
                    thisView[2].setOpacity(0.0d);
                }
            }
        }
    }
}
