package domain;

import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Scanner;

import domain.MedRecDBInterface;


public class MedRecDBClient {
	
	public static void menuDoctor(){
		System.out.println("***** MEDICAL RECORDS DATABASE *****");
        System.out.println("0. Sair");
        System.out.println("1. Ver Registo de Paciente");
        System.out.println("2. Mudar password");
        System.out.print("Opcao: ");
    }
	
	public static void main(String arg[]) throws Exception{ 

		MedRecDBInterface remote = null;
		try {
			Registry registry = LocateRegistry.getRegistry("localhost", 8095);
			remote = (MedRecDBInterface)registry.lookup("MedicalRecordsDatabase");
			System.out.println("Cliente comunica com server");
		} catch (ConnectException e) {
			throw new ConnectException("Cliente não encontra server");
		}
		
		remote.createUser("greg", "9363", "ZZZZZ");
		
		/*while (true){		
			int opcao;
			String username = new String();
			String password = new String();
			Scanner entradaScanner = new Scanner(System.in);
			System.out.println("***** LOGIN MEDICAL RECORDS DATABASE *****");
			System.out.print("Username: ");
			username = entradaScanner.next();
			System.out.print("Password: ");
			password = entradaScanner.next();
			
			loginInSystemClient(remote, username, password);
			do{
				menuDoctor();
			    String patientName = new String();
			    opcao = entradaScanner.nextInt();
			    System.out.println("0. Voltar");
	
			    switch(opcao){
			    case 1:
			    	System.out.print("Paciente: ");
			        patientName = entradaScanner.next();
			        if(patientName.equals("0")){
			        	break;
			        }
			        System.out.println();
			        remote.doctorViewRecord(username, password, patientName, false);
			        break;
			    }
			} while(opcao != 0);
		} */
	}
}
