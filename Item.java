public enum Item{
    CARROT("こ"),
    DRINK("どり"),
    SEED("こ"),
    MONEY("きゃろ");

    private final String unit;

    Item(String unit){
        this.unit = unit;
    }

    public String unit(){
        return this.unit;
    }
}

