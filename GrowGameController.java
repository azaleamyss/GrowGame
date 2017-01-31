import java.util.ArrayList;
public class GrowGameController{ 
    protected static ArrayList<Field> fieldArea;
    protected static Shop shop;
    protected static Storage storage;
    protected static GrowthCondition growthCondition;
    protected static LogController logController;
    protected static ArrayList<String> date;

    static{
        logController = new LogController();
        date = logController.getFieldLog("date");
    }

    protected String getDispDate(){
        String dispdate = date.get(1)+"月"+date.get(2)+"日";
        System.out.println(dispdate);
        return dispdate;
    }

    public void exit(){
        //ログ出力
        ArrayList<ArrayList<String>> newlog;

        newlog = storage.getLog();
        logController.setStorageLog(newlog);
        logController.updateStorageLog();

        newlog = null;
        for(Field f: fieldArea){
            newlog.add(f.getLog());
        }
        logController.setFieldLog(newlog);
        logController.updateFieldLog();

        System.exit(0);
    }
}