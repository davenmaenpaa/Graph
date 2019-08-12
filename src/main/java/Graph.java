import lombok.Data;
import lombok.NoArgsConstructor;
import model.Bottle;
import model.Node;
import model.State;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Optional.empty;

@Data
@NoArgsConstructor
class Graph {

    Node getNodeWithShortestPathToTarget(Bottle bottleA, Bottle bottleB, int target) {
        var nodes = new HashMap<Integer, Node>();

        var startingNode = new Node(new State(bottleA, bottleB), 1);
        nodes.put(startingNode.hashCode(), startingNode);

        var queue = new LinkedList<Node>();
        queue.add(startingNode);

        Node nodeWithShortestPathToTarget = new Node(0);

        while (!queue.isEmpty()) {
            var parentNode = queue.getFirst();

            if (nodeHaveShorterPath(target, queue, nodeWithShortestPathToTarget, parentNode)) {
                nodeWithShortestPathToTarget = parentNode;
            }

            var childNodes = buildChildNodes(nodes, parentNode);
            queue.poll();

            childNodes.forEach(node -> {
                nodes.put(node.hashCode(), node);
                queue.add(node);
            });
        }

        return nodeWithShortestPathToTarget;
    }

    private boolean nodeHaveShorterPath(int target, LinkedList<Node> queue, Node nodeWithShortestPathToTarget,
                                        Node parentNode) {
        return isCurrentVolumeTheSameAsTarget(target, parentNode.getState().getBottleA()) &&
                childNodeDistanceIsShorter(nodeWithShortestPathToTarget, parentNode) ||

                isCurrentVolumeTheSameAsTarget(target, parentNode.getState().getBottleA()) &&
                        nodeWithShortestPathToTarget.getDistance() == 0 ||

                isCurrentVolumeTheSameAsTarget(target, parentNode.getState().getBottleB()) &&
                        childNodeDistanceIsShorter(nodeWithShortestPathToTarget, queue.getFirst()) ||

                isCurrentVolumeTheSameAsTarget(target, parentNode.getState().getBottleB()) &&
                        nodeWithShortestPathToTarget.getDistance() == 0;
    }

    private boolean childNodeDistanceIsShorter(Node nodeWithShortestPathToTarget, Node parentNode) {
        return parentNode.getDistance() < nodeWithShortestPathToTarget.getDistance();
    }

    private boolean isCurrentVolumeTheSameAsTarget(int target, Bottle bottleA) {
        return bottleA.getCurrentVolume() == target;
    }

    private boolean nodeExists(HashMap nodes, Node node) {
        return nodes.containsKey(node.hashCode());
    }

