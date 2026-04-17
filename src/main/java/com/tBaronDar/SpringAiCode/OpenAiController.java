package com.tBaronDar.SpringAiCode;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenAiController {

    //generally ChatModel has limited functionality
    //so we are going to use chatClient
    //private OpenAiChatModel chatModel;

    private ChatClient chatClient;

    public OpenAiController(OpenAiChatModel openAiChatModel) {
        this.chatClient=ChatClient.create(openAiChatModel);
    }

    @GetMapping("api/{message}")
    public ResponseEntity<String> test(@PathVariable String message) {

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
