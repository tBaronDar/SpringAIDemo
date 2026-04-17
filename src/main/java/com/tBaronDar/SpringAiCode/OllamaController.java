package com.tBaronDar.SpringAiCode;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OllamaController {

    private ChatClient chatClient;

    //We use .create() in the constructor when we have multiple
    //models.
    public OllamaController(OllamaChatModel ollamaChatModel) {
        this.chatClient = ChatClient.create(ollamaChatModel);
    }

    @GetMapping
    public org.springframework.http.ResponseEntity<String> test(@PathVariable String message) {

        ChatResponse chatResponse = chatClient
                .prompt(message)
                .call()
                .chatResponse();

        System.out.println(chatResponse.getMetadata().getModel());

        String response = chatResponse
                .getResult()
                .getOutput()
                .getText();

        return ResponseEntity.ok(response);
    }
}
