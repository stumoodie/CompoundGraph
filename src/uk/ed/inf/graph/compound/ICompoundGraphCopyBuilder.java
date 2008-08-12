package uk.ed.inf.graph.compound;

import uk.ed.inf.graph.compound.base.BaseChildCompoundGraph;
import uk.ed.inf.graph.compound.base.BaseCompoundEdge;
import uk.ed.inf.graph.compound.base.BaseCompoundNode;

public interface ICompoundGraphCopyBuilder {

	void setSourceSubgraph(
			ISubCompoundGraph<BaseCompoundNode, BaseCompoundEdge> sourceSubCompoundGraph);

	void setDestinatChildCompoundGraph(BaseChildCompoundGraph childCompoundGraph);

	void copyNodes();

	void copyEquivalentEdges();

}