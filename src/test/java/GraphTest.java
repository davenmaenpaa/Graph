import model.Bottle;
import model.Node;
import model.State;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    private final Graph graph = new Graph();

    @Test
    void getNodeWithShortestPathToTargetVolumeOne() {
        var expected = new Node(new State(new Bottle(3, 1), new Bottle(5, 5)), 5);
        var actual = graph.getNodeWithShortestPathToTarget(new Bottle(3, 0), new Bottle(5, 0), 1);

        assertEquals(expected, actual);
    }

    @Test
    void getNodeWithShortestPathToTargetVolumeFour() {
        var expected = new Node(new State(new Bottle(3, 3), new Bottle(5, 4)), 7);
        var actual = graph.getNodeWithShortestPathToTarget(new Bottle(3, 0), new Bottle(5, 0), 4);

        assertEquals(expected, actual);
    }

    @Test
    void testIfStartingNodeGeneratesCorrectChildNodes() {
        var nodes = new HashMap<Integer, Node>();
        var startingNode = new Node(new State(new Bottle(3, 0), new Bottle(5, 0)), 1);
        nodes.put(startingNode.hashCode(), startingNode);

        var actual = graph.buildNodes(nodes, startingNode);

        var expected = new ArrayList<Node>();
        expected.add(new Node(new State(new Bottle(3, 3), new Bottle(5, 0)), 2));
        expected.add(new Node(new State(new Bottle(3, 0), new Bottle(5, 5)), 2));

        assertEquals(expected, actual);
    }

    @Test
    void testIfChildNodeGeneratesCorrectlyWhenFillingFromBToA() {
        var nodes = new HashMap<Integer, Node>();
        var startingNode = new Node(new State(new Bottle(3, 0), new Bottle(5, 5)), 1);
        nodes.put(startingNode.hashCode(), startingNode);

        var actual = graph.buildNodes(nodes, startingNode);

        var expected = singletonList(new Node(new State(new Bottle(3, 3), new Bottle(5, 2)), 2));

        assertTrue(actual.contains(expected.get(0)));
    }

}