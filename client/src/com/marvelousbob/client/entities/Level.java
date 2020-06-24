package com.marvelousbob.client.entities;

import com.badlogic.gdx.math.Intersector;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import space.earlygrey.shapedrawer.ShapeDrawer;

@Data
public class Level implements Drawable {

    private Intersector intersector = new Intersector();
    private List<Wall> walls = new ArrayList<>();
    private List<Player> players = new ArrayList<>();

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        walls.forEach(w -> w.drawMe(shapeDrawer));
        players.forEach(p -> p.drawMe(shapeDrawer));
    }
}
