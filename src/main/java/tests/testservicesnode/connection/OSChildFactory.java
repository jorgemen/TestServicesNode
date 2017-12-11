package tests.testservicesnode.connection;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.awt.StatusDisplayer;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.NbPreferences;
import org.openide.util.RequestProcessor;
import org.openstack4j.api.OSClient;
import org.openstack4j.api.OSClient.OSClientV2;
import org.openstack4j.api.exceptions.AuthenticationException;
import org.openstack4j.api.exceptions.ConnectionException;
import org.openstack4j.openstack.OSFactory;
import tests.testservicesnode.NewRootNode;

/**
 *
 * @author jor3
 */
public class OSChildFactory extends ChildFactory.Detachable<OSClientV2> {

    private final List<OSClientV2> clients;
    private String name;
    private ChangeListener listener;

    public OSChildFactory() {
        this.clients = new ArrayList<>();
    }

    @Override
    protected boolean createKeys(List<OSClientV2> toPopulate) {
        toPopulate.addAll(clients);
        return true;
    }

    @Override
    protected Node createNodeForKey(OSClientV2 osClient) {
        return new OSNode(osClient, name);
    }

    @Override
    protected void addNotify() {
        PropertiesNotifier.addChangeListener(listener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    String urlPrefix = NbPreferences.forModule(NewRootNode.class).get("url", "openstack");
                    String user = NbPreferences.forModule(NewRootNode.class).get("user", "error");
                    String password = NbPreferences.forModule(NewRootNode.class).get("password", "error");
                    OSClientV2 os = OSFactory.builderV2()
                            .endpoint(urlPrefix)
                            .credentials(user, password)
                            .tenantName("admin")
                            .authenticate();
                    clients.add(os);
                    name = NbPreferences.forModule(NewRootNode.class).get("name", "Default");
                    refresh(true);
                    StatusDisplayer.getDefault().setStatusText("new openstack");
                } catch (ConnectionException | AuthenticationException ae) {
                    RequestProcessor.getDefault().post(new Runnable() {
                        @Override
                        public void run() {
                            ae.printStackTrace();
                            String msg = "Invalid login credentials";
                            JOptionPane.showMessageDialog(null, msg);
                            StatusDisplayer.getDefault().setStatusText(msg);
                        }
                    });

                }
            }
        });
    }

    @Override
    protected void removeNotify() {
        if (listener != null) {
            PropertiesNotifier.removeChangeListener(listener);
            listener = null;
        }
    }

}
