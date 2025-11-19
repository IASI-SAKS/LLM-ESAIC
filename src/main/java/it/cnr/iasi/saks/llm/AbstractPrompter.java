package it.cnr.iasi.saks.llm;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public abstract class AbstractPrompter {

	private ChatModel llm; 
	protected String lastResponse;
 
	private static final String OLLAMA_BASE_URL = "http://localhost:11434";
    private static final String LLM_NAME = "llama3.2";
    private static final String LLM_VERSION = "latest";
//    private static final double LLM_TEMPERATURE = 0.8;
    private static final double LLM_TEMPERATURE = 0.5;
    private static final int LLM_TIMEOUT = 300;

    private List<ChatMessage> chatMessageHistory;

    public AbstractPrompter () {
		this(OLLAMA_BASE_URL,LLM_NAME,LLM_VERSION);
	}

	public AbstractPrompter (String url, String llmName, String version) {		
		this(OLLAMA_BASE_URL,llmName+":"+version);
	}
	
	public AbstractPrompter (String url, String llmName) {		
	    // Build the ChatLanguageModel
	    this.llm = OllamaChatModel.builder()
		                       .baseUrl(url)
		                       .modelName(llmName)
		                       .temperature(LLM_TEMPERATURE)
		                       .timeout(Duration.ofSeconds(LLM_TIMEOUT))
		                       .build();

	    this.chatMessageHistory = new ArrayList<ChatMessage>();	    
	}

	public AbstractPrompter (ChatModel llm) {
	    this.llm = llm;
	}

	/*
	 * It does not keep in consideration the history at all.
	 */
	protected String simpleQueryLLM(String prompt) {		
		this.lastResponse = this.llm.chat(prompt);
		return this.lastResponse;
	}

	/*
	 * It only considers past history but without adding any further information.
	 */
	protected String queryLLM(String prompt) {		
		UserMessage currentMessage = new UserMessage(prompt);
		AiMessage currentResponse = this.chatLLM(currentMessage);
				
		this.chatMessageHistory.remove(currentMessage);
		this.chatMessageHistory.remove(currentResponse);
		
		this.lastResponse = currentResponse.text(); 		
		return this.lastResponse;		
	}

	/*
	 * It considers past history, and it now is able to recall also new contents.
	 */
	protected String chatLLM(String prompt) {    			
		UserMessage currentMessage = new UserMessage(prompt);
		AiMessage currentResponse = this.chatLLM(currentMessage);
				
		this.lastResponse = currentResponse.text(); 		
		return this.lastResponse;		
	}
	
//	protected String chatLLM(String prompt) {
////		Conceptual example with Java Varargs from the tutorial:		
////	    	UserMessage firstUserMessage = UserMessage.from("Hello, my name is Klaus");
////	    	AiMessage firstAiMessage = model.chat(firstUserMessage).aiMessage(); // Hi Klaus, how can I help you?
////	    	UserMessage secondUserMessage = UserMessage.from("What is my name?");
////	    	AiMessage secondAiMessage = model.chat(firstUserMessage, firstAiMessage, secondUserMessage).aiMessage(); // Klaus    	
//	    			
//			UserMessage currentMessage = new UserMessage(prompt);
//			this.chatMessageHistory.add(currentMessage);
//					
////		This is an example on how to convert a List into Java Varargs. Possibly the invoke to "streams()" can be omitted.
////			locations.stream().toArray(WorldLocation[]::new)
//			AiMessage currentResponse = this.llm.chat(this.chatMessageHistory.stream().toArray(ChatMessage[]::new)).aiMessage();
//			this.chatMessageHistory.add(currentResponse);
//			
//			this.lastResponse = currentResponse.text(); 		
//			return this.lastResponse;		
//		}

	private AiMessage chatLLM(UserMessage currentMessage) {
//		Conceptual example with Java Varargs from the tutorial:		
//	    	UserMessage firstUserMessage = UserMessage.from("Hello, my name is Klaus");
//	    	AiMessage firstAiMessage = model.chat(firstUserMessage).aiMessage(); // Hi Klaus, how can I help you?
//	    	UserMessage secondUserMessage = UserMessage.from("What is my name?");
//	    	AiMessage secondAiMessage = model.chat(firstUserMessage, firstAiMessage, secondUserMessage).aiMessage(); // Klaus    	
	    			
			this.chatMessageHistory.add(currentMessage);
					
//		This is an example on how to convert a List into Java Varargs. Possibly the invoke to "streams()" can be omitted.
//			locations.stream().toArray(WorldLocation[]::new)
			AiMessage currentResponse = this.llm.chat(this.chatMessageHistory.stream().toArray(ChatMessage[]::new)).aiMessage();
			this.chatMessageHistory.add(currentResponse);

			return currentResponse;
		}

	public void cleanHistory() {		
		this.chatMessageHistory.clear();
	}

	public String getLastResponse() {
		return this.lastResponse;
	}

	protected List<ChatMessage> fetchHistory() {
		return this.chatMessageHistory;
	}
}
