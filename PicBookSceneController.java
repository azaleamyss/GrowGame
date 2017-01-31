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

public class PicBookSceneController extends GrowGameController implements Initializable,ControlledScreen {
    private ArrayList<String> picBookPages;

    ScreensController myController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("図鑑");
        picBook = new PictureBook();
    }    
    
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToHatake(ActionEvent event){
       myController.setScreen(GrowGame.screen1ID);
    }

    //見たい種類(飲み物図鑑かにんじん図鑑)を選ぶ
    @FXML
    private void drinkPageButtonAction(ActionEvent event){
        picBookPages = picBook.load("drink"); 
    } 
    @FXML
    private void carrotPageButtonAction(ActionEvent event){
        picBookPages = picBook.load("carrot"); 
    } 

    //図鑑のページをめくる
    @FXML
    private void nextPageButtonAction(ActionEvent event){

    }
    @FXML
    private void beforePageButtonAction(ActionEvent event){
    }

    private void read(){
    }
}

