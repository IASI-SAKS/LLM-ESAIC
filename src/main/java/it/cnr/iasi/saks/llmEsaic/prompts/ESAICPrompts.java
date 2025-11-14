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
package it.cnr.iasi.saks.llmEsaic.prompts;

public class ESAICPrompts {

	private final static String ACK = "--OK--";

	private final static String NACK = "THERE WAS A PROBLEM";
	
	private final static String END_OF_INPUT = "--END--";
	
	private final static String RECOMMENDATION_LOADING_HEADER = "I am loading a list of Recommandations by ESAIC"
			+ " (European Society of Anaesthesiology and Intensive Care). "
			+ "Each Recommendation in introduced by its ID in the form: RX.Y, where X and Y are numbers. "
			+ "After each Recommendation is processed your answer has to be only: \""+ ACK + "\". "
			+ "When the process is over, I will prompt you with: \"" + END_OF_INPUT + "\". "
			+ "If this message is clear just reply: \""+ ACK + "\".";

	public static String getAck() {
		return ACK;
	}

	public static String getRecommendationLoadingHeader() {
		return RECOMMENDATION_LOADING_HEADER;
	}

	public static String getNack() {
		return NACK;
	}

	public static String getEndOfInput() {
		return END_OF_INPUT;
	}

}
