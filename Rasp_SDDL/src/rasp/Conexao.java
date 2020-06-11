package rasp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;
import java.util.UUID;

import lac.cnclib.net.NodeConnection;
import lac.cnclib.net.NodeConnectionListener;
import lac.cnclib.net.groups.Group;
import lac.cnclib.net.groups.GroupCommunicationManager;
import lac.cnclib.net.groups.GroupMembershipListener;
import lac.cnclib.net.mrudp.MrUdpNodeConnection;
import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.message.Message;


public class Conexao implements NodeConnectionListener, GroupMembershipListener {

    private static int gatewayPort  = 5500;
    private MrUdpNodeConnection connection;
    private UUID myUUID;
    private String nome;
    private boolean isConnect;
    private GroupCommunicationManager groupManager;
    private Group aGroup;
    private static Controle controle;


    public Conexao(String IP, String nome){
        this.nome = nome;
        isConnect = false;
        

        InetSocketAddress address = new InetSocketAddress(IP,gatewayPort);
        
        try {
			controle = new Controle();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        try{
            connection = new MrUdpNodeConnection();
            connection.addNodeConnectionListener(this);
            connection.connect(address);
            
            aGroup = new Group(250,1);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void connected(NodeConnection nodeConnection) {
        ApplicationMessage message = new ApplicationMessage();
    //    message.setContentObject("Conectando "+ this.nome);
        myUUID = connection.getUuid();
        groupManager = new GroupCommunicationManager(nodeConnection);

        groupManager.addMembershipListener(this);
        if(connection.getUuid() != null)
            this.isConnect = true;
        
        System.out.println("CONECTADO!");
        

        try{
            connection.sendMessage(message);
            groupManager.joinGroup(aGroup);
    		
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean isConnected(){
        return isConnect;
    }

    @Override
    public void reconnected(NodeConnection nodeConnection, SocketAddress socketAddress, boolean b, boolean b1) {
        this.isConnect = true;
    }

    @Override
    public void disconnected(NodeConnection nodeConnection) {
        this.isConnect = false;
    }

    @Override
    public void newMessageReceived(NodeConnection nodeConnection, Message message) {
    	
    	String[] comandos = message.getContentObject().toString().split(" ");
    	
		if("ctrl".equals(comandos[0])) {
			System.out.println("Comando: " + message.getContentObject().toString());
			try {
				controle.direcao(comandos[1], comandos[2]);
				controle.angulo(Integer.parseInt(comandos[3]));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
    }

    @Override
    public void unsentMessages(NodeConnection nodeConnection, List<Message> list) {


    }

    @Override
    public void internalException(NodeConnection nodeConnection, Exception e) {

    }

    public UUID getMyUUID() {
        return myUUID;
    }

    @Override
    public void enteringGroups(List<Group> list) {

        ApplicationMessage appMsg = new ApplicationMessage();
        appMsg.setContentObject(nome + " Entrou no grupo");
        System.out.println("CONECTADO AO GRUPO (250,1)!");
        try {
            groupManager.sendGroupcastMessage(appMsg, aGroup);
    		
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void leavingGroups(List<Group> list) {
        ApplicationMessage appMsg = new ApplicationMessage();
        appMsg.setContentObject(nome + " Deixou o grupo");
        try {
            groupManager.sendGroupcastMessage(appMsg, aGroup);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public void sendUUID() {
    	ApplicationMessage appMsg = new ApplicationMessage();
        appMsg.setContentObject("001x " + myUUID);
        try {
            groupManager.sendGroupcastMessage(appMsg, aGroup);
    		
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

