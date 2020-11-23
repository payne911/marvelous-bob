package com.marvelousbob.client.controllers;

import static com.marvelousbob.client.MyGame.client;
import static com.marvelousbob.client.MyGame.controller;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import com.marvelousbob.common.model.MarvelousBobException;
import com.marvelousbob.common.model.entities.dynamic.allies.Player;
import com.marvelousbob.common.model.entities.dynamic.allies.RangedPlayer;
import com.marvelousbob.common.network.register.dto.MoveActionDto;
import com.marvelousbob.common.network.register.dto.RangedPlayerAttackDto;
import com.marvelousbob.common.network.register.dto.WeaponFacingDto;
import com.marvelousbob.common.state.GameWorld;
import com.marvelousbob.common.state.LocalGameState;
import com.marvelousbob.common.utils.MovementUtils;
import com.marvelousbob.common.utils.UUID;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


@ToString(callSuper = true)
@Slf4j
public class Controller {

    @Getter
    private final UUID selfPlayerUuid;

    /**
     * Takes care of everything that relates to the Game State logic.
     */
    @Getter
    private final ClientWorldManager clientWorldManager;

    /**
     * Used for the TCP network communications with the server.
     */
    @Getter
    private final Client kryoClient;


    public Controller(Client kryoClient, GameWorld initGameWorld, UUID selfPlayerUuid) {
        this.kryoClient = kryoClient;
        this.clientWorldManager = new ClientWorldManager(initGameWorld);
        this.selfPlayerUuid = selfPlayerUuid;
    }

    public void updateGameState(float delta) {
        clientWorldManager.updateGameState(delta);
    }

    // ==========================================
    //  INPUT controls

    /**
     * Origin of screen is assumed at bottom-left.
     */
    public void playerLeftClicked(float destX, float destY) {
        log.debug("Tapped on (%f,%f)".formatted(destX, destY));
        log.debug("Before changes, game state is: " + controller.getLocalState());

        Player self = getSelfPlayer();

        /* Assume local input will be accepted by server. */
        clientWorldManager.updatePlayerDestination(self, destX, destY);

        /* Send movement request to server. */
        var moveActionDto = new MoveActionDto();
        moveActionDto.setDestX(destX);
        moveActionDto.setDestY(destY);
        moveActionDto.setSourcePlayerUuid(self.getUuid());
        moveActionDto.stampNow();
        moveActionDto.setSourcePlayerUuid(selfPlayerUuid);
        log.debug("sending MoveActionDto: " + moveActionDto);
        client.sendTCP(moveActionDto);
        log.debug("After changes, game state is: " + controller.getLocalState());
    }

    /**
     * Origin of screen is assumed at bottom-left.
     */
    public void playerRightClicked(float screenX, float screenY) {
        getSelfPlayer().attack(new Vector2(screenX, screenY));
        var player = getSelfPlayer();
        // TODO: 2020-07-03 FIX INSTANCE OF!!!     --- OLA
        if (player instanceof RangedPlayer rp) {
            var playerAttackDto = new RangedPlayerAttackDto(
                    player.getCurrCenterPos(),
                    new Vector2(screenX, screenY),
                    rp.getBulletSpeed(),
                    rp.getBulletSize(),
                    selfPlayerUuid);
            log.debug("sending PlayerAttackDto: {}", playerAttackDto.toString());
            client.sendTCP(playerAttackDto);
        }
    }

    /**
     * Origin of screen is assumed at bottom-left.
     */
    public void playerMouseMoved(float screenX, float screenY) {
        var player = getSelfPlayer();
        float mouseRelativeToPlayerY = screenY - player.getCurrCenterY();
        float mouseRelativeToPlayerX = screenX - player.getCurrCenterX();
        float angle = MovementUtils.atan2Degrees360(mouseRelativeToPlayerY, mouseRelativeToPlayerX);
        player.setMouseAngleRelativeToCenter(angle);
        var dto = new WeaponFacingDto(player.getUuid(), angle);
        client.sendTCP(dto);
    }

    // ==========================================
    //  GETTERS (and shortcuts)

    public GameWorld getGameWorld() {
        return clientWorldManager.getMutableGameWorld();
    }

    public LocalGameState getLocalState() {
        return getGameWorld().getLocalGameState();
    }

    public Player getSelfPlayer() {
        return getLocalState().getPlayer(selfPlayerUuid)
                .orElseThrow(() -> new MarvelousBobException(
                        "Illegal State: could not find the PlayerDto associated with yourself (your client)."));
    }
}
