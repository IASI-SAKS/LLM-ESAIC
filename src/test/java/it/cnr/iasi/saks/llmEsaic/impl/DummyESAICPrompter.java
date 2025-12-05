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
package it.cnr.iasi.saks.llmEsaic.impl;

import java.util.ArrayList;
import java.util.List;

import dev.langchain4j.data.message.ChatMessage;
import it.cnr.iasi.saks.llmEsaic.SimpleESAICPrompter;

public class DummyESAICPrompter extends SimpleESAICPrompter {

	private String lastProcessedRecID;
	private List<String> pastWrongAnswers;
	
	public DummyESAICPrompter () {
			this.loadESAIC();
			this.lastProcessedRecID = null;
			this.pastWrongAnswers = null;
	}
	
	public boolean loadESAIC(int picoNumber, int recNumber) {
		if (! this.areRecomandationsProcessable()) {
			this.loadESAIC();
		}
		return this.isRecomandationLoaded(picoNumber, recNumber);
	}
	
	public String queryRecommendationGrade(int picoNumber, int recNumber) {		
		String response = SimpleESAICPrompter.UNSET;
		
		if (this.isRecomandationLoaded(picoNumber, recNumber))
		{		
			String recID = this.computeRecommendationID(picoNumber, recNumber);
			String prompt = "Which is the severity index of the ESAIC recommendation: " + recID + "? Your answer must start with the keyword: \""+ recID + " GRADE:\""; 
			this.lastProcessedRecID = recID;
			this.pastWrongAnswers = new ArrayList<String>();
			
			response = this.queryLLM(prompt);
			response = this.clearResponse(response);
		}
		
		return response;
	}

	public String queryRecommendationGrade_LastAnswerNotCorrect(String wrongAnswer) {
		String response = SimpleESAICPrompter.UNSET;
		
//		if ((this.lastPrompt != null) && (this.lastProcessedRecID != null)) {
		if (this.lastProcessedRecID != null) {
			List<ChatMessage> backupHistory = this.fetchHistory();
			
			this.pastWrongAnswers.add(wrongAnswer);
			String promptHeader = "";
			for (String pastWrongAnswer : this.pastWrongAnswers) {
				promptHeader = promptHeader + this.lastProcessedRecID + " does not have severity index: " + pastWrongAnswer + ".\n";				
			}

			String prompt = promptHeader + "Which is the severity index of the ESAIC recommendation: " + this.lastProcessedRecID
					+ "? Your answer must start with the keyword: \""+ this.lastProcessedRecID + " GRADE:\""; 

			response = this.chatLLM(prompt);
			response = this.clearResponse(response);
			this.configureHistory(backupHistory);
		}
		
		return response;
	}

	public int currentHistorySize() {
		return this.fetchHistory().size();
	}
	
	private String clearResponse(String response) {
		return response.replaceFirst(".*GRADE:","").trim();		
	}
	
}
