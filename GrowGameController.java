import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class GrowGameController{ 
    //他コントローラで利用しないものはこのフィールドから消します
    protected static ArrayList<Field> fieldArea;
    protected static Shop shop;
    protected static Storage storage;
    protected static GrowthCondition growthCondition;
    protected static LogController logController;
    protected static ArrayList<String> passDate;
    protected static PictureBook picBook;
    protected static SimpleDateFormat sdf;

    static{
        logController = new LogController();
        passDate = logController.getFieldLog("date");
        sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
    }

    protected ArrayList<String> getNowDate(){
        String nowDateText = sdf.format(new Date());
        String[] nowDate = nowDateText.split("-"); 
        return (ArrayList<String>)Arrays.asList(nowDate);
    }

    protected void exit(){
        //ログ出力
        ArrayList<ArrayList<String>> newlog;

        newlog = storage.getLog();
        logController.setStorageLog(newlog);
        logController.updateStorageLog();

        newlog = null;
        for(Field f: fieldArea){
            newlog.add(f.getLog());
        }
        newlog.add(getNowDate());
        logController.setFieldLog(newlog);
        logController.updateFieldLog();

        System.exit(0);
    }

    protected static int getDateDiff(ArrayList<String> log){
        String dateText = null;
        long ms_diff = 0;
        try{
            //過去
            dateText = log.get(0)+"-"+log.get(1)+"-"+log.get(2)+"-"+log.get(3)+"-"+log.get(4);
            Date past = sdf.parse(dateText);
            //現在
            dateText = sdf.format(new Date());
            Date now = sdf.parse(dateText);

            long past_ms = past.getTime();
            long now_ms = now.getTime();
            ms_diff = now_ms-past_ms;
        }catch(ParseException e){
            e.printStackTrace();
        }

        long m = 1000*60;

        return (int)(ms_diff/m);
    }
}
