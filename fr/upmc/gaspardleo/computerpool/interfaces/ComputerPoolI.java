package fr.upmc.gaspardleo.computerpool.interfaces;

import java.util.HashMap;
import java.util.HashSet;

import fr.upmc.components.interfaces.OfferedI;
import fr.upmc.components.interfaces.RequiredI;
import fr.upmc.gaspardleo.applicationvm.ApplicationVM.ApplicationVMPortTypes;

public interface ComputerPoolI extends OfferedI, RequiredI {
	
	
	/**
	 * Ajoute un ordinateur au pool actuel du composant.
	 * Détails des paramètres disponibles à @see {@link fr.upmc.gaspardleo.computer.Computer}.
	 * @return
	 * 		Nouveau Computer.
	 */
	public void createNewComputer(String computerURI,
			HashSet<Integer> possibleFrequencies,
			HashMap<Integer, Integer> processingPower,
			Integer defaultFrequency,
			Integer maxFrequencyGap,
			Integer numberOfProcessors,
			Integer numberOfCores) throws Exception;
	
	/**
	 * Créé une nouvelle ApplicationVM.
	 * @param avmURI 
	 * 		URI de la nouvelle ApplicationVM.
	 * @param numberOfCoreToAllocate
	 * 		Nombre de coeur à allouer à l'ApplicationVM.
	 * @return
	 * 		La nouvelle ApplicationVM.
	 */
	public HashMap<ApplicationVMPortTypes, String> createNewApplicationVM(String avmURI, Integer numberOfCoreToAllocate) throws Exception;
	
	
	public void releaseCores(String avmUri) throws Exception;
	
	/**
	 * La pool a-t-il des core disponnible ?
	 * @return
	 * 		true : core disponnible, false : aucun core n'est disponible.
	 * @throws Exception
	 */
	public Boolean hasAvailableCore() throws Exception;
	
	/**
	 * Incremente la fréquence des cores alloués à une AVM d'un pallier.
	 * @param avmUri
	 * 		AVM concernée.
	 * @return
	 * 		true : Une augmentation de la fréquence a été effectué sur au moins un core. 
	 * 		false : Tout les cores sont à la fréquence maximale.
	 * @throws Exception
	 */
	public Boolean increaseCoreFrequency(String avmUri) throws Exception;
	
	/**
	 * Decremente la fréquence des cores alloués à une AVM d'un pallier
	 * @param avmUri
	 * 		AVM concernée.
	 * @return
	 * 		true : Une diminution de la fréquence a été effectué sur au moins un core. 
	 * 		false : Tout les cores sont à la fréquence minimale.
	 * @throws Exception
	 */
	public Boolean decreaseCoreFrequency(String avmUri) throws Exception;

}
