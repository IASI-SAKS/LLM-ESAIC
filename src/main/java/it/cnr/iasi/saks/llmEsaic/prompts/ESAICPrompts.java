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

import java.util.List;

public class ESAICPrompts {

	private final static String ACK = "--OK--";

	private final static String NACK = "THERE WAS A PROBLEM";
	
	private final static String BEGIN_OF_INPUT = "--BEGIN--";
	
	private final static String END_OF_INPUT = "--END--";

	private final static String BEGIN_OF_ANSWER = "--ANSWER-BEGIN--";
	private final static String END_OF_ANSWER = "--ANSWER-END--";

	private static final String CASE_ID = "CASE ID";
	private static final String CASE_CONTEXT = "CONTEXT";
	private static final String CASE_SUBJECT = "SUBJECT";
	private static final String CASE_ANAMNESIS = "ANAMNESIS";
	private static final String CASE_EXAMINATIONS = "EXAMINATIONS";
	private static final String CASE_ANESTHESIOLOGICAL_NOTE = "ANESTHESIOLOGICAL NOTE";
	private static final String CASE_DECISION = "DECISION";
	private static final String CASE_DECISION_EXPLAINATION = "EXPLAINATION";
	private static final String CASE_PROCEED = "PROCEED";
	private static final String CASE_POSTPONE = "POSTPONE";


	private final static String PREAMBLE = "Please, avoid to invent facts. "
			+ "Before answering always take in consideration all the my previous prompts first, and then your own knowledge.";

	private final static String RECOMMENDATION_LOADING_HEADER = "I am loading a list of Recommandations by ESAIC"
			+ " (European Society of Anaesthesiology and Intensive Care). "
			+ "Each Recommendation in introduced by its identifier: RX.Y, where X and Y are numbers. "
//			+ "Each Recommendation has a severity index, for example a severity index G for the Recommendation RX.Y is reported as: (RX.Y GRADE: G) . "
			+ "Each Recommendation has a severity index, for example a severity index G for the Recommendation RX.Y is reported as: \"(RX.Y has severity index: G)\" . "
			+ "When the process is over, I will prompt you with: \"" + END_OF_INPUT + "\". ";

	private final static String RECOMMENDATION_LOADING_ACK_HEADER = "I am loading a list of Recommandations by ESAIC"
			+ " (European Society of Anaesthesiology and Intensive Care). "
			+ "Each Recommendation in introduced by its identifier: RX.Y, where X and Y are numbers. "
//			+ "Each Recommendation has a severity index, for example a severity index G for the Recommendation RX.Y is reported as: (RX.Y GRADE: G) . "
			+ "Each Recommendation has a severity index, for example a severity index G for the Recommendation RX.Y is reported as: \"(RX.Y has severity index: G)\" . "
			+ "After each Recommendation is processed your answer has to be only: \""+ ACK + "\". "
			+ "When the process is over, I will prompt you with: \"" + END_OF_INPUT + "\". ";

//	private final static String GRADE_DESCRIPTIONS_HEADER = "As you know, an ESAIC Recommendations RX.Y has a severity index expressed as: (RX.Y GRADE: G) "
	private final static String GRADE_DESCRIPTIONS_HEADER = "As you know, an ESAIC Recommendations RX.Y has a severity index expressed as: \"(RX.Y has severity index: G)\" "
			+ "Overall: Strong recommendations should be take in consideration, while Weak recommendations can be take in considerations. "
			+ "In addition: High-quality evidences impliy confidence in the estimation of benefits and risks; moderate-quality evidence can impact the confidence in the estimation of benefits and risks; low-quality evidences may lead to uncertain effects; very low-quality evidences leads to high uncertainty in the estimates, but benefits may outweigh risks and burdens. "
			+ "These are the meaning of the possible values associated with the severity index G:"
			+ "1A means: Strong recommendation, high-quality evidence; "
			+ "1B means: Strong recommendation, moderate-quality evidence; "
			+ "1C means: Strong recommendation, low-quality evidence; "
			+ "2A means: Weak recommendation, high-quality evidence; "
			+ "2B means: Weak recommendation, moderate-quality evidence; "
			+ "2C means: Weak recommendation, low-quality evidence; "
			+ "3A means: Very low-quality evidence. ";
//			+ "CPS means: Very low-quality evidence. ";

