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

import it.cnr.iasi.saks.llmEsaic.AbstractESAICPrompter;

public class DummyESAICPrompter extends AbstractESAICPrompter {

	public DummyESAICPrompter () {
			this.loadESAIC();
	}
	
	public boolean loadESAIC(int picoNumber, int recNumber) {
		if (! this.areRecomandationsProcessable()) {
			this.loadESAIC();
		}
		return this.isRecomandationLoaded(picoNumber, recNumber);
	}
	
	public String queryRecommendationGrade(int picoNumber, int recNumber) {		
		String response = AbstractESAICPrompter.UNSET;
		
		if (this.isRecomandationLoaded(picoNumber, recNumber))
		{		
			String recID = this.computeRecommendationID(picoNumber, recNumber);
//			String prompt = "Return the grade of the ESAIC recommendation: " + recID + "? Your answer must follow the format: \"GRADE: R\", where R is the rank of " + recID + ". "; 
//			String prompt = "Return the grade of the ESAIC recommendation: " + recID + "? Your answer must start with the keyword: \"GRADE:\""; 
			String prompt = "Which is the severity index of the ESAIC recommendation: " + recID + "? Your answer must start with the keyword: \""+ recID + " GRADE:\""; 
			
			response = this.queryLLM(prompt);
			response = response.replaceFirst(".*GRADE:","").trim();
		}
		
		return response;
	}

	public String informLastAnswerNotCorrect(String originalPrompt, String wrongAnswer) {
		String prompt = "Your last answer was not correct. The prompt was: \"" + originalPrompt + "\".\n Your wrong answer was: \""+ wrongAnswer+ "\".\n Next time try to give a different answer.";
		String response = this.chatLLM(prompt);
		return response;
	}

}
