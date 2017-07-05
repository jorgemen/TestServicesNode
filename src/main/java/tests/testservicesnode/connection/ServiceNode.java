package tests.testservicesnode.connection;

import java.beans.IntrospectionException;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openstack4j.model.identity.v2.Service;

/**
 *
 * @author jor3
 */
public class ServiceNode extends BeanNode<Service> {

    private final Service service;

    public ServiceNode(Service bean) throws IntrospectionException {
        this(bean, new InstanceContent());
    }
    
    public ServiceNode(Service bean, InstanceContent ic) throws IntrospectionException {
        super(bean, Children.LEAF, new AbstractLookup(ic));
        ic.add(bean);
        ic.add(this);
        this.service = bean;
        setDisplayName(bean.getName());
    }

}
