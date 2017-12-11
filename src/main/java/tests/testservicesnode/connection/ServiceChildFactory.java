package tests.testservicesnode.connection;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeListener;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openstack4j.api.OSClient.OSClientV2;
import org.openstack4j.model.identity.v2.Access;
import org.openstack4j.model.identity.v2.Service;

/**
 *
 * @author jor3
 */
public class ServiceChildFactory extends ChildFactory.Detachable<Access.Service>  {

    private OSClientV2 client;
    private ChangeListener listener;
    private List<Access.Service> servicesList;

    public ServiceChildFactory(OSClientV2 client) {
        this.client = client;
        servicesList = new ArrayList<>();
        
        servicesList.addAll(client.getAccess().getServiceCatalog());        
        
    }

//    @Override
//    protected void addNotify() {
//        RefreshServicesListTrigger.addChangeListener(listener = new ChangeListener() {
//            @Override
//            public void stateChanged(ChangeEvent ce) {
//                RequestProcessor.getDefault().post(new Runnable() {
//                    @Override
//                    public void run() {
//                        BaseProgressUtils.showProgressDialogAndRun(new Runnable() {
//                            @Override
//                            public void run() {
//                                final String service name
//                                
//                            }
//                        }, "Refreshing services... ");
//                    }
//                });
//            }
//        });
//    }
    
    
    @Override
    protected boolean createKeys(List<Access.Service> toPopulate) {
        toPopulate.addAll(servicesList);
        return true;
    }

    @Override
    protected Node createNodeForKey(Access.Service key) {
        ServiceNode node = null; 
        try {
            node = new ServiceNode(key);            
        } catch(IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }

    
}
