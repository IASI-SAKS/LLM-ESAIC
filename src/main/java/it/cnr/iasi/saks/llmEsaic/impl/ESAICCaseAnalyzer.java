package it.cnr.iasi.saks.llmEsaic.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import it.cnr.iasi.saks.llmEsaic.AbstractESAICPrompter;
import it.cnr.iasi.saks.llmEsaic.prompts.ESAICPrompts;

public class ESAICCaseAnalyzer extends AbstractESAICPrompter{

	private static final String ESAIC_CASES_PATH = ESAIC_PATH + "/Cases";
    private static final String CASE_TAG = "_°°_";
	private static final String CASE_FILENAME = "case"+CASE_TAG+".txt";

	private String caseID;
	private String caseDescription;
	private String caseSuggestion;
	
	public ESAICCaseAnalyzer () {
		if (! this.areRecomandationsProcessable()) {
			this.loadESAIC();
		}
		
		String response = this.chatLLM(ESAICPrompts.getCaseLoadingHeader());
		
		this.caseID = AbstractESAICPrompter.UNSET;
		this.caseDescription = AbstractESAICPrompter.UNSET;
		this.caseSuggestion = AbstractESAICPrompter.UNSET;
	}

	public boolean isLoadedCaseValid () {
		boolean caseIDFlag = (this.caseID != null) && (!this.caseID.isBlank()) && (!this.caseID.isEmpty()) && (!this.caseID.equalsIgnoreCase(AbstractESAICPrompter.UNSET));
		boolean caseDescriptionFlag = (this.caseDescription != null) && (!this.caseDescription.isBlank()) && (!this.caseDescription.isEmpty()) && (!this.caseDescription.equalsIgnoreCase(AbstractESAICPrompter.UNSET));
		
		return (caseIDFlag && caseDescriptionFlag);
	}
	
	public boolean isSuggestionValid () {
		boolean caseSuggestionFlag = (this.caseSuggestion != null) && (!this.caseSuggestion.isBlank()) && (!this.caseSuggestion.isEmpty()) && (!this.caseSuggestion.equalsIgnoreCase(AbstractESAICPrompter.UNSET));
		
		return (caseSuggestionFlag && this.isLoadedCaseValid());
	}

	public void loadCase (String caseID) {
		this.caseID = caseID;
		this.caseSuggestion = AbstractESAICPrompter.UNSET;
		
		InputStream fis = null;
		String caseFileName = ESAIC_CASES_PATH + "/" + CASE_FILENAME.replace(CASE_TAG, caseID);
		try { 
//			ClassLoader classLoader = getClass().getClassLoader();
//			fis = classLoader.getResourceAsStream(caseFileName);
			fis = new FileInputStream(caseFileName);
		} catch (FileNotFoundException e1) {
				System.err.println("Trying to load as-a-stream the resource: " + caseFileName);
				ClassLoader classLoader = getClass().getClassLoader();
				fis = classLoader.getResourceAsStream(caseFileName);
			}

		String data = AbstractESAICPrompter.UNSET;
		
		try {
			data = IOUtils.toString(fis, "UTF-8");
		} catch (Exception e) {
//			e.printStackTrace();
			System.err.println("Keeping UNSET the contents from the case: " + caseFileName);
			data = UNSET; 
		}
		
		this.caseDescription = data;		
	}
	
	public String fetchSuggestion() {
		return this.caseSuggestion;
	}
	
	public void processCase() {
		if (this.isLoadedCaseValid()) {	
			String prompt = ESAICPrompts.getBeginOfInput() + "\n" + this.caseDescription + "\n" + ESAICPrompts.getEndOfInput();
			
			this.caseSuggestion = this.queryLLM(prompt);			
		} else {
			this.caseSuggestion = AbstractESAICPrompter.UNSET;
		}
	}
	
}
