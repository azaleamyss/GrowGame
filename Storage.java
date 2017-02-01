import java.util.ArrayList;
public class Storage{
    private ArrayList<String> storedDrinks;
    private ArrayList<String> storedCarrots;
    private ArrayList<String> storedSeeds;
    private ArrayList<String> storedMoney;

    Storage(ArrayList<String> carrots, ArrayList<String> drinks, ArrayList<String> seeds, ArrayList<String> money){
        storedDrinks = drinks;
        storedCarrots = carrots;
        storedSeeds = seeds;
        storedMoney = money;
    }

    public void setStoredCarrots(ArrayList<String> value){
        storedCarrots = value;
    }
    public void setStoredSeeds(ArrayList<String> value){
        storedSeeds = value;
    }
    public void setStoredDrinks(ArrayList<String> value){
        storedDrinks = value;
    }
    public void setStoredMoney(ArrayList<String> value){
        storedMoney = value;
    }

    public ArrayList<String> getStoredCarrots(){
        return storedCarrots;
    }
    public ArrayList<String> getStoredSeeds(){
        return storedSeeds;
    }
    public ArrayList<String> getStoredDrinks(){
        return storedDrinks;
    }
    public ArrayList<String> getStoredMoney(){
        return storedMoney;
    }

    public ArrayList<ArrayList<String>> getLog(){
        ArrayList<ArrayList<String>> log = new ArrayList<ArrayList<String>>();  
        log.add(storedDrinks);
        log.add(storedSeeds);
        log.add(storedCarrots);
        log.add(storedMoney);
        return log;
    }
}
