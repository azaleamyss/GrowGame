public enum FieldPos{
    TOP_LEFT(0),
    TOP_CENTER(1),
    TOP_RIGHT(2),
    UNDER_LEFT(3),
    UNDER_CENTER(4),
    UNDER_RIGHT(5);

    private final int id;

    FieldPos(int id){
        this.id = id;
    }

    public int id(){
        return this.id;
    }
}
