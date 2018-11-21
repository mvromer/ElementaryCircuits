package com.elementarycircuits.lib.test;

import com.elementarycircuits.lib.DirectedGraph;
import com.elementarycircuits.lib.Node;
import com.elementarycircuits.lib.SCC;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class SCCTest {
    @Test
    public void t1() {
        // Test case pulled from wikipedia article on Tarjan's strongly connected components algorithm.
        DirectedGraph g = new DirectedGraph();
        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();
        Node n4 = new Node();
        Node n5 = new Node();
        Node n6 = new Node();
        Node n7 = new Node();
        Node n8 = new Node();

        g.addEdge( n1, n2 );
        g.addEdge( n2, n3 );
        g.addEdge( n3, n1 );
        g.addEdge( n4, n2 );
        g.addEdge( n4, n3 );
        g.addEdge( n4, n5 );
        g.addEdge( n5, n4 );
        g.addEdge( n5, n6 );
        g.addEdge( n6, n3 );
        g.addEdge( n6, n7 );
        g.addEdge( n7, n6 );
        g.addEdge( n8, n7 );
        g.addEdge( n8, n5 );
        g.addEdge( n8, n8 );

        Set<Node> scc1 = new HashSet<>();
        scc1.add( n1 );
        scc1.add( n2 );
        scc1.add( n3 );

        Set<Node> scc2 = new HashSet<>();
        scc2.add( n4 );
        scc2.add( n5 );

        Set<Node> scc3 = new HashSet<>();
        scc3.add( n6 );
        scc3.add( n7 );

        Set<Node> scc4 = new HashSet<>();
        scc4.add( n8 );

        Set<Set<Node>> sccs = SCC.findStronglyConnectedComponents( g );
        for( Set<Node> scc : sccs ) {
            System.out.print( "SCC: " );
            for( Node v : scc ) {
                System.out.print( v.getId() + " " );
            }
            System.out.println();
        }

        Assertions.assertEquals( sccs.size(), 4 );
        Assertions.assertTrue( sccs.contains( scc1 ) );
        Assertions.assertTrue( sccs.contains( scc2 ) );
        Assertions.assertTrue( sccs.contains( scc3 ) );
        Assertions.assertTrue( sccs.contains( scc4 ) );
    }

    @Test
    public void t3() {
        // Test case pulled from wikipedia article on Tarjan's strongly connected components algorithm.
        DirectedGraph g = new DirectedGraph();
        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();
        Node n4 = new Node();
        Node n5 = new Node();
        Node n6 = new Node();
        Node n7 = new Node();
        Node n8 = new Node();

        g.addEdge( n1, n2 );
        g.addEdge( n2, n3 );
        g.addEdge( n3, n1 );
        g.addEdge( n4, n2 );
        g.addEdge( n4, n3 );
        g.addEdge( n4, n5 );
        g.addEdge( n5, n4 );
        g.addEdge( n5, n6 );
        g.addEdge( n6, n3 );
        g.addEdge( n6, n7 );
        g.addEdge( n7, n6 );
        g.addEdge( n8, n7 );
        g.addEdge( n8, n5 );
        g.addEdge( n8, n8 );

        Set<Node> scc1 = new HashSet<>();
        scc1.add( n1 );
        scc1.add( n2 );
        scc1.add( n3 );

        Set<Node> scc2 = new HashSet<>();
        scc2.add( n4 );
        scc2.add( n5 );

        Set<Node> scc3 = new HashSet<>();
        scc3.add( n6 );
        scc3.add( n7 );

        Set<Node> scc4 = new HashSet<>();
        scc4.add( n8 );

        Set<Set<Node>> sccs = SCC.findStronglyConnectedComponentsFromLeastNode( g, n3 );
        for( Set<Node> scc : sccs ) {
            System.out.print( "SCC: " );
            for( Node v : scc ) {
                System.out.print( v.getId() + " " );
            }
            System.out.println();
        }
    }
}
