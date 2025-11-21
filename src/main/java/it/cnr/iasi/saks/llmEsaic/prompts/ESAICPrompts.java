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
	
	private final static String BEGIN_OF_INPUT = "--BEGIN--";
	
	private final static String END_OF_INPUT = "--END--";

	private final static String BEGIN_OF_ANSWER = "--ANSWER-BEGIN--";
	private final static String END_OF_ANSWER = "--ANSWER-END--";

	private static final String CASE_ID = "CASE";
	private static final String CASE_CONTEXT = "CONTEXT";
	private static final String CASE_SUBJECT = "SUBJECT";
	private static final String CASE_ANAMNESIS = "ANAMNESIS";
	private static final String CASE_EXAMINATIONS = "EXAMINATIONS";
	private static final String CASE_ANESTHESIOLOGICAL_NOTE = "ANESTHESIOLOGICAL NOTE";
	private static final String CASE_DECISION = "DECISION";
	private static final String CASE_DECISION_EXPLAINATION = "EXPLAINATION";
	private static final String CASE_PROCEED = "PROCEED";
	private static final String CASE_POSTPONE = "POSTPONE";


	private final static String RECOMMENDATION_LOADING_HEADER = "I am loading a list of Recommandations by ESAIC"
			+ " (European Society of Anaesthesiology and Intensive Care). "
			+ "Each Recommendation in introduced by its ID in the form: RX.Y, where X and Y are numbers. "
			+ "Each Recommendation has a severity index which is expressed in the form: (GRADE: R), where R ranks the severity of the Recommendation. "
			+ "After each Recommendation is processed your answer has to be only: \""+ ACK + "\". "
			+ "When the process is over, I will prompt you with: \"" + END_OF_INPUT + "\". "
			+ "If this message is clear just reply: \""+ ACK + "\".";

	private final static String GRADE_DESCRIPTIONS_HEADER = "As you know, ESAIC Recommendations have associated a severity index as: (GRADE: R) "
			+ "These are the meaning of the severity indexes "
			+ "Each Recommendation has a severity index which is expressed in the form: (GRADE: R), where R ranks the severity of the Recommendation. "
			+ "(GRADE: 1A) means: Strong recommendation, high-quality evidence; "
			+ "(GRADE: 1B) means: Strong recommendation, moderate-quality evidence; "
			+ "(GRADE: 1C) means: Strong recommendation, low-quality evidence; "
			+ "(GRADE: 2A) means: Weak recommendation, high-quality evidence; "
			+ "(GRADE: 2B) means: Weak recommendation, moderate-quality evidence; "
			+ "(GRADE: 2C) means: Weak recommendation, low-quality evidence; "
			+ "(GRADE: CPS) means: Very low-quality evidence. "
			+ "Overall: Strong recommendations should be take in consideration, while Weak recommendations can be take in considerations. "
			+ "In addition: High-quality evidences impliy confidence in the estimation of benefits and risks; moderate-quality evidence can impact the confidence in the estimation of benefits and risks; low-quality evidences may lead to uncertain effects; very low-quality evidences leads to high uncertainty in the estimates, but benefits may outweigh risks and burdens. "
			+ "If this message is clear just reply: \""+ ACK + "\".";

	private final static String CASE_LOADING_HEADER = "As professional expert in Anaesthesiology and Intensive Care "
			+ "you are requested to suggest a surgery decision on a specific clinical case. "
			+ "Each clinical case is reported as a whole between the following tags: \n"
			+ BEGIN_OF_INPUT + ", and " + END_OF_INPUT + ". \n"
			+ "Each case is structured as follows: \n"
			+ CASE_ID + ": which reports a unique identifies of the clinical case; \n" 
			+ CASE_CONTEXT + ":  which reports additional information about the patients or their teraphies; \n"
			+ CASE_SUBJECT + ": which reports information about the patient; \n"
			+ CASE_ANAMNESIS + ": which reports anamnesis information about the clinical case; \n"
			+ CASE_EXAMINATIONS + ": which reports data about objective examinations performed on the patient; \n"
			+ CASE_ANESTHESIOLOGICAL_NOTE + ": which reports any other information by some Anaesthesiology expert. \n"
			+ "Each decision has to be retuned between a line containing only the tag: "
			+ BEGIN_OF_ANSWER + ", and a line containing only the tag: " + END_OF_ANSWER + " .\n" 
			+ "Each decision has to be structured as follows: \n"
			+ CASE_DECISION + ": where you only report either : " + CASE_POSTPONE + ", or " + CASE_PROCEED + ". You report the former if the suggestion is to postpone the surgery, while the latter if the suggestion is to proceed with the surgery."
			+ CASE_DECISION_EXPLAINATION + ": where you explain your suggestion. Note that your explaination has to take into account and explicitly cite one or more ESAIC Recommendation.\n"
			+ "If these instructions are clear just reply: \""+ ACK + "\", otherwise: " + "\""+ NACK + "\".";

	public static String getGradeDescriptionsHeader() {
		return GRADE_DESCRIPTIONS_HEADER;
	}

	public static String getCaseLoadingHeader() {
		return CASE_LOADING_HEADER;
	}

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

	public static String getBeginOfInput() {
		return BEGIN_OF_INPUT;
	}

	public static String getBeginOfAnswer() {
		return BEGIN_OF_ANSWER;
	}

	public static String getEndOfAnswer() {
		return END_OF_ANSWER;
	}

}
