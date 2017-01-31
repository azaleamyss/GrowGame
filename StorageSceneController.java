import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import java.util.ArrayList;

public class StorageSceneController extends GrowGameController implements Initializable,ControlledScreen {
    ScreensController myController;

    @FXML private Label dispLabel1;
    @FXML private Label dispLabel2;
    @FXML private Label dispLabel3;
    @FXML private Label dispLabel4;
    @FXML private Label dispLabel5;
    @FXML private Label dispLabel6;
    @FXML private Label dispLabel7;

    private Label[] dispLabels;

    static{
        //取得したログから倉庫インスタンス生成
        ArrayList<String> callotLog = logController.getStorageLog("callots");
        ArrayList<String> drinkLog = logController.getStorageLog("drinks");
        ArrayList<String> seedsLog = logController.getStorageLog("seeds");
        ArrayList<String> moneyLog = logController.getStorageLog("money");
        int seeds = Integer.parseInt(seedsLog.get(0));
        int money = Integer.parseInt(moneyLog.get(0));

        storage = new Storage(callotLog,drinkLog,seeds,money);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        disp(storage.getStoredDrinks());
    }
    @FXML
    private void dispSeedsButtonAction(ActionEvent event){
        disp(storage.getStoredSeeds());
    }
    @FXML
    private void dispCarrotButtonAction(ActionEvent event){
        disp(storage.getStoredCarrots());
    }

    //在庫を表示(0個のものは表示されない)
    private void disp(ArrayList<String> list){
    }
}
