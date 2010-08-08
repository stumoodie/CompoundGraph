/**
 * 
 */
package uk.ac.ed.inf.graph.basic.listeners;

/**
 * @author smoodie
 *
 */
public interface ISuppressableChangeListenee {
	/**
	 * Enables or disables the listeners observing this object. If disabled then event notifications
	 * will not be triggered. 
	 * @param enabled the enablement status, true means listeners will act on events.
	 */
	void setListenersEnabled(boolean enabled);
	
	/**
	 * Gtes the enablement status of the listeners on this object.
	 * @return true if listeners will fire upon events, false otherwise.
	 */
	boolean areListenersEnabled();

}
