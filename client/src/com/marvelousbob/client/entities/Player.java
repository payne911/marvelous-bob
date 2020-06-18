package com.marvelousbob.client.entities;

import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import lombok.experimental.Delegate;

public class Player extends Stack implements Identifiable {

    @Delegate
    private PlayerDto dto;

    public Player(PlayerDto playerDto) {
        this.dto = playerDto;
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

    public void updateFromDto(PlayerDto playerDto) {
//        this.dto = playerDto;
    }
}
