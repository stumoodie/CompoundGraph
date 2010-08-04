package uk.ed.inf.graph.compound;

import uk.ac.ed.inf.designbycontract.ClassInvariant;
import uk.ac.ed.inf.designbycontract.Postcondition;
import uk.ac.ed.inf.designbycontract.Precondition;

public interface ISubgraphRemovalBuilder {

	public static abstract aspect ISubgraphRemovalBuilderDBC {
		
		public abstract pointcut allMethods(ISubgraphRemovalBuilder obj);
		
		pointcut setRemovalSubgraph(ISubgraphRemovalBuilder rb, ISubCompoundGraph subgraph) :
			execution(public void ISubgraphRemovalBuilder.setRemovalSubgraph(ISubCompoundGraph)) &&
			target(rb) &&
			args(subgraph);
		
		after(final ISubgraphRemovalBuilder rb, final ISubCompoundGraph subgraph) returning : setRemovalSubgraph(rb, subgraph) {
			new Postcondition(){{
				assertion(subgraph.equals(rb.getRemovalSubgraph()), "subgraph is set");
			}};
		}
		
		pointcut removeSubgraph(ISubgraphRemovalBuilder rb) :
			execution(public void removeSubgraph())
			&& target(rb);

		before(final ISubgraphRemovalBuilder rb) : removeSubgraph(rb) {
			new Precondition(){{
				assertion(rb.canRemoveSubgraph(), "can remove subgraph");
			}};
		}

		after(final ISubgraphRemovalBuilder rb) returning : removeSubgraph(rb) {
			new Precondition(){{
				assertion(rb.canRemoveSubgraph(), "can remove subgraph");
				assertion(rb.getRemovalSubgraph() != null, "has removal subgraph");
			}};
		}
		
		after(final ISubgraphRemovalBuilder rb) : allMethods(rb){
			new ClassInvariant(){{
				assertion(rb.getGraph() != null, "graph always defined");
				assertion(implies(rb.canRemoveSubgraph(), rb.getRemovalSubgraph() != null), "can remove implied subgraph defined");
				assertion(implies(rb.canRemoveSubgraph(), rb.getRemovalSubgraph().isConsistentSnapShot()), "can remove implied subgraph is consistent");
				assertion(implies(rb.getRemovalSubgraph() == null, !rb.canRemoveSubgraph()), "no subgraph implies cannot remove");
			}};
		}
	}
	
	/**
	 * Sets the subgraph to be removed.
	 * @param subgraph the subgraph to be removed, whoch cannot be null
	 */
	void setRemovalSubgraph(ISubCompoundGraph subgraph);
	
	/**
	 * Get subgraph to be removed
	 * @return the subgraph to be removed.
	 */
	ISubCompoundGraph getRemovalSubgraph();
	
	/**
	 * Tests if subgraph removal will succeed. To succeed the subgraph must belong to this graph,
	 *  it must be a consistent snapshot and cannot be null.
	 * @param subgraph the subgraph to be removed, which can be null.
	 * @return true if the subgraph will succeed, false otherwise. 
	 */
	boolean canRemoveSubgraph();
	
	/**
	 * Removes the nodes and edges defined in the subgraph from this graph. The subgraph must be consistent with
	 *  this graph and be a subgraph of this graph.
	 * @param subgraph The subgraph to remove, cannot be null.
	 * @throws IllegalArgumentException if <code>canRemoveSubgraph(subgraph) == false</code>.
	 * @deprecated use setRemovalSubgraph() then removeSubgraph()
	 */ 
	void removeSubgraph(ISubCompoundGraph subgraph);

	void removeSubgraph();
	
	/**
	 * Gets the graph that owns this removal builder
	 * @return the graph, which cannot be null.
	 */
	ICompoundGraph getGraph();

}
