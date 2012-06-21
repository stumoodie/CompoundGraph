package uk.ac.ed.inf.graph.compound.newimpl;

import uk.ac.ed.inf.graph.compound.CompoundNodePair;
import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundEdgeFactory;
import uk.ac.ed.inf.graph.compound.ICompoundGraph;
import uk.ac.ed.inf.graph.compound.ICompoundNode;
import uk.ac.ed.inf.graph.compound.ICompoundNodeFactory;
import uk.ac.ed.inf.graph.compound.IElementAttribute;
import uk.ac.ed.inf.graph.compound.newimpl.CompoundGraph;
import uk.ac.ed.inf.graph.compound.testfixture.NodeElementAttribute;
import uk.ac.ed.inf.graph.compound.testfixture.NodeElementAttributeFactory;


class CompoundGraphMoveWithChildAndIncidentEdgesGraphTestFixture {
    private ICompoundGraph CompoundGraphMoveWithChildAndIncidentEdgesGraph;
    private ICompoundNode n1;
    private ICompoundNode n2;
    private ICompoundNode n5;
    private ICompoundNode n6;
    private ICompoundNode n3;
    private ICompoundNode n4;
    private ICompoundEdge e1;
    private ICompoundEdge e3;
    private ICompoundEdge e2;
  
    @SuppressWarnings("unused")
    public CompoundGraphMoveWithChildAndIncidentEdgesGraphTestFixture(){
        IElementAttribute rootAtt = new NodeElementAttribute("root");
        NodeElementAttributeFactory elementFactory = new NodeElementAttributeFactory(); 
        this.CompoundGraphMoveWithChildAndIncidentEdgesGraph = new CompoundGraph(rootAtt);
        ICompoundEdgeFactory rootEdgeFactory = this.CompoundGraphMoveWithChildAndIncidentEdgesGraph.edgeFactory();
        rootEdgeFactory.setAttributeFactory(elementFactory);
        ICompoundNodeFactory rootNodeFactory = this.CompoundGraphMoveWithChildAndIncidentEdgesGraph.nodeFactory();
        rootNodeFactory.setAttributeFactory(elementFactory);
        elementFactory.setName("N3");
        rootNodeFactory.setAttributeFactory(elementFactory);
        n3 = rootNodeFactory.createNode();
        ICompoundNodeFactory n3NodeFactory = n3.getChildCompoundGraph().nodeFactory();
        ICompoundEdgeFactory n3EdgeFactory = n3.getChildCompoundGraph().edgeFactory();
        elementFactory.setName("N1");
        n3NodeFactory.setAttributeFactory(elementFactory);
        n1 = n3NodeFactory.createNode();
        elementFactory.setName("N2");
        n3NodeFactory.setAttributeFactory(elementFactory);
        n2 = n3NodeFactory.createNode();
        elementFactory.setName("E1");
        n3EdgeFactory.setAttributeFactory(elementFactory);
        n3EdgeFactory.setPair(new CompoundNodePair(n1, n2));
        e1 = n3EdgeFactory.createEdge();
        ICompoundEdgeFactory e1EdgeFactory = e1.getChildCompoundGraph().edgeFactory();
        ICompoundNodeFactory e1NodeFactory = e1.getChildCompoundGraph().nodeFactory();
        elementFactory.setName("N5");
        e1NodeFactory.setAttributeFactory(elementFactory);
        n5 = e1NodeFactory.createNode(); 
        elementFactory.setName("N6");
        n3NodeFactory.setAttributeFactory(elementFactory);
        n6 = n3NodeFactory.createNode();
        elementFactory.setName("E3");
        n3EdgeFactory.setAttributeFactory(elementFactory);
        n3EdgeFactory.setPair(new CompoundNodePair(n5, n6));
        e3 = n3EdgeFactory.createEdge(); 
        elementFactory.setName("N4");
        rootNodeFactory.setAttributeFactory(elementFactory);
        n4 = rootNodeFactory.createNode();
        elementFactory.setName("E2");
        rootEdgeFactory.setAttributeFactory(elementFactory);
        rootEdgeFactory.setPair(new CompoundNodePair(n4, n2));
        e2 = rootEdgeFactory.createEdge();
    }
   
    public ICompoundGraph getGraph(){
        return CompoundGraphMoveWithChildAndIncidentEdgesGraph;
    }
   
    public ICompoundNode getNoden1() { return n1; } 

    public ICompoundNode getNoden2() { return n2; } 

    public ICompoundNode getNoden5() { return n5; } 

    public ICompoundNode getNoden6() { return n6; } 

    public ICompoundNode getNoden3() { return n3; } 

    public ICompoundNode getNoden4() { return n4; } 
  
    public ICompoundEdge getEdgee1() { return e1; } 

    public ICompoundEdge getEdgee3() { return e3; } 

    public ICompoundEdge getEdgee2() { return e2; } 
}