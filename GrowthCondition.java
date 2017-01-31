import java.util.ArrayList;
public class GrowthCondition{
    private int epsilon = 10;
    private ArrayList<Carrot> carrotList;

    /*enum実装次第消します*/
    GrowthCondition(){
        carrotList = new ArrayList<Carrot>();
        conditionInit();
    }

    //人参の進化条件(最低限)
    private void conditionInit(){

        /*未実装*/

        //WATER,TEA,SHOYU,MILK,COLA,VEER,WINE
        carrotList.add(new Carrot(CarrotType.WHITE, 0,0,0,3,0,0,0));
    }

    //成長条件を満たす人参の種類を返す
    public CarrotType getSatisfiedCarrotType(Carrot carrot){

        /*未実装*/

        return CarrotType.ORANGE;
    }
}
