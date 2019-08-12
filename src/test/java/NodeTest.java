import model.Bottle;
import model.Node;
import model.State;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @Test
    void testEquals() {
        var stateA = new State(new Bottle(3, 3), new Bottle(5, 2));
        var nodeA = new Node(stateA, 10);

        var stateB = new State(new Bottle(3, 3), new Bottle(5, 2));
        var nodeB = new Node(stateB, 20);

        assertEquals(nodeA, nodeB);
    }

}