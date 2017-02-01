import javafx.scene.image.Image;
public enum DrinkType{
    WATER("みず", 100, new Image("image/drink/water.png")),
    TEA("おちゃ", 200, new Image("image/drink/tea.png")),
    SHOYU("しょうゆ", 300, new Image("image/drink/cooking_syouyu_bottle.png")),
    MILK("ぎゅうにゅう", 400, new Image("image/drink/milk.png")),
    COLA("コーラ", 500, new Image("image/drink/cola.png")),
    VEER("ビール", 600, new Image("image/drink/beer.png")),
    WINE("ワイン", 1000, new Image("image/drink/cooking_wine_red.png"));

    private final String japanese;
    private final int price;
    private final Image image;
    
    DrinkType(String japanese,int price, Image image){
        this.japanese = japanese;
        this.price = price;
        this.image = image;
    }
    public int price(){
        return this.price;
    }

    public Image image(){
        return this.image;
    }

    public static DrinkType get(int idx){
        return DrinkType.values()[idx];
    }
}
