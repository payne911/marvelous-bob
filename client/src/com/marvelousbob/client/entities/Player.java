package com.marvelousbob.client.entities;

import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import com.marvelousbob.common.network.register.dto.UUID;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Container of the visual representation of a {@link PlayerDto}. Call {@link
 * #updateFromDto(PlayerDto)} to update it accordingly. todo: introduce "PlayerContainer" in
 * `GameScreen` and call `updateFromDto` accordingly
 */
@Slf4j
public class Player extends Stack implements Identifiable {

    @Getter
    private final UUID uuid;

    public Player(PlayerDto playerDto) {
        this.uuid = playerDto.getUuid();
        updateFromDto(playerDto);

//        // UI stuff should run on UI thread
//        Gdx.app.postRunnable(() -> {
//
//            setSize(playerDto.getSize());
//
//            Image image = new Image(new Texture("kenney/platformercharacters/PNG/Adventurer/Poses/adventurer_stand.png"));
//            image.setScaling(Scaling.fillY);
//            image.setColor(GameConstant.playerColors.get(playerDto.getColorIndex()));
//            addActor(image);
//
//            Label label = new Label(String.valueOf(playerDto.getColorIndex()), skin);
//            label.setTouchable(Touchable.disabled);
//            label.setOrigin(Align.center);
//            label.setAlignment(Align.center);
//            label.setFontScale(2);
//            label.setColor(GameConstant.playerColors.get(playerDto.getColorIndex()));
//            addActor(label);
//
//            setDebug(true);
//        });
//        MyGame.stage.addActor(this);
    }

    public void updateFromDto(PlayerDto input) {
        setSize(input.getSize(), input.getSize());
        setX(input.getCurrX());
        setY(input.getCurrY());
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public boolean isEquals(UUID other) {
        return uuid.equals(other);
    }
}
