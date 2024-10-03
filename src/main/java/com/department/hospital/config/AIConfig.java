package com.department.hospital.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.*;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.core.MongoTemplate;


@Configuration
@Profile("!test")
public class AIConfig {

	@Bean
	public VectorStore mongodbVectorStore(MongoTemplate mongoTemplate, EmbeddingModel embeddingModel) {
		return new MongoDBAtlasVectorStore(mongoTemplate, embeddingModel,
				MongoDBAtlasVectorStore.MongoDBVectorStoreConfig.builder().build(), true);
	}

	@Bean
	public ChatClient chatClient(ChatClient.Builder builder, VectorStore vectorStore){
		return builder.defaultAdvisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults())).build();
	}

}
