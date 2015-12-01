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
		System.out.println("0. Back");
	}
	
	public static void menu(){
		System.out.println("***** MEDICAL RECORDS DATABASE *****");
        System.out.println("0. Exit");
        System.out.println("1. Check Patient Record");
        System.out.println("2. Create Appointment");
        System.out.println("3. Create User");
        System.out.println("4. Create Patient");
        System.out.println("5. Create Doctor");
        System.out.println("6. Create Staff");
        System.out.println("7. Change Password");
        System.out.print("Option: ");
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
		String[] inputsNovaPassword;
		String records = null;
		String description;
		String newUsername;
		
		while (true){
			try{
				nameAndPass = getCredentials();
				username = nameAndPass[0];
				password = nameAndPass[1];			
				remote.loginInSystem(username, password);
				do{
					try{
						menu();
						patientName = new String();
						opcao = entradaScanner.nextInt();
						if (opcao == 0)
							break;
						inicioNovoMenu();
						
						switch(opcao){
						case 1: // CHECK PATIENT RECORD
							nameAndPass = getCredentials();
							if (nameAndPass == null)
								break;
							username = nameAndPass[0];
							password = nameAndPass[1];
							type = remote.getUserType(username);
							if(type.equals("doctor")){
								System.out.print("Patient Username: ");
								patientName = entradaScanner.next();
								if(patientName.equals("0"))
									break;
								System.out.println();
								records = remote.doctorViewRecord(username, password, patientName, false);
							}else{
								records = remote.viewRecord(username, password);
							}
							
							System.out.println(records);
							break;
						
						case 2: //CREATE APPOINTMENT
							nameAndPass = getCredentials();
							if (nameAndPass == null)
								break;
							username = nameAndPass[0];
							password = nameAndPass[1];
							System.out.print("Patient Username: ");
							patientName = entradaScanner.next();
							if(patientName.equals("0"))
								break;
							System.out.print("Description: ");
							description = entradaScanner.next();
							if(description.equals("0"))
								break;						
							remote.createAppointment(username, password, patientName, description);
							break;
							
						case 3: //CREATE USER
							System.out.print("New Username to be created: ");
							newUsername = entradaScanner.next();
							if (newUsername.equals("0"))
								break;
							String newPass = remote.createUser(username, password, newUsername);
							System.out.println(newUsername + "'s Password: " + newPass);
							break;
							
						case 4: //CREATE PATIENT
							System.out.print("Username of the Patient: ");
							newUsername = entradaScanner.next();
							if (newUsername.equals("0"))
								break;
							System.out.print("First Name: ");
							String first = entradaScanner.next();
							if (first.equals("0"))
								break;
							System.out.print("Last Name: ");
							String last = entradaScanner.next();
							if (last.equals("0"))
								break;
							String address = entradaScanner.next();
							if (address.equals("0"))
								break;
							remote.createPatient(username, password, false, newUsername, first, last, address);
							break;
							
						case 5: //CREATE DOCTOR
							nameAndPass = getCredentials();
							if (nameAndPass == null)
								break;
							username = nameAndPass[0];
							password = nameAndPass[1];
							System.out.print("Username of the Doctor: ");
							newUsername = entradaScanner.next();
							if (newUsername.equals("0"))
								break;
							System.out.print("Specialty: ");
							String spec = entradaScanner.next();
							if (spec.equals("0"))
								break;
							remote.createDoctor(username, password, newUsername, spec);
							break;
							
						case 6: //CREATE STAFF
							nameAndPass = getCredentials();
							if (nameAndPass == null)
								break;
							username = nameAndPass[0];
							password = nameAndPass[1];
							System.out.print("Username of the Staff Member: ");
							newUsername = entradaScanner.next();
							if (newUsername.equals("0"))
								break;
							System.out.print("Wage: ");
							int wage = entradaScanner.nextInt();
							if (wage == 0)
								break;
							remote.createStaff(username, password, newUsername, wage);
							break;
							
						case 7: //CHANGE PASSWORD
							inputsNovaPassword = mudaPassword();
							remote.changePassword(username, inputsNovaPassword[0], inputsNovaPassword[1], inputsNovaPassword[2]);
							System.out.println("Password changed with success!");
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