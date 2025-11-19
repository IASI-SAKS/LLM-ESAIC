package it.cnr.iasi.saks.llmEsaic.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import it.cnr.iasi.saks.llmEsaic.impl.ESAICCaseAnalyzer;

public class LoadingCaseTest {
	private static ESAICCaseAnalyzer prompter;
	
	@BeforeAll
    public static void setup() {
    	prompter = new ESAICCaseAnalyzer();
    }

    @ParameterizedTest
    @CsvSource({"A1,true", "Z99,false"})
	public void loadCasesTest(String caseID, boolean expected) {
    	
    	prompter.loadCase(caseID);
    	
    	assertEquals(expected, prompter.isLoadedCaseValid());    
    }
	
    @ParameterizedTest
    @CsvSource({"A1"})
    public void processCasesWithoutAssessingSuggestionsTest(String caseID) {    	
    	prompter.loadCase(caseID);
    	prompter.processCase();
    	String response = prompter.fetchSuggestion();

    	assertTrue(prompter.isSuggestionValid());    
    }
}
