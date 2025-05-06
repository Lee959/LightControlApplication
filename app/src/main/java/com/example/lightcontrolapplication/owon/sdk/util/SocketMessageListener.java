package com.example.lightcontrolapplication.owon.sdk.util;

public interface SocketMessageListener {
    void getMessage(int commandID, Object bean);
}
