package fr.upmc.gaspardleo.admissioncontroller.interfaces;

import java.util.ArrayList;

import fr.upmc.datacenter.software.applicationvm.ports.ApplicationVMManagementOutboundPort;
import fr.upmc.gaspardleo.applicationvm.ApplicationVM;
import fr.upmc.gaspardleo.requestdispatcher.RequestDispatcher;

public interface AdmissionControllerI {

	public RequestDispatcher addRequestDispatcher(
			String RD_URI,
			String RG_RequestNotificationInboundPortURI) throws Exception;
	
	public ArrayList<ApplicationVM> addApplicationVMs(RequestDispatcher rd) throws Exception;
	
	/**
	 * Supprime le RequestDispatcher associé à l'URI du port donné en paramètre.
	 * @param RD_RequestSubmissionInboundPortUri
	 * 		Uri du port du RequestDispatcher à supprimer.
	 * @throws Exception
	 */
	public void removeRequestSource(
			String RD_RequestSubmissionInboundPortUri) throws Exception;
	
	public ArrayList<ApplicationVMManagementOutboundPort> getApplicationVMManagementOutboundPorts();
}
