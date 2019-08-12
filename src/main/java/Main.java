import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import model.Bottle;
import model.Solution;

@Slf4j
class Main {

    public static void main(String[] args) {
        var graph = new Graph();
        var bottleA = new Bottle(3, 0);
        var bottleB = new Bottle(5, 0);

        Gson gson = new Gson();
        log.info(gson.toJson(new Solution(1, graph.getNodeWithShortestPathToTarget(bottleA, bottleB, 1))));
        log.info(gson.toJson(new Solution(4, graph.getNodeWithShortestPathToTarget(bottleA, bottleB, 4))));
    }

}
