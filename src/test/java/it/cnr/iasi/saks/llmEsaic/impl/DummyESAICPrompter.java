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

import java.util.List;

import dev.langchain4j.data.message.ChatMessage;
import it.cnr.iasi.saks.llmEsaic.SimpleESAICPrompter;

public class DummyESAICPrompter extends SimpleESAICPrompter {

	private String lastPrompt;
	
	public DummyESAICPrompter () {
			this.loadESAIC();
			this.lastPrompt = null;
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
//			String prompt = "Return the grade of the ESAIC recommendation: " + recID + "? Your answer must follow the format: \"GRADE: R\", where R is the rank of " + recID + ". "; 
//			String prompt = "Return the grade of the ESAIC recommendation: " + recID + "? Your answer must start with the keyword: \"GRADE:\""; 
			String prompt = "Which is the severity index of the ESAIC recommendation: " + recID + "? Your answer must start with the keyword: \""+ recID + " GRADE:\""; 
			this.lastPrompt = prompt;
			
			response = this.queryLLM(prompt);
			response = this.clearResponse(response);
		}
		
		return response;
	}

	public String queryRecommendationGrade_LastAnswerNotCorrect(String wrongAnswer) {
		String response = SimpleESAICPrompter.UNSET;
		
		if (this.lastPrompt != null) {
			List<ChatMessage> backupHistory = this.fetchHistory();

			String prompt = "Your last answer was not correct: \n " 
					+ " * FORMER PROMPT:" + this.lastPrompt + "\".\n "
					+ " * YOUR WRONG ANSWER: "+ wrongAnswer+ ".\n"
					+ "Try to give a different answer; please replay to this prompt with: ACK.";			
			response = this.chatLLM(prompt);
			response = this.chatLLM(this.lastPrompt);
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