	private final static String CASE_LOADING_HEADER = "As professional expert in Anaesthesiology and Intensive Care "
			+ "you are requested to advise if a surgery on a specific clinical case should be either performed or postponed. "
// Begin CoT			
// primus			
//			+ "Be aware that these cases concern patients that really need to undergo surgery; thus as professional expert in Anaesthesiology and Intensive Care, you should advise to proceed with surgery. "
//			+ "However, you shall advise to postpone surgery in the following scenarios: "
//			+ "the case specifically violates any strong ESAIC Recommendation (i.e., 1A, 1B, or 1C severity indexes), "
// //			+ "the case specifically violates any strong ESAIC Recommendation, "
//			+ "or changing any treatment will improve the conditions of the patient, "
//			+ "or further examinations could improve the overall understanding of the case, "
//			+ "or the case conflicts with many weak ESAIC Recommendations. "
// **********************************
//// bis			
//			+ "These cases concern patients that need to undergo surgery, thus you should advise to proceed with surgery if benefits outweigh risks and burdens. "
////			+ "However, if the case specifically violates any strong ESAIC Recommendation (i.e., 1A, 1B, or 1C severity indexes), you shall advise to postpone surgery. "
//			+ "However, if the case specifically violates any strong ESAIC Recommendation, you shall advise to postpone surgery. "
//			+ "Also, if changing any treatment will improve the conditions of the patient, you shall advise to postpone surgery. "
//			+ "In addition, if further examinations could improve the overall understanding of the case, you shall advise to postpone surgery. "
//			+ "Finally, if the case conflicts with many weak ESAIC Recommendations, you shall advise to postpone surgery. "
// **********************************
// ter			
//			+ "These cases concern patients that need to undergo surgery thus, you should notice major motivations in order to postpone the surgery. "
////			+ "However, if the case specifically violates any strong ESAIC Recommendation (i.e., 1A, 1B, or 1C severity indexes), you shall advise to postpone surgery. "
//			+ "For example, if the case specifically violates any strong ESAIC Recommendation, you shall advise to postpone surgery. "
//			+ "Also, if changing any treatment will improve the conditions of the patient in the short term, you shall advise to postpone surgery. "
//			+ "In addition, if further examinations could improve the overall understanding of the case, you shall advise to postpone surgery. "
//			+ "Finally, if the case conflicts with many weak ESAIC Recommendations, you shall advise to postpone surgery. "
// **********************************
// quater			
						+ "These cases concern patients that need to undergo surgery thus, you should advice to proceed with the surgey unless you notice major motivations in order to postpone it. "
						+ "Specifically, use the following procedure to evaluate the case: "
						+ "if the case violates any strong ESAIC Recommendation, you shall advise to postpone surgery; "
						+ "if changing any treatment will improve the conditions of the patient in the short term, you should advise to postpone surgery; "
						+ "if further examinations could improve the overall understanding of the case, you should advise to postpone surgery; "
						+ "if the case conflicts with many weak ESAIC Recommendations, you should advise to postpone surgery; "
						+ "if none of the above conditions, you shall advise to proceed with the surgery. "
// End CoT			
			+ "Each clinical case is reported as a whole between the following tags: "
			+ BEGIN_OF_INPUT + ", and " + END_OF_INPUT + ". "
			+ "Each case is structured as follows: "
			+ CASE_ID + ": which reports a unique identifies of the clinical case; " 
			+ CASE_CONTEXT + ":  which reports additional information about the patients or their teraphies; "
			+ CASE_SUBJECT + ": which reports information about the patient; "
			+ CASE_ANAMNESIS + ": which reports anamnesis information about the clinical case; "
			+ CASE_EXAMINATIONS + ": which reports data about objective examinations performed on the patient; "
			+ CASE_ANESTHESIOLOGICAL_NOTE + ": which reports any other information by some Anaesthesiology expert. "
//			+ "Each advise you reply has to be structured as follows: "
			+ "Each advise you reply has to be structured in " + CommonConstants.getAnswerLanguage() +". "
//			+ "Detect the language that is mainly used in the case structure and return each advise accordingly."
			+ "Also, each advise you reply has to be structured as follows: "
			+ CASE_DECISION + ": where you only report either : " + CASE_POSTPONE + ", or " + CASE_PROCEED + ". You report the former if the suggestion is to postpone the surgery, while the latter if the suggestion is to proceed with the surgery."
			+ CASE_DECISION_EXPLAINATION + ": where you explain your suggestion. Note that your explaination has to take into account and explicitly cite several ESAIC Recommendations."
			+ "Each advice you reply has to be retuned between a line containing only the tag: "
			+ BEGIN_OF_ANSWER + ", and a line containing only the tag: " + END_OF_ANSWER + " ."; 

