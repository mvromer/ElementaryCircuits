package com.elementarycircuits.lib;

import java.util.*;

public class DirectedGraph<T> {
    private int nextNodeId = Node.MIN_ID;

    // Checks to see if a previous T has been already wrapped as a Node that we can reuse.
    private Map<T, Node<T>> dataToNodes = new HashMap<>();

    // This is just provide fast lookup for dataToNodes by ID.
    private Map<Integer, Node<T>> idToNodes = new HashMap<>();

    private Set<Node<T>> nodes = new HashSet<>();
    private Map<Node<T>, Set<Node<T>>> successors = new HashMap<>();

    public void addEdge( T from, T to ) {
        Node<T> fromNode = dataToNodes.computeIfAbsent( from, k -> {
            Node<T> n = createNode( from );
            nodes.add( n );
            idToNodes.put( n.getId(), n );
            return n;
        } );

        Node<T> toNode = dataToNodes.computeIfAbsent( to, k -> {
            Node<T> n = createNode( to );
            nodes.add( n );
            idToNodes.put( n.getId(), n );
            return n;
        } );

        Set<Node<T>> fromSuccessors = successors.computeIfAbsent( fromNode, key -> new HashSet<>() );
        fromSuccessors.add( toNode );
    }

    public Set<Node<T>> getNodes() {
        return Collections.unmodifiableSet( nodes );
    }

    public Node<T> getNodeFromId( int id ) {
        return idToNodes.get( id );
    }

    public Node<T> getNodeFromT( T data ) {
        return dataToNodes.get( data );
    }

    public Set<Node<T>> getSuccessorsForNode( Node<T> node ) {
        return Collections.unmodifiableSet( successors.computeIfAbsent( node, key -> new HashSet<>() ) );
    }

    private Node<T> createNode( T data ) {
        return Node.create( nextNodeId++, data );
    }
}
