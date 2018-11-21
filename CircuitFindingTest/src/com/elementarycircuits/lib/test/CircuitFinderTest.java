package com.elementarycircuits.lib.test;

import com.elementarycircuits.lib.CircuitFinder;
import com.elementarycircuits.lib.DirectedGraph;
import com.elementarycircuits.lib.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CircuitFinderTest {
    @Test
    public void t1() {
        DirectedGraph<Integer> g = new DirectedGraph<>();
        g.addEdge( 1, 2 );
        g.addEdge( 2, 1 );
        g.addEdge( 2, 3 );

        Node<Integer> n1 = g.getNodeFromT( 1 );
        Node<Integer> n2 = g.getNodeFromT( 2 );
        Node<Integer> n3 = g.getNodeFromT( 3 );


        Set<List<Node<Integer>>> circuits = CircuitFinder.findCircuits( g );

        for( List<Node<Integer>> circuit : circuits ) {
            System.out.print( "Circuit: " );
            for( Node<Integer> v : circuit ) {
                System.out.print( v.getId() + " " );
            }
        }

        List<Node<Integer>> c1 = Arrays.asList( n1, n2, n1 );
        Assertions.assertTrue( circuits.contains( c1 ) );
    }

    @Test
    public void t2() {
        // Test case pulled from wikipedia article on Tarjan's strongly connected components algorithm.
        DirectedGraph<Integer> g = new DirectedGraph<>();

        g.addEdge( 1, 2 );
        g.addEdge( 2, 3 );
        g.addEdge( 3, 1 );
        g.addEdge( 4, 2 );
        g.addEdge( 4, 3 );
        g.addEdge( 4, 5 );
        g.addEdge( 5, 4 );
        g.addEdge( 5, 6 );
        g.addEdge( 6, 3 );
        g.addEdge( 6, 7 );
        g.addEdge( 7, 6 );
        g.addEdge( 8, 7 );
        g.addEdge( 8, 5 );
        g.addEdge( 8, 8 );

        Node<Integer> n1 = g.getNodeFromT( 1 );
        Node<Integer> n2 = g.getNodeFromT( 2 );
        Node<Integer> n3 = g.getNodeFromT( 3 );
        Node<Integer> n4 = g.getNodeFromT( 4 );
        Node<Integer> n5 = g.getNodeFromT( 5 );
        Node<Integer> n6 = g.getNodeFromT( 6 );
        Node<Integer> n7 = g.getNodeFromT( 7 );
        Node<Integer> n8 = g.getNodeFromT( 8 );

        Set<List<Node<Integer>>> circuits = CircuitFinder.findCircuits( g );

        for( List<Node<Integer>> circuit : circuits ) {
            System.out.print( "Circuit: " );
            for( Node<Integer> v : circuit ) {
                System.out.print( v.getId() + " " );
            }
            System.out.println();
        }
    }
}
