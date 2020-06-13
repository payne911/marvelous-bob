package com.marvelousbob.common.events;

public interface GameEvent extends Runnable {
    EventType getEventType();
}
