package com.tuxpoli.common.domain;

public interface EventBus {

    void publish(Object message, String to);
}
