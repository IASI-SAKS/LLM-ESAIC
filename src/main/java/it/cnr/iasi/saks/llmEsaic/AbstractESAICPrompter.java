/* 
 * This file is part of the LLM-ESAIC project.
 * 
 * LLM-ESAIC is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * LLM-ESAIC is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with LLM-ESAIC. If not, see <https://www.gnu.org/licenses/>
 *
 */
package it.cnr.iasi.saks.llmEsaic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import it.cnr.iasi.saks.llmEsaic.prompts.ESAICPrompts;
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

    private static final int TOTAL_PICO = 12;

    private static final String PICO_TAG = "_§§_";
    private static final String REC_TAG = "_çç_";
    private static final String REC_SEPARATOR = "_";
    private static final String CASE_TAG = "_°°_";
    
    private static final String ESAIC_PATH = "src/main/resources/ESAIC";
	private static final String ESAIC_PICO_PATH = ESAIC_PATH + "/PICO" + PICO_TAG;
	private static final String ESAIC_CASE_PATH = ESAIC_PATH + "/Cases";

	private static final String REC_FILENAME = "R"+REC_TAG+".txt";
	private static final String CASE_FILENAME = "case"+CASE_TAG+".txt";

	private static final String UNSET = "THIS ITEM HAS NOT BEEN SET";

    private List<ChatMessage> chatMessageHistory;

    private Map<String, Boolean> loadedRecommendations;

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
	    
	    this.loadedRecommendations = new HashMap<String, Boolean>();
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
		this.loadedRecommendations.clear();
	}

	public String getLastResponse() {
		return this.lastResponse;
	}
	
	protected boolean areRecomandationsProcessable() {
		boolean status = (this.loadedRecommendations != null) && (!this.loadedRecommendations.isEmpty());		
		return status;
	}
	
	protected boolean isRecomandationLoaded(String picoNumber, String recNumber) {
		boolean isLoaded = this.areRecomandationsProcessable();
		if (isLoaded) {
			String recID = this.getRecommendationID(picoNumber, recNumber);
			Boolean status = this.loadedRecommendations.get(recID);
			isLoaded = (status == null) ? false : status;
		}
		
		return isLoaded;
	}

	protected boolean isRecomandationLoaded(int picoNumber, int recNumber) {
		return isRecomandationLoaded(String.valueOf(picoNumber), String.valueOf(recNumber));	
	}

	protected void loadESAIC() {
		this.loadedRecommendations.clear();
		
		String prompt = ESAICPrompts.getRecommendationLoadingHeader();
		String response = this.chatLLM(prompt);
//		boolean headerProcessed = (response.equalsIgnoreCase(ESAICPrompts.getAck()));
		boolean headerProcessed = response.contains(ESAICPrompts.getAck());

		for (int counterPico = 1; (headerProcessed) && (counterPico <= TOTAL_PICO); counterPico++) {
			System.err.println("Processing PICO: " + counterPico);

			boolean isRecommendationUnset = false;
			int counterRec = 0;
			while (! isRecommendationUnset){
				counterRec++;
				String recID = this.getRecommendationID(counterPico, counterRec);
				System.err.println("Processing Recommendation: " + recID);

				String recommendation = this.loadRecommendation(counterPico, counterRec);
				
				isRecommendationUnset = recommendation.equals(UNSET);
				if (! isRecommendationUnset) {
					prompt = recID + ": " + recommendation;
					response = this.chatLLM(prompt);
//					isRecommendationUnset = response.contains(UNSET);
					isRecommendationUnset = !(response.contains(ESAICPrompts.getAck()));
					this.loadedRecommendations.put(recID, ! isRecommendationUnset);
				} else {
					this.loadedRecommendations.put(recID, false);					
				}				
				System.err.println("done with: " + recID);
			}
			System.err.println("done with PICO: " + counterPico);
		}		

		prompt = ESAICPrompts.getEndOfInput();
		response = this.chatLLM(prompt);
	}
	
	private String loadRecommendation(String picoNumber, String recNumber) {
		InputStream fis = null;
		String recID = picoNumber + REC_SEPARATOR + recNumber;
		String recommendationFileName = ESAIC_PICO_PATH.replace(PICO_TAG, picoNumber) + "/" + REC_FILENAME.replace(REC_TAG, recID);
		try { 
//			ClassLoader classLoader = getClass().getClassLoader();
//			fis = classLoader.getResourceAsStream(recommendation);
			fis = new FileInputStream(recommendationFileName);
		} catch (FileNotFoundException e1) {
				System.err.println("Trying to load as-a-stream the resource: " + recommendationFileName);
				ClassLoader classLoader = getClass().getClassLoader();
				fis = classLoader.getResourceAsStream(recommendationFileName);
			}

		String data = UNSET;
		
		try {
			data = IOUtils.toString(fis, "UTF-8");
		} catch (Exception e) {
//			e.printStackTrace();
			System.err.println("Keeping UNSET the contents from the recommendation: " + recommendationFileName);
			data = UNSET; 
		}
		
		return data;
	}

	private String loadRecommendation(int picoNumber, int recNumber) {
		return loadRecommendation(String.valueOf(picoNumber), String.valueOf(recNumber));	
	}
	
	private String getRecommendationID(String picoNumber, String recNumber) {
		return "R" + picoNumber + "." + recNumber;		
	}
	
	private String getRecommendationID(int picoNumber, int recNumber) {
		return getRecommendationID(String.valueOf(picoNumber), String.valueOf(recNumber));
	}
	
		
}
