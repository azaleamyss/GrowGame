import java.util.ArrayList;
public class Carrot{
    private CarrotType type;
    private int[] condition;
    private boolean isrotten;

    //育成用
    Carrot(int[] gainedDrinks, boolean isrotten){
        this.type = CarrotType.ORANGE;
        this.condition = gainedDrinks;
        this.isrotten = isrotten; 
    }

    //人参の成長条件定義用
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
        this.isrotten = true;
    }
    public boolean isRotten(){
        return isrotten;
    }

    //ログ
    public ArrayList<String> getLog(){
        ArrayList<String> log = new ArrayList<String>();
        return log;
    }
}
