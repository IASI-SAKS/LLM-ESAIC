package it.cnr.iasi.saks.llmEsaic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.cnr.iasi.saks.llmEsaic.prompts.CommonConstants;
import it.cnr.iasi.saks.llmEsaic.prompts.ESAICPrompts;

public class ESAICSmartCaseAnalyzer extends ESAICCaseAnalyzer {

	private List<Integer> caseProceedKeyList;
	private List<Integer> casePostponeKeyList;
	private Map<Integer, String> caseExplainationMap;

	private String caseSuggestionResume;
	
	private static final int REPETITIONS = CommonConstants.getRepetitionsPerSinglePrompt();
	
	public ESAICSmartCaseAnalyzer () {
		this(new SimpleESAICPrompter());
	}

	public ESAICSmartCaseAnalyzer (SimpleESAICPrompter esaicPrompter) {
		this(esaicPrompter, true);
	}

	public ESAICSmartCaseAnalyzer (SimpleESAICPrompter esaicPrompter, boolean loadCaseStructureDescription) {
		super(esaicPrompter, loadCaseStructureDescription);
		
		this.caseProceedKeyList = new ArrayList<Integer>();
		this.casePostponeKeyList = new ArrayList<Integer>();
		this.caseExplainationMap = new HashMap<Integer, String>();
		
		this.caseSuggestionResume = UNSET;
	}

	@Override
	public void processCase() {
		this.caseProceedKeyList.clear();
		this.casePostponeKeyList.clear();
		this.caseExplainationMap.clear();
		this.caseSuggestionResume = UNSET;
		
		if (this.isLoadedCaseValid()) {
			String suggestion = null;
			int i = 1;
			boolean stopIterations = (i > REPETITIONS); 
			while (! stopIterations) {
//			for (int i = 1; i <= REPETITIONS; i++) {
	   			System.err.println("Processing Case, repetition: " + i + " of " + REPETITIONS + " ...");
				super.processCase();
				suggestion = super.fetchSuggestion();
				
				System.err.println("Current suggestion: \n" + suggestion);

				this.parseDecision(i, suggestion);
				this.parseExplaination(i, suggestion);
	   			System.err.println("... done");
	   			
	   			boolean majorityFlag = (this.casePostponeKeyList.size() > (REPETITIONS/2)) || (this.caseProceedKeyList.size() > (REPETITIONS/2));
	   			if (majorityFlag) {
	   				System.err.println("Consensus has been already reached before all repetitions completed.");
	   			}	
	   			i++;
	   			stopIterations = ((i > REPETITIONS) || (majorityFlag));
			}
			
   			System.err.println("Elaborating consensus on the Case ...");
			List<Integer> consensusOnDecisionKeys = this.computeConsensusOnDecisionKeys();
			suggestion = this.computeConsensusExplaination(consensusOnDecisionKeys);

			this.formatAnswer(suggestion); 			
   			System.err.println("... done");
		}	
	}

	private void formatAnswer(String suggestion) {
		this.caseSuggestionResume = ESAICPrompts.getBeginOfAnswer() + "\n" + ESAICPrompts.getCaseDecision() + ": " ;
		if (this.casePostponeKeyList.size() > this.caseProceedKeyList.size()) {
			this.caseSuggestionResume = this.caseSuggestionResume + ESAICPrompts.getCasePostpone() + "\n";
		} else {
			this.caseSuggestionResume = this.caseSuggestionResume + ESAICPrompts.getCaseProceed() + "\n";				
		}
		this.caseSuggestionResume = this.caseSuggestionResume + ESAICPrompts.getCaseDecisionExplaination() + ": " + suggestion + "\n";
		this.caseSuggestionResume = this.caseSuggestionResume + ESAICPrompts.getEndOfAnswer() + "\n";
	}


	private String computeConsensusExplaination(List<Integer> consensusOnDecisionKeys) {
		int maxWordSize = 0;
		String response = UNSET;
		if (consensusOnDecisionKeys.size() != 0) {
			for (Integer key : consensusOnDecisionKeys) {
				String explaination = this.caseExplainationMap.get(key);
				maxWordSize += explaination.split("\\s").length; 
			}
			maxWordSize = maxWordSize / consensusOnDecisionKeys.size();
			
			String prompt = this.buildPrompt(consensusOnDecisionKeys, maxWordSize);
			response = this.esaicPrompter.queryLLM(prompt);
			response = response.replaceAll("\n", "").replace(ESAICPrompts.getBeginOfAnswer(),"").replace(ESAICPrompts.getEndOfAnswer(),"");

		} 
		
		return response;
	}

	private String buildPrompt(List<Integer> consensusOnDecisionKeys, int maxWordSize) {
		List <String> explainationList = new ArrayList<String>();
		for (int key : consensusOnDecisionKeys) {
			String explaination = this.caseExplainationMap.get(key);
			boolean isExplainationUnset = (explaination == null) || (explaination.isEmpty()) || (explaination.isBlank()) || (explaination.contains(UNSET));
			if (! isExplainationUnset) {
				explainationList.add(explaination);
			}	
		}
		
		String requestForResumePrompt = ESAICPrompts.getRequestForResumePrompt(maxWordSize, explainationList);
		return requestForResumePrompt;
	}

	private List<Integer> computeConsensusOnDecisionKeys() {
		List<Integer> consensusKeyList = new ArrayList<Integer>();
		if (this.casePostponeKeyList.size() > this.caseProceedKeyList.size()) {
			consensusKeyList = this.casePostponeKeyList;
		} else {
			consensusKeyList = this.caseProceedKeyList;
		}
		return consensusKeyList;
	}

	private void parseDecision(int key, String suggestion) {
		String regex1 = ESAICPrompts.getCaseDecisionExplaination() + ".*";
		String regex2 = ".*" + ESAICPrompts.getCaseDecision();
		
		String decision = suggestion.replaceAll(ESAICPrompts.getBeginOfAnswer(),"").replaceAll(ESAICPrompts.getEndOfAnswer(),"").replaceAll("(?m)^[ \t]*\r?\n", "").replaceAll("\n", "");
		decision = decision.replaceFirst(regex1, "").replaceFirst(regex2, "");
		
		if (decision.contains(ESAICPrompts.getCasePostpone())) {
			this.casePostponeKeyList.add(key);
		} else {
			if (decision.contains(ESAICPrompts.getCaseProceed())) {
				this.caseProceedKeyList.add(key);
			}
		}		
	}

	private void parseExplaination(int key, String suggestion) {
		String regex = ".*" + ESAICPrompts.getCaseDecision() + ".*" + ESAICPrompts.getCaseDecisionExplaination();

		String explaination = suggestion.replaceAll(ESAICPrompts.getBeginOfAnswer(),"").replaceAll(ESAICPrompts.getEndOfAnswer(),"").replaceAll("(?m)^[ \t]*\r?\n", "").replaceAll("\n", "");
		explaination = explaination.replaceFirst(regex, "");
		
		this.caseExplainationMap.put(key, explaination);
	}

	@Override
	public String fetchSuggestion() {
		return this.caseSuggestionResume;
	}
}
