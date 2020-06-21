package com.marvelousbob.server.model.actions;

import com.marvelousbob.common.network.register.dto.IndexedMoveActionDto;
import com.marvelousbob.common.network.register.dto.UUID;
import com.marvelousbob.server.model.ServerState;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MoveAction implements Action {

    private final long index;
    @Getter
    @Setter
    private long timestamp;
    private UUID playerId;
    private float deltaX, deltaY;

    public MoveAction(IndexedMoveActionDto dto) {
        this.timestamp = dto.getDto().getTimestamp(); // We trust player timestamp ???
        this.playerId = dto.getDto().getPlayerId();
        this.deltaX = dto.getDto().getDestX();
        this.deltaY = dto.getDto().getDestY();
        this.index = dto.getIndex();
    }

    @Override
    public long getIndex() {
        return index;
    }

    @Override
    public UUID getPlayerId() {
        return playerId;
    }

    @Override
    public void execute(final ServerState serverState, final float delta) {
        serverState.getPlayer(playerId)
                .ifPresent(p -> {
                    log.info("Move action for player %s detected"
                            .formatted(p.getUuid().getStringId()));
                    p.setDestX(deltaX);
                    p.setDestY(deltaY);
                });
    }

}
