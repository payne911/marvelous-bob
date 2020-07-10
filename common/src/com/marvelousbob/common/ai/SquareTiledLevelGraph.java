package com.marvelousbob.common.ai;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.marvelousbob.common.model.entities.level.Wall;
import com.marvelousbob.common.state.Level;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class SquareTiledLevelGraph implements IndexedGraph<Vector2> {

    private Level level;
    @Getter
    private Map<Vector2, Integer> nodes;
    private int nodeIndex;
    @Getter
    private Map<Vector2, Array<Connection<Vector2>>> connections;
    private int sizeX;
    private int sizeY;
    private int gridSize;
    private boolean computed;


    public SquareTiledLevelGraph(Level level) {
        this.nodeIndex = 0;
        this.level = level;
        this.nodes = new LinkedHashMap<>();
        this.connections = new HashMap<>();
        this.sizeX = level.getSizeX();
        this.sizeY = level.getSizeY();
        this.gridSize = level.getGridSize();
    }


    @Override
    public Array<Connection<Vector2>> getConnections(Vector2 fromNode) {
        return connections.get(fromNode);
    }


    @Override
    public int getNodeCount() {
        return nodeIndex;
    }


    public void computeConnections() {
        if (!computed) {
            computeNodes(level.getWalls());
            int halfGrid = gridSize / 2;
            float maxDist = (float) Math.sqrt(2 * halfGrid * halfGrid);
            for (var v1 : nodes.keySet()) {
                Array<Connection<Vector2>> neighbors = new Array<>();
                for (var v2 : nodes.keySet()) {
                    // todo: fix this stupid O(n^2) distance implementation...
                    if (v1.epsilonEquals(v2)) {
                        continue;
                    }
                    if (Float.compare(v1.dst(v2), maxDist + 1) <= 0) {
                        MyDefaultConnection conn = new MyDefaultConnection(v1, v2);
                        neighbors.add(conn);
                    }
                }
                connections.put(v1, neighbors);
            }
        }
        computed = true;
    }


    private void computeNodes(List<Wall> walls) {
        int halfGrid = gridSize / 2;
        for (int i = 1; i < sizeX; i++) {
            for (int j = 1; j < sizeY; j++) {
                float x = (i * gridSize);
                float y = (j * gridSize);
                Vector2 v1 = new Vector2(x, y);
                Vector2 v2 = new Vector2(x + halfGrid, y);
                Vector2 v3 = new Vector2(x, y + halfGrid);
                Vector2 v4 = new Vector2(x + halfGrid, y + halfGrid);
                addVectorIfNotInWall(walls, v1, v2, v3, v4);
            }
        }
    }


    private void addVectorIfNotInWall(List<Wall> walls, Vector2... vectors) {
        Array<Vector2> conn = new Array<>();
        for (var v : vectors) {
            boolean shouldAdd = true;
            for (var w : walls) {
                if (w.getRectangle().contains(v)) {
                    shouldAdd = false;
                    break;
                }
            }
            if (shouldAdd) {
                nodes.put(v, nodeIndex);

                nodeIndex++;
                conn.add(v);
            }
        }

    }


    public int getIndex(Vector2 node) {
        return nodes.get(node);
    }


    public Vector2 findNodeClosestTo(Vector2 vect) {
        Vector2 closest = null;
        for (var v : connections.keySet()) {
            if (closest == null) {
                closest = v;
            }
            float dist = vect.dst2(v);
            if (dist < vect.dst2(closest)) {
                closest = v;
            }
        }
        return closest;
    }


    static class MyDefaultConnection extends DefaultConnection<Vector2> {

        public MyDefaultConnection(Vector2 fromNode, Vector2 toNode) {
            super(fromNode, toNode);
        }

        @Override
        public float getCost() {
            return fromNode.dst2(toNode);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.fromNode, this.toNode);
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            MyDefaultConnection that = (MyDefaultConnection) o;
            return (fromNode.equals(that.fromNode) &&
                    toNode.equals(that.toNode));

        }

        @Override
        public String toString() {
            String vectStr = "%s, %s";
            return "[%s] -> [%s]"
                    .formatted(
                            vectStr.formatted(fromNode.x, fromNode.y),
                            vectStr.formatted(toNode.x, toNode.y));
        }
    }

}