    List<Node> buildChildNodes(HashMap<Integer, Node> nodes, Node parentNode) {
        var childNodes = new ArrayList<Optional<Node>>();
        childNodes.add(newNodeFilledFromBottleAToB(nodes, parentNode));
        childNodes.add(newNodeFilledFromBottleBToA(nodes, parentNode));
        childNodes.add(newNodeFilledBottleA(nodes, parentNode));
        childNodes.add(newNodeFilledBottleB(nodes, parentNode));
        childNodes.add(newNodeEmptiedBottleA(nodes, parentNode));
        childNodes.add(newNodeEmptiedBottleB(nodes, parentNode));

        return childNodes.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<Node> newNodeFilledFromBottleAToB(HashMap<Integer, Node> nodes, Node parentNode) {
        var currentState = parentNode.getState();
        var bottleA = currentState.getBottleA();
        var bottleB = currentState.getBottleB();

        int volumeLeftInA;
        int volumeToFillFromAToB;

        if (bottleIsFull(currentState.getBottleB()) || bottleIsEmpty(bottleA)) {
            return empty();
        } else {
            volumeToFillFromAToB = Math.min(bottleA.getCurrentVolume(),
                    bottleB.getMaxVolume() - bottleB.getCurrentVolume());
            volumeLeftInA = bottleA.getCurrentVolume() - volumeToFillFromAToB;
        }

        var newState = State.builder()
                .bottleA(new Bottle(bottleA.getMaxVolume(), volumeLeftInA))
                .bottleB(new Bottle(bottleB.getMaxVolume(), volumeToFillFromAToB + bottleB.getCurrentVolume()))
                .build();

        var childNode = Node.builder()
                .state(newState)
                .parentNode(parentNode)
                .distance(parentNode.getDistance() + 1)
                .build();

        if (nodeExists(nodes, childNode)) {
            return empty();
        }

        return Optional.of(childNode);
    }

    private Optional<Node> newNodeFilledFromBottleBToA(HashMap<Integer, Node> nodes, Node parentNode) {
        var currentState = parentNode.getState();
        var bottleA = currentState.getBottleA();
        var bottleB = currentState.getBottleB();

        int volumeLeftInB;
        int volumeToFillFromBToA;

        if (bottleIsFull(currentState.getBottleA()) || bottleIsEmpty(bottleB)) {
            return empty();
        } else {
            volumeToFillFromBToA = Math.min(bottleB.getCurrentVolume(),
                    bottleA.getMaxVolume() - bottleA.getCurrentVolume());
            volumeLeftInB = bottleB.getCurrentVolume() - volumeToFillFromBToA;
        }

        var childState = State.builder()
                .bottleA(new Bottle(bottleA.getMaxVolume(), volumeToFillFromBToA + bottleA.getCurrentVolume()))
                .bottleB(new Bottle(bottleB.getMaxVolume(), volumeLeftInB))
                .build();

        var childNode = Node.builder()
                .state(childState)
                .parentNode(parentNode)
                .distance(parentNode.getDistance() + 1)
                .build();

        if (nodeExists(nodes, childNode)) {
            return empty();
        }

        return Optional.of(childNode);
    }

    private Optional<Node> newNodeFilledBottleA(HashMap<Integer, Node> nodes, Node parentNode) {
        if (bottleIsFull(parentNode.getState().getBottleA())) {
            return empty();
        }

        var childState = State.builder()
                .bottleA(new Bottle(parentNode.getState().getBottleA().getMaxVolume(),
                        parentNode.getState().getBottleA().getMaxVolume()))
                .bottleB(cloneBottle(parentNode.getState().getBottleB()))
                .build();

        var childNode = Node.builder()
                .state(childState)
                .parentNode(parentNode)
                .distance(parentNode.getDistance() + 1)
                .build();

        if (nodeExists(nodes, childNode)) {
            return empty();
        }

        return Optional.of(childNode);
    }

    private Optional<Node> newNodeFilledBottleB(HashMap<Integer, Node> nodes, Node parentNode) {
        if (bottleIsFull(parentNode.getState().getBottleA())) {
            return empty();
        }

        var newState = State.builder()
                .bottleA(cloneBottle(parentNode.getState().getBottleA()))
                .bottleB(new Bottle(parentNode.getState().getBottleB().getMaxVolume(),
                        parentNode.getState().getBottleB().getMaxVolume()))
                .build();

        var childNode = Node.builder()
                .state(newState)
                .parentNode(parentNode)
                .distance(parentNode.getDistance() + 1)
                .build();

        if (nodeExists(nodes, childNode)) {
            return empty();
        }

        return Optional.of(childNode);
    }

    private boolean bottleIsFull(Bottle bottle) {
        return isCurrentVolumeTheSameAsTarget(bottle.getMaxVolume(), bottle);
    }

    private boolean bottleIsEmpty(Bottle bottle) {
        return isCurrentVolumeTheSameAsTarget(0, bottle);
    }

    private Optional<Node> newNodeEmptiedBottleA(HashMap<Integer, Node> nodes, Node parentNode) {
        if (bottleIsEmpty(parentNode.getState().getBottleA())) {
            return empty();
        }

        var childState = State.builder()
                .bottleA(new Bottle(parentNode.getState().getBottleA().getMaxVolume(), 0))
                .bottleB(cloneBottle(parentNode.getState().getBottleB()))
                .build();

        var childNode = Node.builder()
                .state(childState)
                .parentNode(parentNode)
                .distance(parentNode.getDistance() + 1)
                .build();

        if (nodeExists(nodes, childNode)) {
            return empty();
        }

        return Optional.of(childNode);
    }

    private Optional<Node> newNodeEmptiedBottleB(HashMap<Integer, Node> nodes, Node parentNode) {
        if (bottleIsEmpty(parentNode.getState().getBottleA())) {
            return empty();
        }

        var childState = State.builder()
                .bottleA(cloneBottle(parentNode.getState().getBottleA()))
                .bottleB(new Bottle(parentNode.getState().getBottleB().getMaxVolume(), 0))
                .build();

        var childNode = Node.builder()
                .state(childState)
                .parentNode(parentNode)
                .distance(parentNode.getDistance() + 1)
                .build();

        if (nodeExists(nodes, childNode)) {
            return empty();
        }

        return Optional.of(childNode);
    }

    private Bottle cloneBottle(Bottle bottle) {
        return new Bottle(bottle.getMaxVolume(), bottle.getCurrentVolume());
    }

}
