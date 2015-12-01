package domain;

import java.io.ObjectInputStream.GetField;
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
import exception.CantLoginException;


public class MedRecDBClient {
	
	public static String[] getCredentials(){
		String username = new String();
		String password = new String();
		Scanner entradaScanner = new Scanner(System.in);
		System.out.println("***** LOGIN MEDICAL RECORDS DATABASE *****");
		System.out.println("Insert your:");
		System.out.print("Username: ");
		username = entradaScanner.next();
		if (username.equals("0"))
			return null;
		System.out.print("Password: ");
		password = entradaScanner.next();
		if (password.equals("0"))
			return null;
		String[] userAndPass = new String[2];
		userAndPass[0] = username;
		userAndPass[1] = password;
		return userAndPass;
	}
	
	public static String[] mudaPassword(){
		String[] inputs = new String[3];
		Scanner entradaScanner = new Scanner(System.in);
		System.out.print("Actual Password: ");
		inputs[0] = entradaScanner.next();
		System.out.print("New Password: ");
		inputs[1] = entradaScanner.next();
		System.out.print("Confirm your new Password: ");
		inputs[2] = entradaScanner.next();
		return inputs;
		
	}
	
	public static void inicioNovoMenu(){
		System.out.println("*****************");
		System.out.println("0. Voltar");
	}
	
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
		
		/*try{
			remote.createUser("greg", "9363", "ZZZZZ");
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}*/
		
		int opcao = 999;
		String[] nameAndPass;
		String username;
		String password;
		String patientName;
		Scanner entradaScanner = new Scanner(System.in);
		String type;
		String newPassword;
		String[] inputsNovaPassword;
		
		while (true){
			try{
				nameAndPass = getCredentials();
				username = nameAndPass[0];
				password = nameAndPass[1];			
				remote.loginInSystem(username, password);
				type = remote.getUserType(username);
				do{
					try{
						if (type.equals("doctor")){
							menuDoctor();
							patientName = new String();
							opcao = entradaScanner.nextInt();
							if (opcao == 0)
								break;
							inicioNovoMenu();
							
							switch(opcao){
							case 1:
								nameAndPass = getCredentials();
								if (nameAndPass == null)
									break;
								username = nameAndPass[0];
								password = nameAndPass[1];
								System.out.print("Patient Username: ");
								patientName = entradaScanner.next();
								if(patientName.equals("0"))
									break;
								System.out.println();
								String records = remote.doctorViewRecord(username, password, patientName, false);
								System.out.println(records);
								break;
								
							case 2:
								inputsNovaPassword = mudaPassword();
								remote.changePassword(username, inputsNovaPassword[0], inputsNovaPassword[1], inputsNovaPassword[2]);
								System.out.println("Password changed with success!");
							}
						}
					}
					catch (Exception e){
						System.out.println(e.getMessage());
						if (e.toString().contains("CantLogin"))
							opcao=0;
					}
				} while(opcao != 0);
			} 
			catch(CantLoginException e){
				System.out.println(e.getMessage());
			}
		}
	}
}