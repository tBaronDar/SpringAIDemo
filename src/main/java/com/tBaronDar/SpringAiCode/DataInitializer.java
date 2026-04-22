package com.tBaronDar.SpringAiCode;

import org.springframework.ai.reader.TextReader;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {
  // created as bean in AppConfig(simiral-ish to repo)
  @Autowired
  private VectorStore vectorStore;

  // to run before any post requests are used
  // but not to create a new obj on each req
  @PostConstruct
  public void initData() {
    TextReader textReader = new TextReader(new ClassPathResource("product_details.txt"));

    // to get the documents
    List<Document> docs = textReader.get();

    // create splitter
    TextSplitter splitter = TokenTextSplitter.builder()
        .withChunkSize(200)
        .withMinChunkSizeChars(50)
        .build();

    // split documents
    List<Document> documents = splitter.split(docs);

    vectorStore.add(documents);
  }

}
