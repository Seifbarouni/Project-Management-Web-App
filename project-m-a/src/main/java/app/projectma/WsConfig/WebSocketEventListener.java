package app.projectma.WsConfig;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class WebSocketEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketEventListener.class);

    @EventListener
    public void handleWebsocketConnectListener(final SessionConnectedEvent event) {
        LOGGER.info("new connection!");
    }

    @EventListener
    public void handleWebsocketDisconnectListener(final SessionConnectedEvent event) {
        LOGGER.info("disconnected!");
    }

}
