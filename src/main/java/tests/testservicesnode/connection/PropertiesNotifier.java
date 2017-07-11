package tests.testservicesnode.connection;

import javax.swing.event.ChangeListener;
import org.openide.util.ChangeSupport;

/**
 *
 * @author jor3
 */
public class PropertiesNotifier {
    private static final ChangeSupport CS = new ChangeSupport(PropertiesNotifier.class);
    
    public static void addChangeListener(ChangeListener listener) {
        CS.addChangeListener(listener);
    }
    
    public static void removeChangeListener(ChangeListener listener) {
        CS.removeChangeListener(listener);
    }
    
    public static void changed()  {
        CS.fireChange();
    }
    

}
