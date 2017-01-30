import java.util.ArrayList;
public class Field{
    private static final int ROT_TIME = 180;//180分
    private Carrot carrot;
    private DrinkType currentDrink;
    private int passedTime;
    
    Field(ArrayList<String> log){
        this.currentDrink = DrinkType.parseDrinkType(log.get(0));
        this.passedTime = Integer.parseInt(log.get(1));
        int typeNum = DrinkType.values().length-1;
        int[] gainedDrinks = new int[typeNum];
        for(int i=0; i<typeNum; i++){
            gainedDrinks[i] = Integer.parseInt(log.get(2+i));
        }
        boolean isrotten;
        if(this.passedTime < ROT_TIME){
            isrotten = false;
        }else{
            isrotten = true;
        }
        carrot = new Carrot(gainedDrinks,isrotten);
    }

    public ArrayList<String> getLog(){
        /*未実装*/
        return null;
    }

    //時間に合わせて処理
    //引数 int fold: 倍率
    //5分ごとに実行される
    public void passTime(){
        if(ROT_TIME < passedTime){
            carrot.rot();//腐る
        }
        carrot.gain(currentDrink);
        passedTime += 5;
        currentDrink = null;
    }
}
