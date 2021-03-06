package fr.upmc.datacenter.hardware.processors.interfaces;

//Copyright Jacques Malenfant, Univ. Pierre et Marie Curie.
//
//Jacques.Malenfant@lip6.fr
//
//This software is a computer program whose purpose is to provide a
//basic component programming model to program with components
//distributed applications in the Java programming language.
//
//This software is governed by the CeCILL-C license under French law and
//abiding by the rules of distribution of free software.  You can use,
//modify and/ or redistribute the software under the terms of the
//CeCILL-C license as circulated by CEA, CNRS and INRIA at the following
//URL "http://www.cecill.info".
//
//As a counterpart to the access to the source code and  rights to copy,
//modify and redistribute granted by the license, users are provided only
//with a limited warranty  and the software's author,  the holder of the
//economic rights,  and the successive licensors  have only  limited
//liability. 
//
//In this respect, the user's attention is drawn to the risks associated
//with loading,  using,  modifying and/or developing or reproducing the
//software by the user in light of its specific status of free software,
//that may mean  that it is complicated to manipulate,  and  that  also
//therefore means  that it is reserved for developers  and  experienced
//professionals having in-depth computer knowledge. Users are therefore
//encouraged to load and test the software's suitability as regards their
//requirements in conditions enabling the security of their systems and/or 
//data to be ensured and,  more generally, to use and operate it in the 
//same conditions as regards security. 
//
//The fact that you are presently reading this means that you have had
//knowledge of the CeCILL-C license and that you accept its terms.

import fr.upmc.components.interfaces.OfferedI;
import fr.upmc.components.interfaces.RequiredI;
import fr.upmc.datacenter.software.applicationvm.interfaces.TaskI;

/**
 * The interface <code>ProcessorServicesI</code> defines the functional
 * services of a processor, such as running a task on a given core.
 *
 * <p><strong>Description</strong></p>
 * 
 * The method <code>executeTaskOnCore</code> runs a given task on a given core.
 * 
 * <p><strong>Invariant</strong></p>
 * 
 * <pre>
 * invariant		true
 * </pre>
 * 
 * <p>Created on : January 19, 2015</p>
 * 
 * @author	<a href="mailto:Jacques.Malenfant@lip6.fr">Jacques Malenfant</a>
 * @version	$Name$ -- $Revision$ -- $Date$
 */
public interface			ProcessorServicesI
extends		OfferedI,
			RequiredI
{
	/**
	 * execute a task on a given core.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	t != null && isValidCoreNo(coreNo)
	 * post	true			// no postcondition.
	 * </pre>
	 *
	 * @param t			task to be executed.
	 * @param coreNo	number of the core on which t must be executed.
	 * @throws Exception
	 */
	public void			executeTaskOnCore(final TaskI t, final int coreNo)
	throws Exception ;

	/**
	 * execute a task on a given core and notify the end of the task through
	 * a port which URI is given.
	 * 
	 * The port which URI is given must implement the interface
	 * <code>ProcessorServicesNotificationI</code>.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	t != null && isValidCoreNo(coreNo) && notificationPortURI != null
	 * post	true			// no postcondition.
	 * </pre>
	 *
	 * @param t						task to be executed.
	 * @param coreNo				number of the core on which t must be executed.
	 * @param notificationPortURI	URI of the port to which the end of the task will be notified.
	 * @throws Exception
	 */
	public void			executeTaskOnCoreAndNotify(
		final TaskI t,
		final int coreNo,
		final String notificationPortURI
		) throws Exception ;
}
