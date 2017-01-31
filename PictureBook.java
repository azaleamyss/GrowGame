import java.util.ArrayList;
public class PictureBook{
    private ArrayList<String> pageCarrot;
    private ArrayList<String> pageDrink;

    PictureBook(){
        pageCarrot = new ArrayList<String>();
        pageDrink = new ArrayList<String>();
    }

    public ArrayList<String> load(Item item){
        if(item == Item.CARROT){
            return pageCarrot;
        }else if(item == Item.DRINK){
            return pageDrink;
        }
    }
}
