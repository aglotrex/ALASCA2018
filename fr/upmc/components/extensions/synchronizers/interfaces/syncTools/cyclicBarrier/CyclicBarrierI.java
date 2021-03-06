package fr.upmc.components.extensions.synchronizers.interfaces.syncTools.cyclicBarrier;

import fr.upmc.components.interfaces.OfferedI;

public interface CyclicBarrierI extends OfferedI {
	
	/**  Waits until all parties have invoked await on this barrier.
	 * @return	??
	 * @throws Exception
	 */
	public int await() throws Exception;
	
	/**Returns the number of parties currently waiting at the barrier.
	 * @return	??
	 * @throws Exception
	 */
	public int getNumberWaiting() throws Exception;
	
	/**Returns the number of parties currently missing at the barrier.
	 * @return	??
	 * @throws Exception
	 */
	public int getNumberToWait() throws Exception;
}
