import model.Bottle;

class Main {

    public static void main(String[] args) {
        var bottleA = new Bottle(3, 0);
        var bottleB = new Bottle(5, 0);

        var graph = new Graph();

        graph.getNodeWithShortestPathToTarget(bottleA, bottleB, 1).printNodeAsJSON();
        graph.getNodeWithShortestPathToTarget(bottleA, bottleB, 4).printNodeAsJSON();
    }

}
