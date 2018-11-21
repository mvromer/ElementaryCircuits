package com.elementarycircuits.lib;

import java.util.Objects;

public class Node {
    private static int nextId = 1;
    private int id;

    public Node() {
        this.id = nextId;
        ++nextId;
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
