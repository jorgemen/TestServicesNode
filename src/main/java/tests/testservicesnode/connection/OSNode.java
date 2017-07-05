package tests.testservicesnode.connection;

import javax.swing.Action;
import org.openide.actions.NewAction;
import org.openide.actions.OpenLocalExplorerAction;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openstack4j.api.OSClient;

/**
 *
 * @author jor3
 */
public class OSNode extends AbstractNode {

    private OSClient client;
    private String name;

    public OSNode(OSClient client, String name) {
        this(client, name, new InstanceContent());
    }

    public OSNode(OSClient client, String name, InstanceContent content) {
        super(Children.create(
                new ServiceChildFactory(client), true), new AbstractLookup(content));
        this.client = client;
        this.name = name;
        content.add(this);
        content.add(client);
        setDisplayName(name);

    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[]{
            SystemAction.get(NewAction.class),
            SystemAction.get(OpenLocalExplorerAction.class),};
    }

    @Override
    public boolean canRename() {
        return true;
    }

    
}