	public static String getAckMessage() {
		return "If this message is clear just reply: \""+ ACK + "\".";
	}
	
	public static String getAckAndNackMessage() {
		return "If these instructions are clear just reply: \""+ ACK + "\", otherwise: " + "\""+ NACK + "\".";
	}

	public static String getPreamble() {
		return PREAMBLE;
	}

	public static String getPreambleWithAck() {
		String message = getPreamble() + " " + getAckMessage();
		return message;
	}

	public static String getGradeDescriptionsHeader() {
		return GRADE_DESCRIPTIONS_HEADER;
	}

	public static String getGradeDescriptionsHeaderWithAck() {
		String message = getGradeDescriptionsHeader() + " " + getAckMessage();
		return message;
	}

	public static String getCaseLoadingHeader() {
		return CASE_LOADING_HEADER;
	}

	public static String getCaseLoadingHeaderWithAckAndNack() {
		String message = getCaseLoadingHeader() + " " + getAckAndNackMessage();
		return message;
	}

	public static String getAck() {
		return ACK;
	}

	public static String getRecommendationLoadingHeader() {
		return RECOMMENDATION_LOADING_HEADER;
	}

	public static String getRecommendationLoadingHeaderWithAck() {
		String message = RECOMMENDATION_LOADING_ACK_HEADER + " " + getAckMessage();
		return message;
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

	public static String getCaseDecision() {
		return CASE_DECISION;
	}

	public static String getCaseDecisionExplaination() {
		return CASE_DECISION_EXPLAINATION;
	}

	public static String getCaseProceed() {
		return CASE_PROCEED;
	}

	public static String getCasePostpone() {
		return CASE_POSTPONE;
	}

	public static String getRequestForResumePrompt(int maxWords, List<String> itemList) {
		String message = "You have to report in one paragraph a resume of the following inputs "
				+ "which are reported below each one between the tags: "
				+ BEGIN_OF_INPUT + ", and " + END_OF_INPUT + ". \n"
//				+ "Your resume paragraph has to be retuned between a line containing only the tag: "
//				+ BEGIN_OF_ANSWER + ", and a line containing only the tag: " + END_OF_ANSWER + " .\n" 
				+ "The lenght of the resume paragraoh should countain around " + maxWords + " words. \n"
				+ "In the resume always keep all the references to identifiers of ESAIC recommendations but do not include their whole formulation.\n";
		for (String item : itemList) {
			message += BEGIN_OF_INPUT + "\n " + item + "\n " + END_OF_INPUT +"\n";  
		}
		return message;
	}
}
