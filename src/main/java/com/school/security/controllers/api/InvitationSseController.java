package com.school.security.controllers.api;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/invitation")
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.1.133:3000/"})
public class InvitationSseController {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping
    public SseEmitter streamInvitationCount() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError((ex) -> emitters.remove(emitter));

        return emitter;
    }

    public void sendInvitationUpdate(Long count) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name("invitation-count").data(count));
            } catch (IOException e) {
                emitter.complete();
                emitters.remove(emitter);
            }
        }
    }

    public void notifyInvitationList(Object invitationList) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name("invitation-list").data(invitationList));
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
    }
}
