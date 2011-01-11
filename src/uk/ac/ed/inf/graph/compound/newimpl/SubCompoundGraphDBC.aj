package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.designbycontract.Precondition;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundGraphElement;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraph;
import uk.ac.ed.inf.graph.compound.ISubCompoundGraphDBC;

public aspect SubCompoundGraphDBC extends ISubCompoundGraphDBC {

	public pointcut allMethods(ISubCompoundGraph object) :
		execution(public void SubCompoundGraph.*(*))
		&& target(object);

	pointcut constructor(ICompoundGraph root) :
		execution(SubCompoundGraph.new(ICompoundGraph))
		&& args(root);
	
	before(final ICompoundGraph root) : constructor(root) {
		new Precondition(){{
			assertion(root != null, "parameters not null");
		}};
	}
	
	pointcut addTopElement(SubCompoundGraph sg, ICompoundGraphElement element) :
		execution(void addTopElement(ICompoundGraphElement))
		&& args(element)
		&& target(sg);
	
	before(SubCompoundGraph sg, final ICompoundGraphElement element) : addTopElement(sg, element) {
		new Precondition(){{
			assertion(element != null, "parameter not null");
			assertion(element.isRemoved() == false, "top element is not removed");
		}};
	}
}

