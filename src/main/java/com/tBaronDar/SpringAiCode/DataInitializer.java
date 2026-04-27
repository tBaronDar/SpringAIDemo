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
  // it is the implementation of vector db
  @Autowired
  private VectorStore vectorStore;

  // to run before any post requests are used
  // but not to create a new obj on each req
  @PostConstruct
  public void initData() {
    TextReader textReader = new TextReader(new ClassPathResource("product_details.txt"));

    // to get the documents
    List<Document> docsList = textReader.get();

    // create splitter
    // that does the chunking
    TextSplitter splitter = TokenTextSplitter.builder()
        .withChunkSize(500)
        .withMinChunkSizeChars(50)
        .build();

    // split documents
    // list of chuncks
    List<Document> documents = splitter.split(docsList);

    vectorStore.add(documents);
  }

}
