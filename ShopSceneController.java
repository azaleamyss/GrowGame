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

public class ShopSceneController extends GrowGameController implements Initializable, ControlledScreen  {
    ScreensController myController;

    @FXML
    private ImageView productImageView;
    @FXML
    private Label totalCostLabel;
    
    @FXML private Label shopperLabel;
    @FXML private Label nowMoneyLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        shopperLabel.setText("いらっしゃいませ!");
    }

    private int getTotalCost(){
        int total = Integer.parseInt(totalCostLabel.getText());
        return total;
    }

    //購入時の処理
    @FXML
    private void buyButtonAction(ActionEvent event){
        int total = getTotalCost();
        int money = Integer.parseInt(storage.getStoredMoney().get(0));
        if(total <= money){
            shopperLabel.setText("ありがとうございました!");
            money -= total;
            storage.getStoredMoney().set(0,money+"");
            nowMoneyLabel.setText("お金: "+money); 
        }else{
            shopperLabel.setText("残高不足です!");
        }
    }

    //飲み物各種ボタン
    @FXML
    private void waterButtonAction(ActionEvent event){
        productImageView.setImage(DrinkType.WATER.image());
    }
    @FXML
    private void teaButtonAction(ActionEvent event){
        productImageView.setImage(DrinkType.TEA.image());
    }
    @FXML
    private void milkButtonAction(ActionEvent event){
        productImageView.setImage(DrinkType.MILK.image());
    }
    @FXML
    private void colaButtonAction(ActionEvent event){
        productImageView.setImage(DrinkType.COLA.image());
    }
    @FXML
    private void beerButtonAction(ActionEvent event){
        productImageView.setImage(DrinkType.VEER.image());
    }
    @FXML
    private void wineButtonAction(ActionEvent event){
        productImageView.setImage(DrinkType.WINE.image());
    }
    @FXML
    private void shoyuButtonAction(ActionEvent event){
        productImageView.setImage(DrinkType.SHOYU.image());
    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToHatake(ActionEvent event){
       myController.setScreen(GrowGame.screen1ID);
    }
}
