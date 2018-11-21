package com.elementarycircuits.lib;

import java.util.Objects;

public class Node {
    public static final int MIN_ID = 1;
    private static int nextId = MIN_ID;
    private int id;

    public Node() {
        this.id = nextId;
        ++nextId;
    }

    public static Node fromId( int id ) {
        return new Node( id );
    }

    private Node( int id ) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) return true;
        if( o == null || getClass() != o.getClass() ) return false;
        Node node = (Node) o;
        return id == node.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash( id );
    }
}
