import java.util.ArrayList;
public class Field{
    private static final int ROT_TIME = 3600*3;//3hour
    private Carrot carrot;
    private DrinkType currentDrink;
    private int passedTime;
    private boolean isEmpty;
    
    //前の畑の状態を受け継ぐなら畑インスタンスの追加の生成は行わない
    Field(ArrayList<String> log){
        if(log.get(0).equals("empty")){
            this.isEmpty = true;
        }else{
            this.isEmpty = false;
        }
        System.out.println("type = "+log.get(1));
        if(log.get(1).equals("NONE")){
            this.currentDrink = null;
        }else{
            this.currentDrink = DrinkType.valueOf(log.get(1));
        }
        this.passedTime = Integer.parseInt(log.get(2));
        CarrotType type = CarrotType.valueOf(log.get(3));
        int typeNum = DrinkType.values().length;
        int[] gainedDrinks = new int[typeNum];
        for(int i=0; i<typeNum; i++){
            gainedDrinks[i] = Integer.parseInt(log.get(i+4));
        }
        carrot = new Carrot(type,gainedDrinks);
    }

    public Carrot getCarrot(){
        return carrot;
    }

    public boolean isEmpty(){
        return isEmpty;
    }


    public void setNewCarrot(){
        int[] initCondition = {0,0,0,0,0,0,0};
        this.carrot = new Carrot(CarrotType.ORANGE,initCondition);
        this.passedTime = 0;
        this.currentDrink = null;
        isEmpty = false;
    }

    //時間に合わせて処理
    //1sごとに実行される
    public void passTime(){
        this.passedTime++;
        if(this.passedTime > ROT_TIME){
            carrot.rot();//腐る
        }else{
        }

        if(this.passedTime > FieldSceneController.constraintTime){
            if(currentDrink != null){
                carrot.gain(currentDrink);
                System.out.println("gaine");
            }else{
                System.out.println("none drink");
            }
            currentDrink = null;
        }
    }

    public ArrayList<String> getLog(){
        ArrayList<String> log = new ArrayList<String>();
        if(this.isEmpty()){
            log.add("empty");
        }else{
            log.add("not-empty");
        }
        if(this.currentDrink == null){
            log.add("NONE");
        }else{
            log.add(this.currentDrink.name());
        }
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
