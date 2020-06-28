package com.marvelousbob.common.model.entities;

import com.marvelousbob.common.state.LocalGameState;
import lombok.Data;
import space.earlygrey.shapedrawer.ShapeDrawer;

@Data
public class GameWorld implements Drawable {

    private LocalGameState localGameState;
    private Level level;


    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        level.drawMe(shapeDrawer);
        localGameState.drawMe(shapeDrawer);
    }
}
