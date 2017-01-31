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

    public static DrinkType get(int idx){
        return DrinkType.values()[idx];
    }
}
