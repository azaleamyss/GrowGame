import java.util.ArrayList;
public class Carrot{
    private CarrotType type;
    private int[] condition;
    private boolean rot_f;

    //育成用
    Carrot(CarrotType type, int[] gainedDrinks){
        this.type = type;//最初はみんなORANGE
        this.condition = gainedDrinks;
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
        this.rot_f = true;
    }
    public boolean isRotten(){
        return this.rot_f;
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
        for(int i=0; i<DrinkType.values().length; i++){
            log.add(condition[i]+"");
        }

        return log;
    }
}
