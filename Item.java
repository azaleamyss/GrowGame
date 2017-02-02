import javafx.scene.image.Image;
public enum Item{
    CARROT("こ"),
    DRINK("ほん"),
    SEED("こ",new Image("image/tane.jpg")),
    MONEY("きゃろ");

    private final String unit;
    private final Image image;

    Item(String unit){
        this.unit = unit;
        this.image = null;
    }

    Item(String unit, Image image){
        this.unit = unit;
        this.image = image;
    }

    public Image image(){
        return this.image;
    }

    public String unit(){
        return this.unit;
    }
}

