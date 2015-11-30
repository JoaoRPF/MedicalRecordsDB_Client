package domain;

import java.io.IOException;
import java.rmi.*;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import exception.CantLoginException;
import exception.DoctorViewRecordException;
import exception.NotEnoughPermissionException;
import exception.PatientDoesntExistException;
import exception.SpecialtyAlreadyExistsException;
import exception.UsernameAlreadyExistsException;
import exception.UsernameDoesntExistException;
import exception.invalidSpecialtyException;
import exception.missingDoctorArgumentsException;
import exception.missingPatientArgumentsException;
import exception.missingResourceIDArgumentsException;
import exception.missingResourceSpecArgumentsException;

public interface MedRecDBInterface extends Remote{
	
	void addSpecialty(String username, String pass, String specialty) throws RemoteException, ParserConfigurationException, SAXException, IOException, missingDoctorArgumentsException, missingResourceSpecArgumentsException, missingPatientArgumentsException, missingResourceIDArgumentsException, NotEnoughPermissionException, CantLoginException, SpecialtyAlreadyExistsException;
	void changePassword(String username, String pass, String newPass) throws RemoteException, CantLoginException;
	void createAppointment(String docUsername, String pass, String patientUname, String description) throws RemoteException, CantLoginException, UsernameDoesntExistException, ParserConfigurationException, SAXException, IOException, missingDoctorArgumentsException, missingResourceSpecArgumentsException, missingPatientArgumentsException, missingResourceIDArgumentsException, NotEnoughPermissionException;
	void createDoctor(String username, String pass, String newUsername, String spec) throws RemoteException, ParserConfigurationException, SAXException, IOException, missingDoctorArgumentsException, missingResourceSpecArgumentsException, missingPatientArgumentsException, missingResourceIDArgumentsException, NotEnoughPermissionException, CantLoginException, UsernameDoesntExistException, invalidSpecialtyException;
	void createPatient(String username, String password, boolean emergency, String newUsername, String first, String last, String add) throws RemoteException, NotEnoughPermissionException, CantLoginException, UsernameAlreadyExistsException, ParserConfigurationException, SAXException, IOException, missingDoctorArgumentsException, missingResourceSpecArgumentsException, missingPatientArgumentsException, missingResourceIDArgumentsException, UsernameDoesntExistException;
	void createStaff(String username, String pass, String newUsername, Integer wage) throws RemoteException, ParserConfigurationException, SAXException, IOException, missingDoctorArgumentsException, missingResourceSpecArgumentsException, missingPatientArgumentsException, missingResourceIDArgumentsException, NotEnoughPermissionException, CantLoginException, UsernameDoesntExistException;
	void createUser(String username, String pass, String newUsername) throws RemoteException, NotEnoughPermissionException, CantLoginException, UsernameAlreadyExistsException, ParserConfigurationException, SAXException, IOException, missingDoctorArgumentsException, missingResourceSpecArgumentsException, missingPatientArgumentsException, missingResourceIDArgumentsException;
	void doctorViewRecord(String username, String pass, String patientUname, boolean emergency) throws RemoteException, ParserConfigurationException, SAXException, IOException, missingDoctorArgumentsException, missingResourceSpecArgumentsException, missingPatientArgumentsException, missingResourceIDArgumentsException, DoctorViewRecordException, PatientDoesntExistException, NotEnoughPermissionException, CantLoginException;
	void viewRecord(String username, String pass) throws RemoteException, CantLoginException;
	void loginInSystem(String username, String pass) throws RemoteException, CantLoginException;
}