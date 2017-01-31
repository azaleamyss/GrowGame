import java.util.ArrayList;
public class Carrot{
    private CarrotType type;
    private int[] condition;

    //育成用
    Carrot(CarrotType type, int[] gainedDrinks){
        this.type = type;//最初はみんなORANGE
        this.condition = gainedDrinks;
    }

    //人参の成長条件定義用(→ enumに変更)
    Carrot(CarrotType type, int water, int tea, int shoyu, int milk, int cola, int veer, int wine){
        this.type = type;
        condition = new int[DrinkType.values().length-1];
        condition[0] = water;
        condition[1] = tea;
        condition[2] = shoyu;
        condition[3] = milk;
        condition[4] = cola;
        condition[5] = veer;
        condition[6] = wine;
    }

    public void setType(CarrotType type){
        this.type = type;
    }

    public CarrotType getType(){
        return this.type;
    }
    public int[] getCondition(){
        return this.condition;
    }

    //畑から養分を吸収
    public void gain(DrinkType drink){
        condition[drink.ordinal()]++;
    }

    //腐る
    public void rot(){
        this.type = CarrotType.GREEN;
    }
    public boolean isRotten(){
        if(type == CarrotType.GREEN){
            return true;
        }else{
            return false;
        }
    }

    //成長期が終わったかを確認
    public boolean haveGrown(){
        if(type == CarrotType.ORANGE){
            return false;
        }else{
            return true;
        }
    }

    //ログ
    public ArrayList<String> getLog(){
        ArrayList<String> log = new ArrayList<String>();
        log.add(type.name());
        for(int i=0; i<DrinkType.values().length-1; i++){
            log.add(condition[i]+"");
        }

        return log;
    }
}
