package com.tBaronDar.SpringAiCode;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// import io.modelcontextprotocol.spec.McpSchema.Prompt;

@RestController
public class OpenAiController {

  // generally ChatModel has limited functionality
  // so we are going to use chatClient
  // private OpenAiChatModel chatModel;

  private ChatClient chatClient;

  // required for embedding
  @Autowired
  private EmbeddingModel embeddingModel;

  // We use .create() in the constructor when we have multiple
  // models.
  // public OpenAiController(OpenAiChatModel openAiChatModel) {
  // this.chatClient=ChatClient.create(openAiChatModel);
  // }

  // Define a type of memory to use in the advisor
  ChatMemory chatMemory = MessageWindowChatMemory.builder().build();

  // We use builder when we have only one model
  public OpenAiController(ChatClient.Builder builder) {
    this.chatClient = builder
        // add advisors to edit the responses, censor or add memory to conversation
        .defaultAdvisors(MessageChatMemoryAdvisor
            .builder(chatMemory)
            .build())
        .build();
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

  @PostMapping("api/recommend")
  public ResponseEntity<String> recommendMovie(@RequestParam String type, @RequestParam String year,
      @RequestParam String lang) {

    // create string template
    String tempt = """
        I want to watch a {type} movie from the year {year},
        the movie should be in {lang}.
        Suggest specific movie and tell me the length and the cast,
        of the movie.
        response format should be:
        1. Movie name
        2. basic plot
        3. cast
        4. length
        5. IMDB rating
        """;

    // create a prompt template
    PromptTemplate promptTemplate = new PromptTemplate(tempt);
    // use prompt template to create prompt
    // map the req parameter to the string template
    Prompt prompt = promptTemplate.create(Map.of("type", type, "year", year, "lang", lang));

    // pass prompt to chatClient to get response
    ChatResponse chatResponse = chatClient
        .prompt(prompt)
        .call()
        .chatResponse();

    System.out.println(chatResponse.getMetadata().getModel());

    String response = chatResponse
        .getResult()
        .getOutput()
        .getText();

    return ResponseEntity.ok(response);
  }

  @PostMapping("/api/embedding")
  public float[] embedding(@RequestParam String text) {
    embeddingModel.embed(text);
    return null;
  }

}
