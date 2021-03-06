package fr.upmc.components.extensions.synchronizers.interfaces.syncTools.countDownLatch;

import fr.upmc.components.interfaces.RequiredI;


public interface CountDownLatchClientI
extends RequiredI
{
	/**
	 * The caller component will wait until there is no more component to wait.
	 * @throws Exception
	 */
	public void		await(
	) throws Exception;
	
	/**
	 * Decrement the number of waited components.
	 * @throws Exception
	 */
	public void		count(
	) throws Exception;
	
	/**
	 * @return Number of component waited.
	 * @throws Exception
	 */
	public int		getCount(
	) throws Exception;
}
