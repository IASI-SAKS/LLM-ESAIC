/* 
 * This file is part of the LLM-ESAIC project.
 * 
 * LLM-ESAIC is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * LLM-PrompterDemo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with LLM-PrompterDemo.  If not, see <https://www.gnu.org/licenses/>
 *
 */
package it.cnr.iasi.saks.llmEsaic;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;

public abstract class AbstractESAICPrompter {
	
	private ChatModel llm; 
	protected String lastResponse;

    private static final String OLLAMA_BASE_URL = "http://localhost:11434";
    private static final String LLM_NAME = "llama3.2";
    private static final String LLM_VERSION = "latest";
    private static final double LLM_TEMPERATURE = 0.8;
    private static final int LLM_TIMEOUT = 300;
    
    private List<ChatMessage> chatMessageHistory;

	public AbstractESAICPrompter () {
		this(OLLAMA_BASE_URL,LLM_NAME,LLM_VERSION);
	}

	public AbstractESAICPrompter (String url, String llmName, String version) {		
		this(OLLAMA_BASE_URL,llmName+":"+version);
	}
	
	public AbstractESAICPrompter (String url, String llmName) {		
	    // Build the ChatLanguageModel
	    this.llm = OllamaChatModel.builder()
		                       .baseUrl(url)
		                       .modelName(llmName)
		                       .temperature(LLM_TEMPERATURE)
		                       .timeout(Duration.ofSeconds(LLM_TIMEOUT))
		                       .build();

	    this.chatMessageHistory = new ArrayList<ChatMessage>();
	}

	public AbstractESAICPrompter (ChatModel llm) {
	    this.llm = llm;
	}
    
	public String queryLLM(String prompt) {		
		this.lastResponse = this.llm.chat(prompt);
		return this.lastResponse;
	}

	public String chatLLM(String prompt) {
//	Conceptual example with Java Varargs from the tutorial:		
//    	UserMessage firstUserMessage = UserMessage.from("Hello, my name is Klaus");
//    	AiMessage firstAiMessage = model.chat(firstUserMessage).aiMessage(); // Hi Klaus, how can I help you?
//    	UserMessage secondUserMessage = UserMessage.from("What is my name?");
//    	AiMessage secondAiMessage = model.chat(firstUserMessage, firstAiMessage, secondUserMessage).aiMessage(); // Klaus    	
    			
		UserMessage currentMessage = new UserMessage(prompt);
		this.chatMessageHistory.add(currentMessage);
				
//	This is an example on how to convert a List into Java Varargs. Possibly the invoke to "streams()" can be omitted.
//		locations.stream().toArray(WorldLocation[]::new)
		AiMessage currentResponse = this.llm.chat(this.chatMessageHistory.stream().toArray(ChatMessage[]::new)).aiMessage();
		this.chatMessageHistory.add(currentResponse);
		
		this.lastResponse = currentResponse.text(); 		
		return this.lastResponse;
		
	}
	
	public void cleanHistory() {		
		this.chatMessageHistory.clear();
	}

	public String getLastResponse() {
		return this.lastResponse;
	}

}
