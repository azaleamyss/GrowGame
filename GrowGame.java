import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
public class GrowGame extends Application{
    public static String screen1ID = "field";
    public static String screen1File = "scene_field.fxml";
    public static String screen2ID = "shop";
    public static String screen2File = "scene_shop.fxml";
    public static String screen3ID = "storage";
    public static String screen3File = "scene_storage.fxml";
    public static String screen4ID = "title";
    public static String screen4File = "scene_title.fxml";

    @Override
    public void start(Stage primaryStage){
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(GrowGame.screen1ID, GrowGame.screen1File);
        mainContainer.loadScreen(GrowGame.screen2ID, GrowGame.screen2File);
        mainContainer.loadScreen(GrowGame.screen3ID, GrowGame.screen3File);
        mainContainer.loadScreen(GrowGame.screen4ID, GrowGame.screen4File);

        mainContainer.setScreen(GrowGame.screen4ID);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
