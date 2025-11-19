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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import it.cnr.iasi.saks.llmEsaic.impl.DummyESAICPrompter;

public class LoadingESAICTest {

	private static DummyESAICPrompter prompter;
	
	@BeforeAll
    public static void setup() {
    	prompter = new DummyESAICPrompter();
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
    	assertFalse(prompter.loadESAIC(picoNumber, recNumber));
    }
}
