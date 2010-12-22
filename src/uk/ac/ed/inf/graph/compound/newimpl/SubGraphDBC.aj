package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.designbycontract.Precondition;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ISubGraph;
import uk.ac.ed.inf.graph.compound.ISubGraphDBC;

public aspect SubGraphDBC extends ISubGraphDBC {

	@Override
	public pointcut allMethods(ISubGraph object) :
		execution(public void SubGraph.*(*))
		&& target(object);

	pointcut constructor(ICompoundGraph root) :
		execution(public SubGraph.new(ICompoundGraph))
		&& args(root);
	
	before(final ICompoundGraph root) : constructor(root) {
		new Precondition(){{
			assertion(root != null, "parameters not null");
		}};
	}
	
	pointcut addElement(SubGraph sg, ICompoundGraphElement element) :
		execution(public void addElement(ICompoundGraphElement))
		&& args(element)
		&& target(sg);
	
	before(SubGraph sg, final ICompoundGraphElement element) : addElement(sg, element) {
		new Precondition(){{
			assertion(element != null, "parameter not null");
		}};
	}
}

