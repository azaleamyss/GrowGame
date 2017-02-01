import javafx.scene.image.Image;
public enum CarrotType{
    ORANGE("ふつうのにんじん",500, new Image("image/carrot/ninjin1.png")),
    ROUND("まるいにんじん",700, new Image("image/carrot/daikon2.png")),
    WHITE("しろいにんじん",1500, new Image("image/carrot/daikon1.png")),
    BIG("おおきなにんじん",1700, new Image("image/carrot/goya.png")),
    FAT("おでぶなにんじん",2300, new Image("image/carrot/satsumaimo.png")),
    GOLD("おうごんのにんじん",8000, new Image("image/carrot/banana.jpg")),
    GREEN("くさったにんじん",50, new Image("image/carrot/ninjin2.png"));

    private final String japanese;//日本語名
    private final int price;//売値
    private int[] growthCondition;//成長条件
    private final Image image;//画像

    CarrotType(String japanese,int price, Image image){
        this.japanese = japanese;
        this.price = price;
        this.image = image;
        defineCondition(this);
    }

    //人参の成長条件を定義
    private void defineCondition(CarrotType type){
        if(type == ORANGE){
              int[] temp={0,0,0,0,0,0,0};
              this.growthCondition=temp;
        } else if(type == ROUND) {
              int[] temp={1,2,0,0,0,0,0};
              this.growthCondition=temp;
        } else if(type == WHITE) {
              int[] temp={0,0,0,3,0,0,0};
              this.growthCondition=temp;
        } else if(type ==BIG) {
              int[] temp={1,2,1,0,0,1,0};
              this.growthCondition=temp;
        } else if(type == FAT) {
              int[] temp={0,0,0,0,2,0,1};
              this.growthCondition=temp;
        } else if(type == GOLD) {
              int[] temp={1,1,1,1,1,1,1};
              this.growthCondition=temp;
        } else if(type == GREEN) {
              int[] temp={0,0,0,0,0,0,0};
              this.growthCondition=temp;
        }
        //順番: DrinkTypeの定義順
    }
    public int price(){
        return this.price;
    }
    //成長条件を満たしているかを確認
    public boolean wasGrown(int[] condition){
        for(int i = 0;i < 7;i++){
            if(condition[i] < growthCondition[i]){
                return false;
            } 
        }
        return true;
    }

    public static CarrotType get(int idx){
        return CarrotType.values()[idx]; 
    }
}
