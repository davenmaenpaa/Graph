package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@Accessors(chain = true)
public final class Node {

    State state;
    int distance;
    Node parentNode;

    public Node(State state, int distance) {
        this.state = state;
        this.distance = distance;
    }

    public Node(int distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return state.equals(node.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }

}
