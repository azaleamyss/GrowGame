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

public class ShopSceneController extends GrowGameController implements Initializable, ControlledScreen  {
    ScreensController myController;

    @FXML private ImageView productImageView;//商品のイメージ
    @FXML private Label costLabel;//値段を表示
    @FXML private Label shopperLabel;//店員の発言を表示
    @FXML private Label storageMoneyLabel;//所持金の表示

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        shopperLabel.setText("いらっしゃいませ!");
        costLabel.setText("0");
    }

    //ラベルに表示されている合計の値段を返す
    private int getCost(){
        int cost = Integer.parseInt(costLabel.getText());
        return cost;
    }

    //購入時の処理
    @FXML
    private void buyButtonAction(ActionEvent event){
        int cost = getCost();
        int money = Integer.parseInt(storage.getStoredMoney().get(0));
        if(cost <= money){
            shopperLabel.setText("ありがとうございました!");
            money -= cost;
            storage.getStoredMoney().set(0,money+"");
            storageMoneyLabel.setText(money+""); 
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

    @FXML
    private void seedButtonAction(ActionEvent event){
        productImageView.setImage(Item.SEED.image());
    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToHatake(ActionEvent event){
       myController.setScreen(GrowGame.screen1ID);
       shopperLabel.setText("いらっしゃいませ！");
       costLabel.setText("0");
       productImageView.setImage(null);
    }
}
