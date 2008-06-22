/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ai.fuzzyficators;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dawid
 */
public abstract class Fuzzyficator {
    
    public abstract String processElement(Object object) throws Exception;
    
    public List<String> processCollection(List objects) throws Exception {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < objects.size(); i++) {
            Object object = objects.get(i);
            result.add(this.processElement(object));
        }
        return result;
    }
}
