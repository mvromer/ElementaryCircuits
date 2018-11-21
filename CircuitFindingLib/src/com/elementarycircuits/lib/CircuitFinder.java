package com.elementarycircuits.lib;

import java.util.*;
import java.util.stream.Collectors;

public class CircuitFinder {
    private Map<Node, Boolean> blocked = new HashMap<>();
    private Map<Node, Set<Node>> B = new HashMap<>();
    private Deque<Node> stack = new ArrayDeque<>();

    public static Set<List<Node>> findCircuits( DirectedGraph g ) {
        CircuitFinder finder = new CircuitFinder();
        return finder.findCircuitsInner( g );
    }

    private CircuitFinder() {
    }

    private Set<List<Node>> findCircuitsInner( DirectedGraph g ) {
        int s = 1;
        Set<Node> nodes = g.getNodes();
        Set<List<Node>> circuits = new HashSet<>();

        for( Node v : nodes ) {
            blocked.put( v, false );
            B.put( v, new HashSet<>() );
        }

        while( s < nodes.size() ) {
            LeastScc leastScc = getLeastScc( g, s );

            if( leastScc != null ) {
                Map<Node, Set<Node>> AK = leastScc.getSuccessors();
                s = leastScc.getLeastId();

                for( Node i : AK.keySet() ) {
                    blocked.put( i, false );
                    B.get( i ).clear();
                }

                circuit( Node.fromId( s ), s, AK, circuits );
                s++;
            }
            else {
                s = nodes.size();
            }
        }

        return circuits;
    }

    private boolean circuit( Node v, int s, Map<Node, Set<Node>> AK, Set<List<Node>> circuits ) {
        boolean f = false;
        stack.push( v );
        blocked.put( v, true );

        Set<Node> AKv = AK.get( v );
        for( Node w : AKv ) {
            if( w.getId() == s ) {
                // ArrayDeque, when we push/pop, works at the head of the deque. This means that when we iterate
                // through the stack by default, it goes from top to bottom. We want bottom to top to get the
                // actual directed path traversed to form this circuit. This is why re-add the "last" element
                // to our result list -- that represents the initial starting node that we used when forming this
                // circuit.
                List<Node> result = new ArrayList<>();
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
            for( Node w : AKv ) {
                Set<Node> Bw = B.get( w );
                if( !Bw.contains( v ) )
                    Bw.add( v );
            }
        }

        stack.pop();
        return f;
    }

    private void unblock( Node u ) {
        blocked.put( u, false );
        Set<Node> Bu = B.get( u );
        for( Node w : Bu ) {
            Bu.remove( w );
            if( blocked.get( w ) )
                unblock( w );
        }
    }

    private LeastScc getLeastScc( DirectedGraph g, int s ) {
        Set<Set<Node>> sccs = SCC.findStronglyConnectedComponentsFromLeastNode( g, Node.fromId( s ) );

        // NOTE: Exclude trivial strong component. Johnson's algorithm basically excludes loops (i.e., edges of the
        // form (v,v). Could probably add support for them by just checking to see if the trivial component as an
        // actual edge back to itself in the given graph, but for now that isn't strictly necessary.
        Set<Node> leastScc = null;
        int leastId = Integer.MAX_VALUE;

        for( Set<Node> scc : sccs ) {
            if( scc.size() > 1 ) {
                for( Node v : scc ) {
                    if( v.getId() < leastId ) {
                        leastScc = scc;
                        leastId = v.getId();
                    }
                }
            }
        }

        // Java 8 lambdas complain if a local variable captured by the lambda isn't final or effectively final. So we
        // have to play games to make our leastScc set available in the filter below.
        final Set<Node> leastSccFinal = leastScc;
        LeastScc result = null;

        if( leastScc != null ) {
            Map<Node, Set<Node>> successors = new HashMap<>();
            for( Node v : leastScc ) {
                successors.put( v, g.getSuccessorsForNode( v )
                        .stream()
                        .filter( n -> n.getId() >= s && leastSccFinal.contains( n ) )
                        .collect( Collectors.toSet() ) );
            }

            result = new LeastScc( successors, leastId );
        }

        return result;
    }

    private class LeastScc {
        private Map<Node, Set<Node>> successors;
        private int leastId;

        LeastScc( Map<Node, Set<Node>> successors, int leastId ) {
            this.successors = successors;
            this.leastId = leastId;
        }

        Map<Node, Set<Node>> getSuccessors() {
            return successors;
        }

        int getLeastId() {
            return leastId;
        }
    }
}
