import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.ArrayList;

public class StorageSceneController extends GrowGameController implements Initializable,ControlledScreen {

    ScreensController myController;

    private Label[] dispLabels;//項目表示
    @FXML private Label dispLabel1;
    @FXML private Label dispLabel2;
    @FXML private Label dispLabel3;
    @FXML private Label dispLabel4;
    @FXML private Label dispLabel5;
    @FXML private Label dispLabel6;
    @FXML private Label dispLabel7;
    @FXML private VBox storage_box;
    @FXML private Label storage_none;//画面遷移時用

    static{
        //取得したログから倉庫インスタンス生成
        ArrayList<String> callotLog = logController.getStorageLog("callots");
        ArrayList<String> drinkLog = logController.getStorageLog("drinks");
        ArrayList<String> seedsLog = logController.getStorageLog("seeds");
        ArrayList<String> moneyLog = logController.getStorageLog("money");

        storage = new Storage(callotLog,drinkLog,seedsLog,moneyLog);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dispLabels = new Label[7];
        dispLabels[0] = dispLabel1;
        dispLabels[1] = dispLabel2;
        dispLabels[2] = dispLabel3;
        dispLabels[3] = dispLabel4;
        dispLabels[4] = dispLabel5;
        dispLabels[5] = dispLabel6;
        dispLabels[6] = dispLabel7;
        System.out.println("hoge");
    }    
    
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToHatake(ActionEvent event){
       myController.setScreen(GrowGame.screen1ID);
    }

    @FXML
    private void dispDrinkButtonAction(ActionEvent event){
        disp(storage.getStoredDrinks(),Item.DRINK);
    }
    @FXML
    private void dispCarrotButtonAction(ActionEvent event){
        disp(storage.getStoredCarrots(),Item.CARROT);
    }
    @FXML
    private void dispSeedsButtonAction(ActionEvent event){
        disp(storage.getStoredSeeds(),Item.SEED);
    }
    @FXML
    private void dispMoneyButtonAction(ActionEvent event){
        disp(storage.getStoredMoney(),Item.MONEY);
    }

    //在庫を表示(0個のものは表示されない)
    private void disp(ArrayList<String> storedItemNum, Item item){
        for(int i=0; i<7; i++){
            dispLabels[i].setOpacity(0.0d);
        }

        int i = 0;
        String text = "";
        for(String s: storedItemNum){
            if(item == Item.MONEY || item == Item.SEED){
                text = item.name() + "・・・"+ s +" "+item.unit();
            }else if(item == Item.CARROT){
                text = CarrotType.get(i).name() + "・・・" + storedItemNum.get(i) +" "+ item.unit();
            }else if(item == Item.DRINK){
                text = DrinkType.get(i).name() + "・・・" + storedItemNum.get(i) +" "+ item.unit();
            }

            if(!s.equals("0") || item == Item.MONEY || item == Item.SEED){
                dispLabels[i].setText(text);
                dispLabels[i].setOpacity(0.7d);
                i++;
            }
        }
    }
}

