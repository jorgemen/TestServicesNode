package tests.testservicesnode.connection;

import javax.swing.event.ChangeListener;
import org.openide.util.ChangeSupport;

/**
 *
 * @author jor3
 */
public class RefreshServicesListTrigger {
    private static final ChangeSupport cs = new ChangeSupport(RefreshServicesListTrigger.class);
    
    public static void addChangeListener(ChangeListener listener) {
        cs.addChangeListener(listener);
    }
    
    public static void removeChangeListener(ChangeListener listener) {
        cs.removeChangeListener(listener);
    }
    
    public static void trigger()  {
        cs.fireChange();
    }
    

}
