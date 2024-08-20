package com.ak.rnd.turnstilestatemachine.controller;

import com.ak.rnd.turnstilestatemachine.model.TurnstileEvent;
import com.ak.rnd.turnstilestatemachine.model.TurnstileState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/turnstile/v1")
public class TurnstileController {
    @Autowired
    private StateMachine<TurnstileState, TurnstileEvent> stateMachine;

    @GetMapping
    public ResponseEntity<TurnstileState> state() {
        return ResponseEntity.ok(stateMachine.getState().getId());
    }
}
