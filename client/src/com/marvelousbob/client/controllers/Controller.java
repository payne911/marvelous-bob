package com.marvelousbob.client.controllers;

import static com.marvelousbob.client.MyGame.client;
import static com.marvelousbob.client.MyGame.controller;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.kryonet.Client;
import com.marvelousbob.common.model.MarvelousBobException;
import com.marvelousbob.common.model.entities.GameWorld;
import com.marvelousbob.common.model.entities.dynamic.allies.Player;
import com.marvelousbob.common.network.register.dto.MoveActionDto;
import com.marvelousbob.common.state.GameWorldManager;
import com.marvelousbob.common.state.LocalGameState;
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
    private final GameWorldManager gameWorldManager;

    /**
     * Used for the TCP network communications with the server.
     */
    @Getter
    private final Client kryoClient;


    public Controller(Client kryoClient, GameWorld initGameWorld, UUID selfPlayerUuid) {
        this.kryoClient = kryoClient;
        this.gameWorldManager = new GameWorldManager(initGameWorld);
        this.selfPlayerUuid = selfPlayerUuid;
    }

    public void updateGameState(float delta) {
        gameWorldManager.updateGameState(delta);
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
        self.getDestination().x = destX;
        self.getDestination().y = destY;

        /* Send movement request to server. */
        var moveActionDto = new MoveActionDto();
        moveActionDto.setDestX(destX);
        moveActionDto.setDestY(destY);
        moveActionDto.setSourcePlayerUuid(self.getUuid());
        moveActionDto.stampNow();
        moveActionDto.setSourcePlayerUuid(selfPlayerUuid);
        log.debug("sending MoveActionDto: " + moveActionDto);
        client.getClient().sendTCP(moveActionDto);
        log.debug("After changes, game state is: " + controller.getLocalState());
    }

    /**
     * Origin of screen is assumed at bottom-left.
     */
    public void playerRightClicked(float screenX, float screenY) {
        // todo
    }

    /**
     * Origin of screen is assumed at bottom-left.
     */
    public void playerMouseMoved(float screenX, float screenY) {
        var player = getSelfPlayer();
        float mouseRelativeToPlayerY = screenY - player.getCurrCenterY();
        float mouseRelativeToPlayerX = screenX - player.getCurrCenterX();
        log.info("Input screen (%f , %f) corresponds to relative coords (%f , %f)"
                .formatted(screenX, screenY, mouseRelativeToPlayerX, mouseRelativeToPlayerY));
        player.setMouseAngleRelativeToCenter(
                atan2Degrees360(mouseRelativeToPlayerY, mouseRelativeToPlayerX));

        // todo: send GunPositionDto
    }

    // ==========================================
    //  UTILITY methods

    /**
     * libGDX's {@link MathUtils#atan2(float, float)} is >50% slower.<p> JDK's {@link
     * Math#atan2(double, double)} is >400% slower.<p> Benchmark took in consideration the "+360"
     * and "%360" to normalize.<p> This all comes at the cost of a minor loss of precision which we
     * aren't concerned with.
     *
     * @author TEttinger
     */
    public float atan2Degrees360(final float y, final float x) {
        if (y == 0.0 && x >= 0.0) {
            return 0f;
        }
        final float ax = Math.abs(x), ay = Math.abs(y);
        if (ax < ay) {
            final float a = ax / ay, s = a * a,
                    r = 90f - (((-0.0464964749f * s + 0.15931422f) * s - 0.327622764f) * s * a + a)
                            * 57.29577951308232f;
            return (x < 0f) ? (y < 0f) ? 180f + r : 180f - r : (y < 0f) ? 360f - r : r;
        } else {
            final float a = ay / ax, s = a * a,
                    r = (((-0.0464964749f * s + 0.15931422f) * s - 0.327622764f) * s * a + a)
                            * 57.29577951308232f;
            return (x < 0f) ? (y < 0f) ? 180f + r : 180f - r : (y < 0f) ? 360f - r : r;
        }
    }

    // ==========================================
    //  GETTERS (and shortcuts)

    public GameWorld getGameWorld() {
        return gameWorldManager.getMutableGameWorld();
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
