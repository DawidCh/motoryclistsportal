/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.fuzzyficators;

import ai.FuzzyDriver;
import ai.fuzzyficators.usagecomputers.SquareUsageComputer;
import ai.fuzzyficators.usagecomputers.UsageComputerInterface;
import entities.FishierElementBridge;
import fuzzyelements.Fuzzyficable;
import fuzzyelements.TrapeziumMembershipFunction;
import entities.Usage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import utils.BeanGetter;
import utils.MPException;

/**
 * Class wich computes Usage of motorbike parts given in FishierElementBridge.
 * @author Dawid Chojnacki
 */
public class FishierElementBridgeFuzzyficator extends AbstractFuzzyficator {

    /**
     * Number of months in year.
     */
    private static final int MONTH_COUNT = 12;
    /**
     * Object for computing percentage usage of element.
     */
    private UsageComputerInterface usageComputer;

    /**
     * Constructor.
     */
    public FishierElementBridgeFuzzyficator() {
        super();
        this.usageComputer = new SquareUsageComputer();
    }

    /**
     * Method using for computing fuzzy value for given FishierElementBridge.
     * @param object given for computing fuzzy value
     * @return fuzzy value represented by String
     * @throws java.lang.Exception
     */
    public Usage processElement(Fuzzyficable object) throws Exception {
        Logger.getLogger("errorLogger").trace("Entering to: processElement");
        if (!(object instanceof FishierElementBridge)) {
            throw new MPException("Object passed to"
                    + "FishierElementBridgheFuzzyficator.processElement"
                    + "is not properly");
        }
        FishierElementBridge fishierElBr = (FishierElementBridge) object;
        String activityPeriod = fishierElBr.getActivityperiod().
                getDescription();
        Usage fuzzyUsageResult = null;
        Double doubleUsage = 0.0;
        if (activityPeriod.equals("distance")) {
            Integer changeDistance = fishierElBr.getChangemileage();
            Integer bikeMileage = new Integer(fishierElBr.getFishier().
                    getMotorcycle().getMileage().toString());
            Integer partMileage = bikeMileage - changeDistance;
            Integer partAvailabilityMileage = fishierElBr.getPeriodlength();
            if (partMileage > partAvailabilityMileage) {
                fuzzyUsageResult = BeanGetter.lookupUsageFacade().findHardest();
            } else {
                doubleUsage = this.usageComputer.
                        computePercentageValue(partMileage,
                        partAvailabilityMileage);
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
                fuzzyUsageResult = BeanGetter.lookupUsageFacade().findHardest();
            } else {
                int partAvailabilityMonths = partAvailabilityYears
                        * FishierElementBridgeFuzzyficator.MONTH_COUNT;
                int monthsDiff = this.getDiffInMonths(changeCalDate, now);
                doubleUsage = this.usageComputer.
                        computePercentageValue(monthsDiff,
                        partAvailabilityMonths);
            }
        }
        if (fuzzyUsageResult == null) {
            List < TrapeziumMembershipFunction  > usages =
                    new ArrayList < TrapeziumMembershipFunction  >(
                    BeanGetter.lookupUsageFacade().findAll());
            fuzzyUsageResult = (Usage) FuzzyDriver.
                    getTrapeziumFuzzySetForValue(usages, doubleUsage);
        }
        Logger.getLogger("errorLogger").trace("Exiting from: processElement");
        return fuzzyUsageResult;
    }

    /**
     * Method used for compute difference in months between two dates.
     * @param earlierDate earlier date
     * @param laterDate later date
     * @return number of months between given dates or -1 when error
     */
    private int getDiffInMonths(final Calendar earlierDate,
            final Calendar laterDate) {
        Logger.getLogger("errorLogger").trace("Entering to: getDiffInMonths");
        int monthsCount = -1;
        while (earlierDate.before(laterDate)) {
            earlierDate.add(Calendar.MONTH, 1);
            monthsCount++;
        }
        Logger.getLogger("errorLogger").trace("Exiting from: getDiffInMonths");
        return monthsCount;
    }
}
