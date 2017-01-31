public class PictureBook{
    private ArrayList<String> pageCarrot;
    private ArrayList<String> pageDrink;

    PictureBook(){
        pageCarrot = new ArrayList<String>();
        pageDrink = new ArrayList<String>();
    }

    public ArrayList<String> load(String page){
        if(page.equals("carrot")){
            return pageCarrot;
        }else if(page.equals("drink")){
            return pageDrink;
        }
    }
}
