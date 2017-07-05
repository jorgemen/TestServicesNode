package tests.testservicesnode.connection;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import javax.swing.JOptionPane;
import org.openide.awt.StatusDisplayer;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.NbPreferences;
import org.openide.util.RequestProcessor;
import org.openstack4j.api.OSClient;
import org.openstack4j.api.exceptions.AuthenticationException;
import org.openstack4j.openstack.OSFactory;
import tests.testservicesnode.NewRootNode;

/**
 *
 * @author jor3
 */
public class OSChildFactory extends ChildFactory.Detachable<OSClient> implements PreferenceChangeListener {

    private final List<OSClient> clients;
    private String name;

    public OSChildFactory() {
        this.clients = new ArrayList<>();
    }

    @Override
    protected boolean createKeys(List<OSClient> toPopulate) {
        toPopulate.addAll(clients);
        return true;
    }

    @Override
    protected Node createNodeForKey(OSClient osClient) {        
        return new OSNode(osClient, name);
    }

    @Override
    public void preferenceChange(PreferenceChangeEvent pce) {
        if (pce.getKey().equals("name")) {
            name = pce.getNewValue();
            try {
                String urlPrefix = NbPreferences.forModule(NewRootNode.class).get("url", "openstack");
                String user = NbPreferences.forModule(NewRootNode.class).get("user", "error");
                String password = NbPreferences.forModule(NewRootNode.class).get("password", "error");
                OSClient client = OSFactory.builderV2().endpoint(urlPrefix).
                        credentials(user, password).
                        tenantName("admin").
                        authenticate();
                clients.add(client);
                refresh(true);
                StatusDisplayer.getDefault().setStatusText("new openstack");
            } catch (AuthenticationException ae) {
                RequestProcessor.getDefault().post(new Runnable() {
                    @Override
                    public void run() {
                        String msg = "Invalid login credentials";
                        JOptionPane.showMessageDialog(null, msg);
                        StatusDisplayer.getDefault().setStatusText(msg);
                    }
                });

            }
        }
    }

    @Override
    protected void addNotify() {
        NbPreferences.forModule(NewRootNode.class).addPreferenceChangeListener(this);
    }

    @Override
    protected void removeNotify() {
        NbPreferences.forModule(NewRootNode.class).removePreferenceChangeListener(this);
    }

}
