package com.elementarycircuits.lib;

import java.util.*;

// Based on pseudocode from wikipedia: https://en.wikipedia.org/wiki/Tarjan%27s_strongly_connected_components_algorithm
public class SCC {
    private int nextNodeIndex = 0;
    private Map<Node, Integer> nodeIndices = new HashMap<>();
    private Map<Node, Integer> nodeLowLinks = new HashMap<>();
    private Map<Node, Boolean> nodeOnStack = new HashMap<>();
    private Deque<Node> S = new ArrayDeque<>();

    public static Set<Set<Node>> findStronglyConnectedComopnents( DirectedGraph g ) {
        SCC scc = new SCC();
        return scc.findStronglyConnectedComponentsInner( g );
    }

    private SCC() {
    }

    private Set<Set<Node>> findStronglyConnectedComponentsInner( DirectedGraph g ) {
        Set<Set<Node>> sccs = new HashSet<>();

        for( Node v : g.getNodes() ) {
            if( !nodeIndices.containsKey( v ) ) {
                stronglyConnect( v, g, sccs );
            }
        }

        return sccs;
    }

    private void stronglyConnect( Node v, DirectedGraph g, Set<Set<Node>> sccs ) {
        nodeIndices.put( v, nextNodeIndex );
        nodeLowLinks.put( v, nextNodeIndex );
        ++nextNodeIndex;

        S.push( v );
        nodeOnStack.put( v, true );

        for( Node w : g.getSuccessorsForNode( v ) ) {
            if( !nodeIndices.containsKey( w ) ) {
                stronglyConnect( w, g, sccs );
                nodeLowLinks.put( v, Math.min( nodeLowLinks.get( v ), nodeLowLinks.get( w ) ) );
            }
            else if( nodeOnStack.get( w ) ) {
                nodeLowLinks.put( v, Math.min( nodeLowLinks.get( v ), nodeIndices.get( w ) ) );
            }
        }

        if( nodeLowLinks.get( v ) == nodeIndices.get( v ) ) {
            Set<Node> scc = new HashSet<>();
            Node w;

            do {
                w = S.pop();
                nodeOnStack.put( w, false );
                scc.add( w );
            } while( !w.equals( v ) );

            sccs.add( scc );
        }
    }
}
