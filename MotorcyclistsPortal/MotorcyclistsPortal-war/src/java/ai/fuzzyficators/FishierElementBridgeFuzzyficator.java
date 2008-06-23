/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.fuzzyficators;

import ai.fuzzycomputers.FuzzyComputerInterface;
import ai.fuzzycomputers.TrapeziumComputer;
import ai.usagecomputers.SquareUsageComputer;
import ai.usagecomputers.UsageComputerInterface;
import entities.FishierElementBridge;
import entities.Usage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import utils.BeanGetter;
import utils.MPException;
import utils.MPLogger;

/**
 *
 * @author kalosh
 */
public class FishierElementBridgeFuzzyficator extends Fuzzyficator {

    private UsageComputerInterface usageComputer;
    private FuzzyComputerInterface fuzzyComputer;

    public FishierElementBridgeFuzzyficator() {
        this.usageComputer = new SquareUsageComputer();
        this.fuzzyComputer = new TrapeziumComputer();
    }

    public String processElement(Object object) throws Exception {
        if (!(object instanceof FishierElementBridge)) {
            throw new MPException("Object passed to FishierElementBridgheFuzzyficator.processElement is not properly");
        }
        FishierElementBridge fishierElBr = (FishierElementBridge) object;
        String activityPeriod = fishierElBr.getActivityperiod().getDescription();
        Usage fuzzyUsage = null;
        Double usage = 0.0;
        if (activityPeriod.equals("distance")) {
            Integer changeDistance = fishierElBr.getChangemileage();
            Integer bikeMileage = new Integer(fishierElBr.getFishier().getMotorcycle().getMileage().toString());
            Integer partMileage = bikeMileage - changeDistance;
            Integer partAvailabilityMileage = fishierElBr.getPeriodlength();
            if (partMileage > partAvailabilityMileage) {
                fuzzyUsage = BeanGetter.lookupUsageFacade().findHardest();
                return fuzzyUsage.getDescription();
            } else {
                usage = this.usageComputer.computeUsage(partMileage, partAvailabilityMileage);
            }
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date changeDate = sdf.parse(fishierElBr.getChangedate());
            int partAvailabilityYears = fishierElBr.getPeriodlength();

            Calendar usageLimitDate = Calendar.getInstance();
            usageLimitDate.setTime(changeDate);
            usageLimitDate.add(Calendar.YEAR, partAvailabilityYears);

            Calendar changeCalDate = Calendar.getInstance();
            changeCalDate.setTime(changeDate);

            Calendar now = Calendar.getInstance();
            if (usageLimitDate.before(now)) {
                fuzzyUsage = BeanGetter.lookupUsageFacade().findHardest();
                return fuzzyUsage.getDescription();
            } else {
                int partAvailabilityMonths = partAvailabilityYears * 12;
                int monthsDiff = this.getDiffInMonths(changeCalDate, now);
                usage = this.usageComputer.computeUsage(monthsDiff, partAvailabilityMonths);
            }
        }
        List parameters = new ArrayList();
        List<Usage> usages = BeanGetter.lookupUsageFacade().findAll();
        parameters.add(usage);
        parameters.addAll(usages);
        Usage usageResult = ((Usage) this.fuzzyComputer.extractFuzzyValue(parameters));
        return usageResult.getDescription();
    }

    private int getDiffInMonths(Calendar earlierDate, Calendar laterDate) {
        int monthsCount = -1;
        while (earlierDate.before(laterDate)) {
            earlierDate.add(Calendar.MONTH, 1);
            monthsCount++;
        }
        return monthsCount;
    }
}
