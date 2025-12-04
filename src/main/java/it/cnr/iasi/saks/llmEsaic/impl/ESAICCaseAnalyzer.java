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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import it.cnr.iasi.saks.llmEsaic.AbstractESAICPrompter;
import it.cnr.iasi.saks.llmEsaic.prompts.CommonConstants;
import it.cnr.iasi.saks.llmEsaic.prompts.ESAICPrompts;

public class ESAICCaseAnalyzer {

	private AbstractESAICPrompter esaicPrompter;
	
    protected static final String ESAIC_PATH = CommonConstants.getESAICDefaultPath();

	private static final String ESAIC_CASES_PATH = ESAIC_PATH + "/Cases";
    private static final String CASE_TAG = "_°°_";
	private static final String CASE_FILENAME = "case"+CASE_TAG+".txt";

	protected static final String UNSET = CommonConstants.getUNSET();

	private String caseID;
	private String caseDescription;
	private String caseSuggestion;
	
	public ESAICCaseAnalyzer () {
		this(new AbstractESAICPrompter());
	}

	public ESAICCaseAnalyzer (AbstractESAICPrompter esaicPrompter) {
		this.esaicPrompter = esaicPrompter;
		
		if (! this.esaicPrompter.areRecomandationsProcessable()) {
			this.esaicPrompter.loadESAIC();
		}
		
		String response = this.esaicPrompter.chatLLM(ESAICPrompts.getCaseLoadingHeader());
		
		this.caseID = UNSET;
		this.caseDescription = UNSET;
		this.caseSuggestion = UNSET;
	}

	public boolean isLoadedCaseValid () {
		boolean caseIDFlag = (this.caseID != null) && (!this.caseID.isBlank()) && (!this.caseID.isEmpty()) && (!this.caseID.equalsIgnoreCase(UNSET));
		boolean caseDescriptionFlag = (this.caseDescription != null) && (!this.caseDescription.isBlank()) && (!this.caseDescription.isEmpty()) && (!this.caseDescription.equalsIgnoreCase(UNSET));
		
		return (caseIDFlag && caseDescriptionFlag);
	}
	
	public boolean isSuggestionValid () {
		boolean caseSuggestionFlag = (this.caseSuggestion != null) && (!this.caseSuggestion.isBlank()) && (!this.caseSuggestion.isEmpty()) && (!this.caseSuggestion.equalsIgnoreCase(UNSET));
		
		return (caseSuggestionFlag && this.isLoadedCaseValid());
	}

	public void loadCase (String caseID) {
		this.caseID = caseID;
		this.caseSuggestion = UNSET;
		
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

		String data = UNSET;
		
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
			
			this.caseSuggestion = this.esaicPrompter.queryLLM(prompt);			
		} else {
			this.caseSuggestion = UNSET;
		}
	}
	
}
