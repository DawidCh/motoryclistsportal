/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import entities.FishierElementBridge;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author kalosh
 */
public class FuzzyDriver {

    public static String LOW_USAGE = new String("low");
    public static String MEDIUM_USAGE = new String("medium");
    public static String HARD_USAGE = new String("hard");
    private static FuzzyDriver instance;

    private FuzzyDriver() {
    }

    public static FuzzyDriver getInstance() {
        if (FuzzyDriver.instance == null) {
            FuzzyDriver.instance = new FuzzyDriver();
        }
        return FuzzyDriver.instance;
    }

    public List<String> generateResult(List<FishierElementBridge> fishierElements) throws Exception {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < fishierElements.size(); i++) {
            FishierElementBridge fishierElementBridge = fishierElements.get(i);
            result.add(computeUsage(fishierElementBridge));
        }
        return result;
    }

    private String computeUsage(FishierElementBridge fishierElBr) throws ParseException {
        String activityPeriod = fishierElBr.getActivityperiod().getDescription();

        if (activityPeriod.equals("distance")) {
            Integer changeDistance = fishierElBr.getChangemileage();
            Integer bikeMileage = new Integer(fishierElBr.getFishier().getMotorcycle().getMileage().toString());
            Integer partMileage = bikeMileage - changeDistance;
            return this.fuzzyValue(partMileage, fishierElBr.getPeriodlength());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date changeDate = sdf.parse(fishierElBr.getChangedate());
            Date now = Calendar.getInstance().getTime();
            Long partAge = now.getTime() - changeDate.getTime();
            return this.fuzzyValue(partAge, fishierElBr.getPeriodlength());
        }
    }

    private String fuzzyValue(Integer partMileage, int periodlength) {
        return FuzzyDriver.HARD_USAGE;
    }

    private String fuzzyValue(Long partAge, int periodlength) {
        return FuzzyDriver.HARD_USAGE;
    }
}
