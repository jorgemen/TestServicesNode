
import java.util.List;
import org.openstack4j.api.OSClient;
import org.openstack4j.api.compute.ComputeImageService;
import org.openstack4j.api.exceptions.AuthenticationException;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.identity.v2.Service;
import org.openstack4j.model.image.v2.Image;
import org.openstack4j.openstack.OSFactory;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jor
 */
public class NewtonAuthTest {

    public static void main(String[] args) {
//        clientv3();
        clientv2();

    }

    private static void clientv3() throws AuthenticationException {
        OSClient.OSClientV3 os = OSFactory.builderV3()
                .endpoint("http://16.17.101.131:5000/v3/")
                .credentials("admin", "admin")
                .scopeToProject(Identifier.byName("vDSP"), Identifier.byName("Default"))
                .authenticate();
    }

    private static void clientv2() throws AuthenticationException {
        OSClient.OSClientV2 os = OSFactory.builderV2()
                .endpoint("http://16.17.101.131:5000/v2.0")
                .credentials("admin", "admin")
                .tenantName("admin")
                .authenticate();
        ComputeImageService images = os.compute().images();
        List<? extends org.openstack4j.model.compute.Image> list = images.list();
        for (org.openstack4j.model.compute.Image image : list) {
            System.out.println("Image name: " + image.getName());
            System.out.println("Image ID: " + image.getId());
        }
        
        List<? extends Service> list1 = os.identity().services().list();
        for (Service service : list1) {
            System.out.println("Service: " + service.getName());
        }
    }
}
