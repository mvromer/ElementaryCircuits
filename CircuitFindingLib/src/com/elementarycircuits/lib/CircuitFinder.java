package com.elementarycircuits.lib;

import java.util.*;
import java.util.stream.Collectors;

// Based on Donald Johnson's algorithm described in the following paper:
// Finding All the Elementary Circuits of a Directed Graph
// Donald B. Johnson
// SIAM Journal on Computing 1975 4:1, 77-84
// https://doi.org/10.1137/0204007
//
public class CircuitFinder<T> {
    private Map<Node<T>, Boolean> blocked = new HashMap<>();
    private Map<Node<T>, Set<Node<T>>> B = new HashMap<>();
    private Deque<Node<T>> stack = new ArrayDeque<>();

    public static <T> Set<List<Node<T>>> findCircuits( DirectedGraph<T> g ) {
        CircuitFinder<T> finder = new CircuitFinder<>();
        return finder.findCircuitsInner( g );
    }

    private CircuitFinder() {
    }

    private Set<List<Node<T>>> findCircuitsInner( DirectedGraph<T> g ) {
        int s = 1;
        Set<Node<T>> nodes = g.getNodes();
        Set<List<Node<T>>> circuits = new HashSet<>();

        for( Node<T> v : nodes ) {
            blocked.put( v, false );
            B.put( v, new HashSet<>() );
        }

        while( s < nodes.size() ) {
            LeastScc<T> leastScc = getLeastScc( g, s );

            if( leastScc != null ) {
                Map<Node<T>, Set<Node<T>>> AK = leastScc.getSuccessors();
                s = leastScc.getLeastId();

                for( Node<T> i : AK.keySet() ) {
                    blocked.put( i, false );
                    B.get( i ).clear();
                }

                circuit( g.getNodeFromId( s ), s, AK, circuits );
                s++;
            }
            else {
                s = nodes.size();
            }
        }

        return circuits;
    }

    private boolean circuit( Node<T> v, int s, Map<Node<T>, Set<Node<T>>> AK, Set<List<Node<T>>> circuits ) {
        boolean f = false;
        stack.push( v );
        blocked.put( v, true );

        Set<Node<T>> AKv = AK.get( v );
        for( Node<T> w : AKv ) {
            if( w.getId() == s ) {
                // ArrayDeque, when we push/pop, works at the head of the deque. This means that when we iterate
                // through the stack by default, it goes from top to bottom. We want bottom to top to get the
                // actual directed path traversed to form this circuit. This is why re-add the "last" element
                // to our result list -- that represents the initial starting node that we used when forming this
                // circuit.
                List<Node<T>> result = new ArrayList<>();
                stack.descendingIterator().forEachRemaining( result::add );
                result.add( stack.getLast() );
                circuits.add( result );
                f = true;
            }
            else if( !blocked.get( w ) && circuit( w, s, AK, circuits ) )
                f = true;
        }

        if( f ) {
            unblock( v );
        }
        else {
            for( Node<T> w : AKv ) {
                Set<Node<T>> Bw = B.get( w );
                if( !Bw.contains( v ) )
                    Bw.add( v );
            }
        }

        stack.pop();
        return f;
    }

    private void unblock( Node<T> u ) {
        blocked.put( u, false );
        Set<Node<T>> Bu = B.get( u );
        for( Node<T> w : Bu ) {
            Bu.remove( w );
            if( blocked.get( w ) )
                unblock( w );
        }
    }

    private LeastScc getLeastScc( DirectedGraph<T> g, int s ) {
        Set<Set<Node<T>>> sccs = SCC.findStronglyConnectedComponentsFromLeastNode( g, g.getNodeFromId( s ) );

        // NOTE: Exclude trivial strong component. Johnson's algorithm basically excludes loops (i.e., edges of the
        // form (v,v). Could probably add support for them by just checking to see if the trivial component as an
        // actual edge back to itself in the given graph, but for now that isn't strictly necessary.
        Set<Node<T>> leastScc = null;
        int leastId = Integer.MAX_VALUE;

        for( Set<Node<T>> scc : sccs ) {
            if( scc.size() > 1 ) {
                for( Node<T> v : scc ) {
                    if( v.getId() < leastId ) {
                        leastScc = scc;
                        leastId = v.getId();
                    }
                }
            }
        }

        // Java 8 lambdas complain if a local variable captured by the lambda isn't final or effectively final. So we
        // have to play games to make our leastScc set available in the filter below.
        final Set<Node<T>> leastSccFinal = leastScc;
        LeastScc result = null;

        if( leastScc != null ) {
            Map<Node<T>, Set<Node<T>>> successors = new HashMap<>();
            for( Node<T> v : leastScc ) {
                successors.put( v, g.getSuccessorsForNode( v )
                        .stream()
                        .filter( n -> n.getId() >= s && leastSccFinal.contains( n ) )
                        .collect( Collectors.toSet() ) );
            }

            result = new LeastScc<T>( successors, leastId );
        }

        return result;
    }

    private class LeastScc<T> {
        private Map<Node<T>, Set<Node<T>>> successors;
        private int leastId;

        LeastScc( Map<Node<T>, Set<Node<T>>> successors, int leastId ) {
            this.successors = successors;
            this.leastId = leastId;
        }

        Map<Node<T>, Set<Node<T>>> getSuccessors() {
            return successors;
        }

        int getLeastId() {
            return leastId;
        }
    }
}
