package com.marvelousbob.common.model.entities.level;

import static com.badlogic.gdx.math.MathUtils.PI2;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.PathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.marvelousbob.common.ai.SquareTiledLevelGraph;
import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.model.entities.Drawable;
import com.marvelousbob.common.utils.UUID;
import java.util.Set;
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

    private Set<Connection<Vector2>> connections;
    private UUID uuid;
    private Vector2 pos;
    private float hp, maxHp;
    private Polygon shape;
    private Polygon shape2;
    private Color color;
    private Array<Array<Vector2>> pathsToBase;
    private Array<Vector2> gridPoints;
    private float offset;

    public EnemySpawnPoint(UUID uuid, Vector2 pos, Polygon shape, Polygon shape2, Color color) {
        this.uuid = uuid;
        this.pos = pos;
        this.shape = shape;
        this.shape2 = shape2;
        this.color = color;
        this.pathsToBase = new Array<>();
        this.gridPoints = new Array<>();
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
        return starShaped(uuid, center, size, Color.BLUE);
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        float[] t1 = shape.getVertices();
        float[] t2 = shape2.getVertices();
        shapeDrawer.setColor(color);
        shapeDrawer.filledTriangle(t1[0], t1[1], t1[2], t1[3], t1[4], t1[5]);
        shapeDrawer.filledTriangle(t2[0], t2[1], t2[2], t2[3], t2[4], t2[5]);

        // debug
//        connections.forEach(conn -> shapeDrawer.line(conn.getFromNode(), conn.getToNode(), Color.SCARLET));
//        gridPoints.forEach(v -> shapeDrawer.filledCircle(v.x, v.y, 3, Color.RED));
        Vector2 prev = null;
        if (!pathsToBase.isEmpty()) {
            for (var arr : pathsToBase) {
                Color color = Color.CYAN;
                for (int i = 0; i < arr.size; i++) {
                    float angle = MathUtils.map(0, arr.size, 0.1f, PI2, i);
                    var v = arr.get(i);
                    color.a = (float) Math.tan((angle + offset) % PI2);
                    color.clamp();
//                    shapeDrawer.filledCircle(v.x, v.y, 5, Color.CYAN);
                    if (prev != null) {
                        shapeDrawer.line(prev, v, color);
                    }
                    prev = v;
                }
            }
        }
        offset = ((offset - 0.04f) % PI2);
    }

    public void findPathToBase(Level level) {
        SquareTiledLevelGraph graph = new SquareTiledLevelGraph(level);
        graph.computeConnections();
        graph.getNodes().keySet().forEach(v -> gridPoints.add(v));
        PathFinder<Vector2> pathFinder = new IndexedAStarPathFinder<>(graph);
        level.getAllPlayerBases().forEach(base -> {
            GraphPath<Vector2> pathFound = new DefaultGraphPath<>();
            log.debug("searching for path from {} to {}", pos.toString(), base.getPos().toString());

            boolean found = pathFinder.searchNodePath(
                    graph.findNodeClosestTo(pos),
                    graph.findNodeClosestTo(base.getPos()),
                    Vector2::dst2,
                    pathFound);

            log.debug("path finding {}", found ? "SUCCESSFUL!!!!!" : "unsuccessful");
            if (found) {
                log.debug("path found: {} nodes!", pathFound.getCount());
            }
            log.debug("Connections from base {}", graph.getConnections(base.getPos()));
            log.debug("Connections from spawn {}", graph.getConnections(pos));
            Array<Vector2> path = new Array<>();
            pathFound.forEach(path::add);
            pathsToBase.add(path);
        });

    }
}
