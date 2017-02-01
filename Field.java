import java.util.ArrayList;
public class Field{
    private static final int ROT_TIME = 180;//180分
    private Carrot carrot;
    private DrinkType currentDrink;
    private int passedTime;
    
    //前の畑の状態を受け継ぐなら畑インスタンスの追加の生成は行わない
    Field(ArrayList<String> log){
        if(log.get(0).equals("NONE")){
            this.currentDrink = null;
        }else{
            this.currentDrink = DrinkType.valueOf(log.get(0));
        }
        this.passedTime = Integer.parseInt(log.get(1));
        CarrotType type = CarrotType.valueOf(log.get(2));
        int typeNum = DrinkType.values().length-1;
        int[] gainedDrinks = new int[typeNum];
        for(int i=0; i<typeNum; i++){
            gainedDrinks[i] = Integer.parseInt(log.get(i+3));
        }
        carrot = new Carrot(type,gainedDrinks);
    }

    public Carrot getCarrot(){
        return carrot;
    }

    public void setNewCarrot(){
        int[] initCondition = {0,0,0,0,0,0,0};
        this.carrot = new Carrot(CarrotType.ORANGE,initCondition);
    }

    //時間に合わせて処理
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
                    /*
                    if(theType.wasGrown(carrot.getCondition())){
                        carrot.setType(theType);
                    }
                    */
                }
            }
        }

        if(currentDrink != null){
            carrot.gain(currentDrink);
            System.out.println("foo");
        }else{
            System.out.println("null");
        }
        passedTime += 5;
        currentDrink = null;
    }

    public ArrayList<String> getLog(){
        ArrayList<String> log = new ArrayList<String>();
        log.add(this.currentDrink.name());
        log.add(this.passedTime+"");
        log.add(this.carrot.getType().name());
        int[] carrCondition = carrot.getCondition();
        for(int i=0; i<carrCondition.length; i++){
            log.add(carrCondition[i]+"");
        }
        return log;
    }

    public int getPassedTime(){
        return this.passedTime;
    }
}
