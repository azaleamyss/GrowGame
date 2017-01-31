public enum DrinkType{
    WATER,
    TEA,
    SHOYU,
    MILK,
    COLA,
    VEER,
    WINE,
    NONE;

    /*
     * 列挙した飲み物に、そのひらがな名称と値段を定義して下さい
     * java enum で検索すれば出てきます
    private final String japanese;
    private final int price;
    DrinkType(String japanese,int price){
        this.japanese = japanese;
        this.price = price;
    }

    public int price(){
        return this.price;
    }
    */

    //列挙名(String)→ 列挙型に変換
    public static DrinkType parseDrinkType(String name){
        for(DrinkType drink: values()){
            if(drink.name().equals(name)){
                return drink;
            }
        }
        return NONE;
    }
}
