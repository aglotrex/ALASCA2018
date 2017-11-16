package fr.upmc.gaspardleo.admissioncontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.upmc.components.AbstractComponent;
import fr.upmc.gaspardleo.applicationvm.ApplicationVM;
import fr.upmc.datacenter.software.applicationvm.ApplicationVM.ApplicationVMPortTypes;
import fr.upmc.datacenter.software.applicationvm.connectors.ApplicationVMManagementConnector;
import fr.upmc.datacenter.software.applicationvm.ports.ApplicationVMManagementOutboundPort;
import fr.upmc.gaspardleo.requestdispatcher.RequestDispatcher;
import fr.upmc.gaspardleo.requestdispatcher.RequestDispatcher.RDPortTypes;
import fr.upmc.gaspardleo.admissioncontroller.interfaces.AdmissionControllerI;

public class AdmissionController 
		extends AbstractComponent
		implements AdmissionControllerI{

	public static enum	ACPortTypes {
		INTROSECTION
	}
	
	// RequestSource related components
	private Map<String, RequestDispatcher>					RDs;
	private Map<String, List<ApplicationVM>>				AVMs;
	private ArrayList<ApplicationVMManagementOutboundPort> 	avmPorts;
	private String											uri;
	
	public AdmissionController(String CVM_InboundPorURI) throws Exception{
		
		super(1, 1);

		this.RDs 		= new HashMap<String, RequestDispatcher>();
		this.AVMs 		= new HashMap<>();
		this.avmPorts 	= new ArrayList<ApplicationVMManagementOutboundPort>();
		
		this.toggleLogging();
		this.toggleTracing();
	}
	
	@Override
	public RequestDispatcher addRequestDispatcher(
			String RD_URI,
			String RG_RequestNotificationInboundPortURI) throws Exception {
		
		// Request Dispatcher creation
		RequestDispatcher rd = new RequestDispatcher(RD_URI, RG_RequestNotificationInboundPortURI);
		
		// Request Dispatcher debug
		rd.toggleLogging();
		rd.toggleTracing();
		
		String result = rd.getRDPortsURI().get(RDPortTypes.REQUEST_SUBMISSION_IN);
		
		RDs.put(result, rd);

		return rd;
	}
	
	@Override
	public ArrayList<ApplicationVM> addApplicationVMs(RequestDispatcher rd) throws Exception {
		
		String numRD = rd.getRDPortsURI().get(RDPortTypes.INTROSPECTION).split("-")[1];
		
		// Vm applications creation
		ApplicationVM vm0 = createApplicationVM("vm-" + numRD + "-0");
		ApplicationVM vm1 = createApplicationVM("vm-" + numRD + "-1");
		ApplicationVM vm2 = createApplicationVM("vm-" + numRD + "-2");
		
		ArrayList<ApplicationVM> newAVMs = new ArrayList<>();
		newAVMs.add(vm0);
		newAVMs.add(vm1);
		newAVMs.add(vm2);
		
		// Register application VM in Request Dispatcher
		rd.registerVM(
				vm0.getAVMPortsURI().get(ApplicationVMPortTypes.INTROSPECTION),
				vm0.getAVMPortsURI().get(ApplicationVMPortTypes.REQUEST_SUBMISSION));
		rd.registerVM(
				vm1.getAVMPortsURI().get(ApplicationVMPortTypes.INTROSPECTION),
				vm1.getAVMPortsURI().get(ApplicationVMPortTypes.REQUEST_SUBMISSION));
		rd.registerVM(
				vm2.getAVMPortsURI().get(ApplicationVMPortTypes.INTROSPECTION),
				vm2.getAVMPortsURI().get(ApplicationVMPortTypes.REQUEST_SUBMISSION));

		AVMs.put(rd.getRDPortsURI().get(RDPortTypes.REQUEST_SUBMISSION_IN), newAVMs);
		
		return newAVMs;
	}
	
	//TODO delete RD avec unregisterVM
	@Override
	public void removeRequestSource(String RD_RequestSubmissionInboundPortUri) throws Exception {
		RDs.get(RD_RequestSubmissionInboundPortUri).shutdown();
		for (ApplicationVM vm :  AVMs.get(RD_RequestSubmissionInboundPortUri)) {
			vm.shutdown();
		}
		
		RDs.remove(RD_RequestSubmissionInboundPortUri);
		AVMs.remove(RD_RequestSubmissionInboundPortUri);
	}
	
	private ApplicationVM createApplicationVM(String VM_URI) throws Exception{
				
		// Vm applications creation
		ApplicationVM vm = new ApplicationVM(VM_URI);
		
		// VM debug
		vm.toggleTracing();
		vm.toggleLogging();
				
		// Create a mock up port to manage the AVM component (allocate cores).
		ApplicationVMManagementOutboundPort avmPort = new ApplicationVMManagementOutboundPort(
				new AbstractComponent(0, 0) {});
		avmPort.publishPort();
		
		avmPort.doConnection(
				vm.getAVMPortsURI().get(ApplicationVMPortTypes.MANAGEMENT),
				ApplicationVMManagementConnector.class.getCanonicalName());
		
		// Cores allocation
		this.avmPorts.add(avmPort);
		return vm;
	}
	
	public Map<ACPortTypes, String>	getACPortsURI() throws Exception {
		HashMap<ACPortTypes, String> ret =
				new HashMap<ACPortTypes, String>();		
		ret.put(ACPortTypes.INTROSECTION,
				this.uri);
		return ret ;
	}

	@Override
	public ArrayList<ApplicationVMManagementOutboundPort> getApplicationVMManagementOutboundPorts() {

		return this.avmPorts;
	}
}