package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    Node parentNode;
    int distance;

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

    public void printNodeAsJSON() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            System.out.println(objectMapper.writeValueAsString(this));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not write object as string", e);
        }
    }
}
