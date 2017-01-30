public enum DrinkType{
    WATER,
    TEA,
    SHOYU,
    MILK,
    COLA,
    VEER,
    WINE,
    NONE;
    public static DrinkType parseDrinkType(String name){
        for(DrinkType drink: values()){
            if(drink.name().equals(name)){
                return drink;
            }
        }
        return NONE;
    }
}
