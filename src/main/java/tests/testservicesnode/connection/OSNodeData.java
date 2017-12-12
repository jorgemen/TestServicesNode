/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests.testservicesnode.connection;

import org.openstack4j.api.OSClient;

/**
 *
 * @author jor
 */
public class OSNodeData {

    private OSClient.OSClientV2 client;

    private String nodeName;
    private String nodeUrl;
    private String nodeUser;
    private String nodePassword;

    public OSNodeData(OSClient.OSClientV2 client, String nodeName, String nodeUrl, String nodeUser, String nodePassword) {
        this.client = client;
        this.nodeName = nodeName;
        this.nodeUrl = nodeUrl;
        this.nodeUser = nodeUser;
        this.nodePassword = nodePassword;
    }
    
    

    
    
    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeUrl() {
        return nodeUrl;
    }

    public void setNodeUrl(String nodeUrl) {
        this.nodeUrl = nodeUrl;
    }

    public String getNodeUser() {
        return nodeUser;
    }

    public void setNodeUser(String nodeUser) {
        this.nodeUser = nodeUser;
    }

    public String getNodePassword() {
        return nodePassword;
    }

    public void setNodePassword(String nodePassword) {
        this.nodePassword = nodePassword;
    }

    public OSClient.OSClientV2 getClient() {
        return client;
    }

    public void setClient(OSClient.OSClientV2 client) {
        this.client = client;
    }
    
    
}
