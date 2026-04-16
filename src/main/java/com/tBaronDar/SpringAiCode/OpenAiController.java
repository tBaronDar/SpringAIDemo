package com.tBaronDar.SpringAiCode;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenAiController {
    private OpenAiChatModel chatModel;

    public OpenAiController(OpenAiChatModel openAiChatModel) {
        this.chatModel = openAiChatModel;
    }

    @GetMapping("api/{message}")
    public String test(@PathVariable String message) {

        String response = chatModel.call(message);
        return "Jello" + message;
    }

}
