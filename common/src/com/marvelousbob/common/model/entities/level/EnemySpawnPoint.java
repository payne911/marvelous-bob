package com.marvelousbob.common.model.entities.level;

import static com.badlogic.gdx.math.MathUtils.PI2;

import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.PathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.ai.SquareTiledLevelGraph;
import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.model.entities.Drawable;
import com.marvelousbob.common.state.Level;
import com.marvelousbob.common.utils.UUID;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import space.earlygrey.shapedrawer.ShapeDrawer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class EnemySpawnPoint implements Drawable, Identifiable {

    public static final int DEFAULT_SIZE = 16;
    public static final Color DEFAULT_COLOR = Color.valueOf("3d144c");
    private static final Color PATH_COLOR = Color.CYAN.cpy();
    private UUID uuid;
    private Vector2 pos;
    private float hp, maxHp;
    private Polygon shape;
    private Polygon shape2;
    private Color color;
    private ArrayList<ArrayList<Vector2>> pathsToBase; // todo change to Map<PlayerBase, Array<Vector2>>   --- OLA
    private ArrayList<Vector2> graphNodes; // for debug purpose
    private float offset;

    /* For spawning */
    private float spawnRate = MathUtils.random(2f, 5f); // time in SECONDS
    private float accumulator = 0;

    public EnemySpawnPoint(UUID uuid, Vector2 pos, Polygon shape, Polygon shape2, Color color) {
        this.uuid = uuid;
        this.pos = pos;
        this.shape = shape;
        this.shape2 = shape2;
        this.color = color;
        this.pathsToBase = new ArrayList<>();
        this.graphNodes = new ArrayList<>();
        this.offset = 0;
    }

    public static EnemySpawnPoint starShaped(UUID uuid, Vector2 center, float size, Color color) {
        Vector2 p1 = new Vector2(center.x + size, center.y);
        Vector2 p2 = p1.cpy().rotateAround(center, 120);
        Vector2 p3 = p2.cpy().rotateAround(center, 120);
        Vector2 p4 = p1.cpy().rotateAround(center, 60);
        Vector2 p5 = p2.cpy().rotateAround(center, 60);
        Vector2 p6 = p3.cpy().rotateAround(center, 60);
        float[] tVertices = new float[]{
                p1.x, p1.y,
                p2.x, p2.y,
                p3.x, p3.y,
        };

        float[] t2Vertices = new float[]{
                p4.x, p4.y,
                p5.x, p5.y,
                p6.x, p6.y,
        };
        return new EnemySpawnPoint(uuid, center, new Polygon(tVertices), new Polygon(t2Vertices),
                color);
    }

    public static EnemySpawnPoint starShaped(UUID uuid, Vector2 center, float size) {
        return starShaped(uuid, center, size, DEFAULT_COLOR.cpy());
    }

    public static EnemySpawnPoint starShaped(UUID uuid, Vector2 center) {
        return starShaped(uuid, center, DEFAULT_SIZE, DEFAULT_COLOR.cpy());
    }

    /**
     * Used by the server to know if an enemy should be spawned by this SpawnPoint.
     *
     * @param delta amount of time that passed since last check
     * @return {@code true} when ready to spawn a new unit
     */
    public boolean update(float delta) {
        accumulator += delta;
        if (accumulator >= spawnRate) { // it's time to spawn !
            accumulator = 0;
            return true;
        }
        return false;
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        float[] t1 = shape.getVertices();
        float[] t2 = shape2.getVertices();
        shapeDrawer.setColor(color);
        shapeDrawer.filledTriangle(t1[0], t1[1], t1[2], t1[3], t1[4], t1[5]);
        shapeDrawer.filledTriangle(t2[0], t2[1], t2[2], t2[3], t2[4], t2[5]);

        // draw the animated paths from spawn point to bases
        Vector2 prev = null;
        if (!pathsToBase.isEmpty()) {
            for (var arr : pathsToBase) {
                for (int i = 0; i < arr.size(); i++) {
                    float angle = MathUtils.map(0, arr.size(), 0.1f, PI2, i);
                    var v = arr.get(i);
                    PATH_COLOR.a = (float) Math.tan((angle + offset) % PI2);
                    PATH_COLOR.clamp();
                    if (prev != null) {
                        shapeDrawer.setColor(PATH_COLOR);
                        shapeDrawer.line(prev, v);
                    }
                    prev = v;
                }
            }
        }
        offset = ((offset - 0.04f) % PI2); // should be using delta from render... ?

        // debug
//        Color c = new Color(0, 1, 1, 1);
//        shapeDrawer.setColor(c);
//        graphNodes.forEach(n -> shapeDrawer.filledCircle(n.x, n.y, 2f, c));
    }

    public void findPathToBase(Level level) {
        SquareTiledLevelGraph graph = new SquareTiledLevelGraph(level);
        graph.computeConnections();
        graph.getNodes().keySet().addAll(graphNodes);
        PathFinder<Vector2> pathFinder = new IndexedAStarPathFinder<>(graph);
        level.getAllPlayerBases().forEach(base -> {
            GraphPath<Vector2> pathFound = new DefaultGraphPath<>();
            boolean found = pathFinder.searchNodePath(
                    graph.findNodeClosestTo(pos),
                    graph.findNodeClosestTo(base.getPos()),
                    Vector2::dst,
                    pathFound);
            if (found) {
                ArrayList<Vector2> path = new ArrayList<>();
                pathFound.forEach(path::add);
                pathsToBase.add(path);
            }
        });
    }
}
