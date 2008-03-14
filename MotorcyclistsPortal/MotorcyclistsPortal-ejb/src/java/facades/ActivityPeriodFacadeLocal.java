/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.ActivityPeriod;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dawid
 */
@Local
public interface ActivityPeriodFacadeLocal {

    void create(ActivityPeriod activityPeriod);

    void edit(ActivityPeriod activityPeriod);

    void remove(ActivityPeriod activityPeriod);

    ActivityPeriod find(Object id);

    List<ActivityPeriod> findAll();

}
