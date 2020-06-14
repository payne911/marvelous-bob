package com.marvelousbob.server.model;

import com.marvelousbob.common.network.register.dto.PlayerDto;
import com.marvelousbob.common.network.register.dto.UUID;
import com.marvelousbob.server.model.actions.Action;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;

@Data
public class ServerState {
    private boolean hasChanged;
    private PriorityQueue<Action> actions;
    private Map<UUID, PlayerDto> players;

    public ServerState() {
        this.actions = new PriorityQueue<>();
        this.players = new HashMap<>();
    }

    public Optional<PlayerDto> getPlayer(UUID uuid) {
        return Optional.ofNullable(players.get(uuid));
    }

    public void addAction(Action action) {
        actions.add(action);
    }

    private void executeAll() {

    }
}
