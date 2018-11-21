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

        Set<Node<Integer>> scc1 = new HashSet<>();
        scc1.add( n1 );
        scc1.add( n2 );
        scc1.add( n3 );

        Set<Node<Integer>> scc2 = new HashSet<>();
        scc2.add( n4 );
        scc2.add( n5 );

        Set<Node<Integer>> scc3 = new HashSet<>();
        scc3.add( n6 );
        scc3.add( n7 );

        Set<Node<Integer>> scc4 = new HashSet<>();
        scc4.add( n8 );

        Set<Set<Node<Integer>>> sccs = SCC.findStronglyConnectedComponents( g );
        for( Set<Node<Integer>> scc : sccs ) {
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

        Set<Node<Integer>> scc1 = new HashSet<>();
        scc1.add( n1 );
        scc1.add( n2 );
        scc1.add( n3 );

        Set<Node<Integer>> scc2 = new HashSet<>();
        scc2.add( n4 );
        scc2.add( n5 );

        Set<Node<Integer>> scc3 = new HashSet<>();
        scc3.add( n6 );
        scc3.add( n7 );

        Set<Node<Integer>> scc4 = new HashSet<>();
        scc4.add( n8 );

        Set<Set<Node<Integer>>> sccs = SCC.findStronglyConnectedComponentsFromLeastNode( g, n3 );
        for( Set<Node<Integer>> scc : sccs ) {
            System.out.print( "SCC: " );
            for( Node v : scc ) {
                System.out.print( v.getId() + " " );
            }
            System.out.println();
        }
    }
}
