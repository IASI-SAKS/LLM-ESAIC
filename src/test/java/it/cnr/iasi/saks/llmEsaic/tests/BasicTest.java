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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import it.cnr.iasi.saks.llmEsaic.impl.DummyESAICPrompter;

public class BasicTest {

    @Test
    public void upAndRunningTest() {
    	DummyESAICPrompter prompter = new DummyESAICPrompter();
    	String answer = prompter.oneShotInteraction();
    	assertNotNull(answer);
    }

    @Test
    public void simpleConversationWithoutMemoryTest() {
    	DummyESAICPrompter prompter = new DummyESAICPrompter();
    	
    	String prompt = "My name is Guybrush Threepwood. If it is clear just reply: \"OK\"";		
    	String answer = prompter.queryLLM(prompt);    		
    	assertTrue(answer.contains("OK"));

    	prompt = "What's my name?";		
    	answer = prompter.queryLLM(prompt);
    	assertFalse(answer.contains("Guybrush Threepwood"));
    }

    @Test
    public void simpleConversationTest() {
    	DummyESAICPrompter prompter = new DummyESAICPrompter();
    	
    	String prompt = "My name is Guybrush Threepwood. If it is clear just reply: \"OK\"";		
    	String answer = prompter.chatLLM(prompt);    		
    	assertTrue(answer.contains("OK"));

    	prompt = "What's my name?";		
    	answer = prompter.chatLLM(prompt);
    	assertTrue(answer.contains("Guybrush Threepwood"));
    }
    
}
