package rasp;

import java.util.Scanner;



public class Operacao {
	

	public static void main(String[] args) throws InterruptedException {
		Conexao conexao;
		
		Scanner ler = new Scanner(System.in);
//		System.out.println("Digite o IP do servidor SDDL:");
//		String IP = ler.nextLine();
		String IP = "192.168.0.54";
		
		do {
		conexao = new Conexao(IP,"VTNT - RaspCAM");
		Thread.sleep(400);
		}while(conexao.getMyUUID() == null);
		
		System.out.println("UUID: " + conexao.getMyUUID().toString());
//		conexao.sendUUID();
		
		
	
		ler.close();
	}

}
