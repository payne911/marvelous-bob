package com.marvelousbob.common.events;

public class PlayerConnection implements GameEvent {


    @Override
    public EventType getEventType() {
        return EventType.PLAYER_CONNECTION;
    }

    @Override
    public void run() {

    }
}
