package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.newimpl.CompoundGraph;
import uk.ac.ed.inf.graph.compound.testfixture.ElementAttribute;
import uk.ac.ed.inf.graph.compound.testfixture.ElementAttributeFactory;


class FlatGraphTestFixture {
    private ICompoundGraph FlatGraph;
    private ICompoundNode n1;
    private ICompoundNode n2;
    private ICompoundNode n3;
    private ICompoundNode n4;
    private ICompoundNode n5;
    private ICompoundEdge e1;
    private ICompoundEdge e2;
  
    @SuppressWarnings("unused")
    public FlatGraphTestFixture(){
        IElementAttribute rootAtt = new ElementAttribute("root");
        ElementAttributeFactory elementFactory = new ElementAttributeFactory(); 
        this.FlatGraph = new CompoundGraph(rootAtt);
        ICompoundEdgeFactory rootEdgeFactory = this.FlatGraph.edgeFactory();
        rootEdgeFactory.setAttributeFactory(elementFactory);
        ICompoundNodeFactory rootNodeFactory = this.FlatGraph.nodeFactory();
        rootNodeFactory.setAttributeFactory(elementFactory);
        elementFactory.setName("N1");
        rootNodeFactory.setAttributeFactory(elementFactory);
        n1 = rootNodeFactory.createNode();
        elementFactory.setName("N2");
        rootNodeFactory.setAttributeFactory(elementFactory);
        n2 = rootNodeFactory.createNode();
        elementFactory.setName("N3");
        rootNodeFactory.setAttributeFactory(elementFactory);
        n3 = rootNodeFactory.createNode();
        elementFactory.setName("N4");
        rootNodeFactory.setAttributeFactory(elementFactory);
        n4 = rootNodeFactory.createNode();
        elementFactory.setName("N5");
        rootNodeFactory.setAttributeFactory(elementFactory);
        n5 = rootNodeFactory.createNode();
        elementFactory.setName("E1");
        rootEdgeFactory.setAttributeFactory(elementFactory);
        rootEdgeFactory.setPair(new CompoundNodePair(n1, n2));
        e1 = rootEdgeFactory.createEdge();
        elementFactory.setName("E2");
        rootEdgeFactory.setAttributeFactory(elementFactory);
        rootEdgeFactory.setPair(new CompoundNodePair(n4, n2));
        e2 = rootEdgeFactory.createEdge();
    }
   
    public ICompoundGraph getGraph(){
        return FlatGraph;
    }
   
    public ICompoundNode getNoden1() { return n1; } 

    public ICompoundNode getNoden2() { return n2; } 

    public ICompoundNode getNoden3() { return n3; } 

    public ICompoundNode getNoden4() { return n4; } 

    public ICompoundNode getNoden5() { return n5; } 
  
    public ICompoundEdge getEdgee1() { return e1; } 

    public ICompoundEdge getEdgee2() { return e2; } 
}