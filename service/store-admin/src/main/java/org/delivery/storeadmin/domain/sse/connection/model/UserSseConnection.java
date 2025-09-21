package org.delivery.storeadmin.domain.sse.connection.model;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.delivery.storeadmin.domain.sse.connection.ifs.ConnectionPoolIfs;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Getter
@ToString
@EqualsAndHashCode
public class UserSseConnection {

    // 외부에서 값을 변경할 수 없도록 캡슐화 하기 위해 final 사용
    private final String uniqueKey;

    private final SseEmitter sseEmitter;

    private final ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs;

    private final ObjectMapper objectMapper;

    private UserSseConnection(
            String uniqueKey,
            ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs,
            ObjectMapper objectMapper
    ) {
        this.uniqueKey = uniqueKey; // key 초기화

        this.sseEmitter = new SseEmitter(60 * 1000L); // sse 초기화

        // callback 초기화
        this.connectionPoolIfs = connectionPoolIfs;

        // objectMapper 초기화
        this.objectMapper = objectMapper;

        // on completion
        this.sseEmitter.onCompletion(() -> {
            // connection poll remove
            this.connectionPoolIfs.onCompletionCallback(this);
        });

        // on timeout
        this.sseEmitter.onTimeout(() -> {
            this.sseEmitter.complete();
        });



        //onopen 메세지
        this.sendMessage("onopen", "connect");

    }

    public static UserSseConnection connect(
            String uniqueKey,
            ConnectionPoolIfs<String, UserSseConnection> connectionPoolIfs,
            ObjectMapper objectMapper
    ) {
        return new UserSseConnection(uniqueKey, connectionPoolIfs,objectMapper);
    }

    public void sendMessage(String eventName, Object data) {
        try {
            var json = this.objectMapper.writeValueAsString(data);
            var event = SseEmitter.event()
                    .name(eventName)
                    .data(json);
            this.sseEmitter.send(event);
        }catch (IOException e){
            this.sseEmitter.completeWithError(e);
        }

    }

    public void sendMessage(Object data) {

        try {
            var json = this.objectMapper.writeValueAsString(data);
            var event = SseEmitter.event()
                    .data(json);

            this.sseEmitter.send(event);
        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }
    }
}
