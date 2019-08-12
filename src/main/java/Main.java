import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Bottle;
import model.Solution;

class Main {

    public static void main(String[] args) {
        var graph = new Graph();
        var bottleA = new Bottle(3, 0);
        var bottleB = new Bottle(5, 0);
        var objectMapper = new ObjectMapper();

        try {
            System.out.println(objectMapper.writeValueAsString(new Solution(1, graph.getNodeWithShortestPathToTarget(bottleA, bottleB, 1))));
            System.out.println(objectMapper.writeValueAsString(new Solution(4, graph.getNodeWithShortestPathToTarget(bottleA, bottleB, 4))));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error mapping Object to JSON", e);
        }
    }

}
