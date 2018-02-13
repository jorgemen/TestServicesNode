package tests.testservicesnode.connection;

import java.beans.IntrospectionException;
import javax.swing.Action;
import org.netbeans.api.annotations.common.StaticResource;
import org.openide.actions.NewAction;
import org.openide.actions.OpenLocalExplorerAction;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.util.actions.SystemAction;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author jor3
 */
public class OSNode extends BeanNode<OSNodeData> {

    
    @StaticResource
    private static final String ICON = "tests/testservicesnode/openstack.png";

    private final OSNodeData nodeData;

    public OSNode(OSNodeData bean) throws IntrospectionException {
        this(bean, new InstanceContent());
    }

    public OSNode(OSNodeData bean, InstanceContent content) throws IntrospectionException {
        super(bean, Children.create(new ServiceChildFactory(bean.getClient()), true), new AbstractLookup(content));
        content.add(bean);
        content.add(this);
        this.nodeData = bean;        
        setIconBaseWithExtension(ICON);
        setDisplayName(bean.getNodeName());

    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[]{
            SystemAction.get(NewAction.class),
            SystemAction.get(OpenLocalExplorerAction.class),};
    }

    
    
    @Override
    public String getDisplayName() {
        return nodeData.getNodeName();
    }

    
    
}
