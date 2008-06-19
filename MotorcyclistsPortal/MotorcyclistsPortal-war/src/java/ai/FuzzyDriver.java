/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import entities.FishierElementBridge;
import entities.Usage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import utils.BeanGetter;

/**
 *
 * @author kalosh
 */
public class FuzzyDriver {

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

            return this.fuzzyValue(changeDate, fishierElBr.getPeriodlength());
        }
    }

    private String fuzzyValue(Integer partMileage, int periodlength) {
        Usage usage = null;
        if (partMileage.intValue() > periodlength) {
            usage = BeanGetter.lookupUsageFacade().findHardest();
        }
        else
            usage = BeanGetter.lookupUsageFacade().findLowest();
        return usage.getDescription();
    }

    private String fuzzyValue(Date changeDate, int periodlength) {
        Calendar usageLimitDate = Calendar.getInstance();
        usageLimitDate.setTime(changeDate);
        usageLimitDate.add(Calendar.YEAR, periodlength);
        Calendar changeCalDate = Calendar.getInstance();
        changeCalDate.setTime(changeDate);
        Calendar now = Calendar.getInstance();
        
        Usage usage = null;
        if (usageLimitDate.before(now)) {
            usage = BeanGetter.lookupUsageFacade().findHardest();
        }
        else
            usage = BeanGetter.lookupUsageFacade().findLowest();
        return usage.getDescription();
    }
}
