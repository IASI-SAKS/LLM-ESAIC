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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.opentest4j.AssertionFailedError;

import it.cnr.iasi.saks.llmEsaic.impl.DummyESAICPrompter;
import it.cnr.iasi.saks.llmEsaic.utils.ESAICPrompterTestingFactory;

public class LoadingESAICTest {

	private static DummyESAICPrompter prompter;
	
	@BeforeAll
    public static void setup() {
//    	prompter = new DummyESAICPrompter();
    	prompter = ESAICPrompterTestingFactory.getInstance().getDummyPrompter();
    }
	
    @ParameterizedTest
    @CsvSource({"1,1", "1,2", "1,3", "1,4", "10,1", "10,2", "11,1", "12,10", "12,11", "12,1", "12,2", "12,3", "12,4", "12,5", "12,6", "12,7", "12,8", "12,9", "2,1", "2,2", "3,1", "3,2", "3,3", "3,4", "3,5", "4,1", "4,2", "5,1", "5,2", "5,3", "6,1", "6,2", "6,3", "6,4", "6,5", "6,6", "7,1", "7,2", "8,1", "8,2", "8,3", "8,4", "8,5", "8,6", "8,7", "8,8", "8,9", "9,1", "9,2", "9,3"})
//    @CsvSource({"1,1"})
    public void loadESAICTest(int picoNumber, int recNumber) {
    	
    	assertTrue(prompter.loadESAIC(picoNumber, recNumber));
    }

    @ParameterizedTest
    @CsvSource({"1,5", "10,3", "11,2", "12,12", "2,3", "3,6", "4,3", "5,4", "6,7", "7,3", "8,10", "9,4", "144,33", "144,-33", "-144,33", "-144,-33",})
    public void loadWrongESAICTest(int picoNumber, int recNumber) {
    	System.err.println("Processing ESAIC Recommendation: " + picoNumber + ", " + recNumber + " ...");
    	boolean isLoaded = prompter.loadESAIC(picoNumber, recNumber);
    	System.err.println("done ESAIC Recommendation: " + picoNumber + ", " + recNumber);
    	
    	assertFalse("Pico: " + picoNumber +", Rec: " + recNumber, isLoaded);
    }


    @ParameterizedTest
//    @CsvSource({"1,1,1C", "1,2,1B", "1,3,CPS", "1,4,CPS", "10,1,2B", "10,2,2C", "11,1,1C", "12,10,CPS", "12,11,CPS", "12,1,CPS", "12,2,CPS", "12,3,CPS", "12,4,CPS", "12,5,CPS", "12,6,CPS", "12,7,CPS", "12,8,CPS", "12,9,CPS", "2,1,CPS", "2,2,CPS", "3,1,2C", "3,2,2C", "3,3,1A", "3,4,1C", "3,5,1C", "4,1,2B", "4,2,2B", "5,1,1C", "5,2,1C", "5,3,CPS", "6,1,1C", "6,2,1A", "6,3,2C", "6,4,1C", "6,5,CPS", "6,6,1C", "7,1,1C", "7,2,2C", "8,1,2C", "8,2,1C", "8,3,1A", "8,4,2C", "8,5,2C", "8,6,2C", "8,7,2C", "8,8,2B", "8,9,1C", "9,1,1C", "9,2,1C", "9,3,1C"})
    @CsvSource({"1,1,1C", "1,2,1B", "1,3,3A", "1,4,3A", "10,1,2B", "10,2,2C", "11,1,1C", "12,10,3A", "12,11,3A", "12,1,3A", "12,2,3A", "12,3,3A", "12,4,3A", "12,5,3A", "12,6,3A", "12,7,3A", "12,8,3A", "12,9,3A", "2,1,3A", "2,2,3A", "3,1,2C", "3,2,2C", "3,3,1A", "3,4,1C", "3,5,1C", "4,1,2B", "4,2,2B", "5,1,1C", "5,2,1C", "5,3,3A", "6,1,1C", "6,2,1A", "6,3,2C", "6,4,1C", "6,5,3A", "6,6,1C", "7,1,1C", "7,2,2C", "8,1,2C", "8,2,1C", "8,3,1A", "8,4,2C", "8,5,2C", "8,6,2C", "8,7,2C", "8,8,2B", "8,9,1C", "9,1,1C", "9,2,1C", "9,3,1C"})
//    @CsvSource({"1,1,GRADE: 1C"})
    public void correctESAICGradesTest(int picoNumber, int recNumber, String expectedGrade) {
    	int max_iterations = 5;
    	int counter = 0;
    	boolean completed = false;
    	
		System.err.println("Processing ESAIC Recommendation: " + picoNumber + ", " + recNumber + " ...");
    	while (! completed) {
    		String response = prompter.queryRecommendationGrade(picoNumber, recNumber);
    		try {
    			assertEquals(expectedGrade, response, "Pico: " + picoNumber +", Rec: " + recNumber );
    			completed = true;
    		} catch (AssertionFailedError e) {
    			counter ++;
    			System.err.println("Failed processing ESAIC Recommendation: " + picoNumber + ", " + recNumber + ". Tentative " + counter + " of " + max_iterations);
    			if (counter >= max_iterations) {    				
    				throw e;
    			}
    		}	
    	}	
		System.err.println("done ESAIC Recommendation: " + picoNumber + ", " + recNumber);
    }

  
//    @ParameterizedTest
////    @CsvSource({"1,1,1C", "1,2,1B", "1,3,CPS", "1,4,CPS", "10,1,2B", "10,2,2C", "11,1,1C", "12,10,CPS", "12,11,CPS", "12,1,CPS", "12,2,CPS", "12,3,CPS", "12,4,CPS", "12,5,CPS", "12,6,CPS", "12,7,CPS", "12,8,CPS", "12,9,CPS", "2,1,CPS", "2,2,CPS", "3,1,2C", "3,2,2C", "3,3,1A", "3,4,1C", "3,5,1C", "4,1,2B", "4,2,2B", "5,1,1C", "5,2,1C", "5,3,CPS", "6,1,1C", "6,2,1A", "6,3,2C", "6,4,1C", "6,5,CPS", "6,6,1C", "7,1,1C", "7,2,2C", "8,1,2C", "8,2,1C", "8,3,1A", "8,4,2C", "8,5,2C", "8,6,2C", "8,7,2C", "8,8,2B", "8,9,1C", "9,1,1C", "9,2,1C", "9,3,1C"})
//    @CsvSource({"2,1,CPS", "3,2,2C", "5,3,CPS", "7,2,2C"})
//  public void correctESAICGradesMultipleTest(int picoNumber, int recNumber, String expectedGrade) {
//  	int iterationCounter = 0;
//  	final int maxIterations = 5;
//  	boolean checkPassed = false;
//  	
//  	String response=null;
//  	
//  	while ((! checkPassed) && (iterationCounter <= maxIterations)) {
//  		iterationCounter++;
//  		try {
//  			response = prompter.queryRecommendationGrade(picoNumber, recNumber);
//  			assertEquals(expectedGrade, response);
//  			checkPassed = true;
//  		} catch (AssertionFailedError e) {  			
//  			response = prompter.informLastAnswerNotCorrect("Which is the severity index of the ESAIC recommendation R"+picoNumber+"."+recNumber+" ?", response);
//  		}
//  	}
//
//  	System.err.println("Number of attempts Executed: " + iterationCounter);
//  	assertEquals(expectedGrade, response);
//  }
}
