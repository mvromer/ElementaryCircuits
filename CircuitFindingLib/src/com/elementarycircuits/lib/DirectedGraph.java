package com.elementarycircuits.lib;

import java.util.*;

public class DirectedGraph {
    private Set<Node> nodes = new HashSet<>();
    private Map<Node, Set<Node>> successors = new HashMap<>();

    public void addEdge( Node from, Node to ) {
        nodes.add( from );
        nodes.add( to );
        Set<Node> fromSuccessors = successors.computeIfAbsent( from, key -> new HashSet<>() );
        fromSuccessors.add( to );
    }

    public Set<Node> getNodes() {
        return Collections.unmodifiableSet( nodes );
    }

    public Set<Node> getSuccessorsForNode( Node node ) {
        return Collections.unmodifiableSet( successors.computeIfAbsent( node, key -> new HashSet<>() ) );
    }
}
