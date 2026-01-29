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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import it.cnr.iasi.saks.llmEsaic.ESAICCaseAnalyzer;
import it.cnr.iasi.saks.llmEsaic.ESAICSmartCaseAnalyzer;
import it.cnr.iasi.saks.llmEsaic.utils.ESAICPrompterTestingFactory;

public class SmartLoadingCaseTest {
	private static ESAICSmartCaseAnalyzer prompter;
	
	@BeforeAll
    public static void setup() {
//    	prompter = new ESAICSmartCaseAnalyzer();
    	prompter = ESAICPrompterTestingFactory.getInstance().getESAICSmartCaseAnalyzer();
    }

    @ParameterizedTest
//  @CsvSource({"A1", "A2", "A3", "A4"})
//  @CsvSource({"B10", "B11", "B12", "B13"})
//  @CsvSource({"B14", "B15", "B16", "B17"})
//  @CsvSource({"B18", "B19", "B1", "B20"})
//  @CsvSource({"B21", "B22", "B23", "B24"})
//  @CsvSource({"B25", "B26", "B27", "B28"})
//  @CsvSource({"B29", "B2", "B30", "B3"})
//  @CsvSource({"B4", "B5", "B6", "B7"})
//  @CsvSource({"B8", "B9"})
//  @CsvSource({"C10", "C11", "C12", "C13"})
//  @CsvSource({"C14", "C15", "C16", "C17"})
//  @CsvSource({"C18", "C19", "C1", "C20"})
//  @CsvSource({"C21", "C22", "C23", "C24"})
//  @CsvSource({"C25", "C26", "C27", "C28"})
//  @CsvSource({"C29", "C2", "C30", "C3"})
//  @CsvSource({"C4", "C5", "C6", "C7"})
//  @CsvSource({"C8", "C9"})
//  @CsvSource({"B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10"})
//  @CsvSource({"B11", "B12", "B13", "B14", "B15", "B16", "B17", "B18", "B19", "B20"})
//  @CsvSource({"B21", "B22", "B23", "B24", "B25", "B26", "B27", "B28", "B29", "B30"})
    @CsvSource({"B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "B11", "B12", "B13", "B14", "B15", "B16", "B17", "B18", "B19", "B20", "B21", "B22", "B23", "B24", "B25", "B26", "B27", "B28", "B29", "B30"})
//  @CsvSource({"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10"})
//  @CsvSource({"C11", "C12", "C13", "C14", "C15", "C16", "C17", "C18", "C19", "C20"})
//  @CsvSource({"C21", "C22", "C23", "C24", "C25", "C26", "C27", "C28", "C29", "C30"})
//  @CsvSource({"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "C11", "C12", "C13", "C14", "C15", "C16", "C17", "C18", "C19", "C20", "C21", "C22", "C23", "C24", "C25", "C26", "C27", "C28", "C29", "C30"})
//    @CsvSource({"A1"})
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
