/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import entities.FishierElementBridge;
import java.util.ArrayList;
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
    
    private FuzzyDriver(){}
    
    public static FuzzyDriver getInstance(){
        if(FuzzyDriver.instance == null)
            FuzzyDriver.instance = new FuzzyDriver();
        return FuzzyDriver.instance;
    }

    public List<String> generateResult(List<FishierElementBridge> fishierElements) {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < fishierElements.size(); i++) {
            FishierElementBridge fishierElementBridge = fishierElements.get(i);
            result.add(computeUsage(fishierElementBridge));
        }
        return result;
    }
    
    private String computeUsage(FishierElementBridge fishierElBr){
        return FuzzyDriver.HARD_USAGE;
    }
}
