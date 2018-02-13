package tests.testservicesnode.connection;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.progress.BaseProgressUtils;
import org.openide.awt.StatusDisplayer;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.NbPreferences;
import org.openide.util.RequestProcessor;
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
    private OSNodeData nodeData;

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
        try {
            return new OSNode(nodeData);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
            return Node.EMPTY;
        }
    }

    @Override
    protected void addNotify() {
        PropertiesNotifier.addChangeListener(listener = (ChangeEvent e) -> {
            try {
                final String urlPrefix = NbPreferences.forModule(NewRootNode.class).get("url", "openstack");
                final String user = NbPreferences.forModule(NewRootNode.class).get("user", "error");
                final String password = NbPreferences.forModule(NewRootNode.class).get("password", "error");
                final String name = NbPreferences.forModule(NewRootNode.class).get("name", "Default");
                
                BaseProgressUtils.showProgressDialogAndRun(() -> {
                    OSClientV2 os = OSFactory.builderV2()
                            .endpoint(urlPrefix)
                            .credentials(user, password)
                            .tenantName("admin")
                            .authenticate();
                    if (!clients.contains(os)) {
                        clients.add(os);
                    }
                    
                    nodeData = new OSNodeData(os, name,
                            urlPrefix, user, password);
                    refresh(true);
                }, "Connecting Openstack...");
                
                StatusDisplayer.getDefault().setStatusText("new openstack");
            } catch (ConnectionException | AuthenticationException ae) {
                RequestProcessor.getDefault().post(() -> {                    
                    String msg = "Invalid login credentials";
                    JOptionPane.showMessageDialog(null, msg);
                    StatusDisplayer.getDefault().setStatusText(msg);
                });
                
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
