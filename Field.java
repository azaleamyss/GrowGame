import java.util.ArrayList;
public class Field{
    private static final int ROT_TIME = 180;//180分
    private Carrot carrot;
    private DrinkType currentDrink;
    private int passedTime;
    
    Field(ArrayList<String> log){
        this.currentDrink = DrinkType.valueOf(log.get(0));
        this.passedTime = Integer.parseInt(log.get(1));
        CarrotType type = CarrotType.valueOf(log.get(2));
        int typeNum = DrinkType.values().length-1;
        int[] gainedDrinks = new int[typeNum];
        for(int i=3; i<typeNum+3; i++){
            gainedDrinks[i] = Integer.parseInt(log.get(i));
        }
        carrot = new Carrot(type,gainedDrinks);
    }

    public ArrayList<String> getLog(){
        return null;
    }

    //時間に合わせて処理
    //引数 int fold: 倍率
    //5分ごとに実行される
    public void passTime(){
        if(ROT_TIME < passedTime){
            //腐る
            carrot.rot();
        }else{
            if(carrot.haveGrown()){
                //もう変化しない
            }else{
                //可能性を秘めている
                for(CarrotType theType: CarrotType.values()){
                    if(theType.wasGrown(carrot.getCondition())){
                        carrot.setType(theType);
                    }
                }
            }
        }

        carrot.gain(currentDrink);
        passedTime += 5;
        currentDrink = null;
    }
}
