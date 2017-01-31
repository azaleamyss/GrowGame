public enum CarrotType{
	ORANGE,
    FAT,
    ROUND,
    WHITE,
    BIG,
    GOLD,
    GREEN;

    /*
     * 列挙したものにそれぞれ日本語の名称と、売値、成長条件を定義してください
     * (「java enum」で調べれば出てきます)
    private final String japanese;//日本語名
    private final int price;//売値
    private final int[] growthCondition;//成長条件

    //人参の成長条件を定義
    private void defineCondition(CarrotType type){
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
    */

    public static CarrotType get(int idx){
        return CarrotType.values()[idx]; 
    }
}
