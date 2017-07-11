package tests.testservicesnode;


import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.Action;
import javax.swing.JButton;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.api.core.ide.ServicesTabNodeRegistration;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.actions.NewAction;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.NbBundle;
import org.openide.util.NbPreferences;
import org.openide.util.actions.SystemAction;
import org.openide.util.datatransfer.NewType;
import tests.testservicesnode.connection.OSChildFactory;
import tests.testservicesnode.connection.PropertiesNotifier;

/**
 *
 * @author jor
 */
@ServicesTabNodeRegistration(displayName = "OpenStacks",
        name = "OpenStacks", iconResource = "tests/testservicesnode/hpe.png")
@NbBundle.Messages({"LBL_Connections=OpenStack"})
public class NewRootNode extends AbstractNode {

    private String urlPrefix;
    private String name;
    private String login;
    private String password;
    
    private static final NewRootNode DEFAULT = new NewRootNode();
            
    @StaticResource
    private static final String ICON = "tests/testservicesnode/hpe.png";
    
    public static NewRootNode getDefault() {
        return DEFAULT;
    }

    public NewRootNode() {
        super(Children.create(new OSChildFactory(), true));
        setDisplayName("OpenStacks");
        setIconBaseWithExtension(ICON);
    }
    
    @Override
    public Action getPreferredAction() {
        return SystemAction.get(NewAction.class);
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[]{SystemAction.get(NewAction.class)};
    }

    @NbBundle.Messages({
        "LBL_Title=OpenStack...",
        "LBL_Text=Enter Name:"})
    @Override
    public NewType[] getNewTypes() {
       return new NewType[]{
            new NewType() {
                @Override
                public String getName() {
                    return Bundle.LBL_Title();
                }
                @Override
                public void create() throws IOException {
                    final LoginForm form = new LoginForm();
                    NotifyDescriptor.Confirmation nd = new NotifyDescriptor.Confirmation(
                            form,
                            Bundle.LBL_Title());
                    JButton ok = new JButton();
                    ok.setText("OK");
                    JButton cancel = new JButton();
                    cancel.setText("Cancel");
                    nd.setOptions(new Object[]{ok, cancel});
                    ok.addActionListener((ActionEvent arg0) -> {
                        urlPrefix = form.getUrlPrefix();
                        name = form.getName();
                        login = form.getUserName();
                        password = form.getUserPassword();
                        NbPreferences.forModule(NewRootNode.class).put("url", urlPrefix);
                        NbPreferences.forModule(NewRootNode.class).put("name", name);
                        NbPreferences.forModule(NewRootNode.class).put("user", login);
                        NbPreferences.forModule(NewRootNode.class).put("password", password);
                        PropertiesNotifier.changed();
                    });
                    DialogDisplayer.getDefault().notify(nd);
                }
            }
        };
    }
    
}
