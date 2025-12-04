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
package it.cnr.iasi.saks.llmEsaic.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import it.cnr.iasi.saks.llmEsaic.ESAICCaseAnalyzer;
import it.cnr.iasi.saks.llmEsaic.utils.ESAICPrompterTestingFactory;

public class LoadingCaseTest {
	private static ESAICCaseAnalyzer prompter;
	
	@BeforeAll
    public static void setup() {
//    	prompter = new ESAICCaseAnalyzer();
    	prompter = ESAICPrompterTestingFactory.getInstance().getESAICCaseAnalyzer();
    }

    @ParameterizedTest
    @CsvSource({"A1,true", "Z99,false"})
	public void loadCasesTest(String caseID, boolean expected) {
    	System.err.println("Processing caseID: " + caseID + " ... ");
    	prompter.loadCase(caseID);    	
    	System.err.println("done caseID: " + caseID);
    	
    	assertEquals(expected, prompter.isLoadedCaseValid());    
    }
	
    @ParameterizedTest
    @CsvSource({"A1", "A2", "A3", "A4"})
    public void processCasesWithoutAssessingSuggestionsTest(String caseID) {    	
    	System.err.println("Processing caseID: " + caseID + " ... ");
    	prompter.loadCase(caseID);
    	prompter.processCase();
    	String response = prompter.fetchSuggestion();
    	System.err.println("done caseID: " + caseID);

    	System.err.println("Response of caseID: " + caseID + "\n" + response);

    	assertTrue(prompter.isSuggestionValid());    
    }
}
