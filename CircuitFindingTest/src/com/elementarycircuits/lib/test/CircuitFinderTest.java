package com.elementarycircuits.lib.test;

import com.elementarycircuits.lib.CircuitFinder;
import com.elementarycircuits.lib.DirectedGraph;
import com.elementarycircuits.lib.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CircuitFinderTest {
    @Test
    public void t1() {
        DirectedGraph g = new DirectedGraph();
        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();

        g.addEdge( n1, n2 );
        g.addEdge( n2, n1 );
        g.addEdge( n2, n3 );

        Set<List<Node>> circuits = CircuitFinder.findCircuits( g );

        for( List<Node> circuit : circuits ) {
            System.out.print( "Circuit: " );
            for( Node v : circuit ) {
                System.out.print( v.getId() + " " );
            }
        }

        List<Node> c1 = Arrays.asList( n1, n2, n1 );
        Assertions.assertTrue( circuits.contains( c1 ) );
    }

    @Test
    public void t2() {
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

        Set<List<Node>> circuits = CircuitFinder.findCircuits( g );

        for( List<Node> circuit : circuits ) {
            System.out.print( "Circuit: " );
            for( Node v : circuit ) {
                System.out.print( v.getId() + " " );
            }
            System.out.println();
        }

        //List<Node> c1 = Arrays.asList( n1, n2, n1 );
        //Assertions.assertTrue( circuits.contains( c1 ) );
    }
}
