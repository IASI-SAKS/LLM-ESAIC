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
package it.cnr.iasi.saks.llm.impl.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import it.cnr.iasi.saks.llm.impl.DummyPrompter;

public class BasicTest {

    @Test
    public void upAndRunningTest() {
    	DummyPrompter prompter = new DummyPrompter();
    	String answer = prompter.oneShotInteraction();
    	assertNotNull(answer);
    }

    @Test
    public void simpleConversation1WithoutMemoryTest() {
    	DummyPrompter prompter = new DummyPrompter();
    	
    	String prompt = "My name is Guybrush Threepwood. If it's clear what my name is, only answer: \"--OK--\"";		
    	String answer = prompter.queryLLM_NoHistory(prompt);    		
    	assertTrue(answer.contains("--OK--"));

    	prompt = "What's my name?";		
    	answer = prompter.queryLLM_NoHistory(prompt);
    	assertFalse(answer.contains("Guybrush Threepwood"));
    }

    @Test
    public void simpleConversation2WithoutMemoryTest() {
    	DummyPrompter prompter = new DummyPrompter();
    	
    	String prompt = "My name is Guybrush Threepwood. If it's clear what my name is, only answer: \"--OK--\"";
    	String answer = prompter.queryLLM(prompt);    		
    	assertTrue(answer.contains("--OK--"));

    	prompt = "What's my name?";		
    	answer = prompter.queryLLM(prompt);
    	assertFalse(answer.contains("Guybrush Threepwood"));
    }

    @Test
    public void simpleConversation3WithoutMemoryTest() {
    	DummyPrompter prompter = new DummyPrompter();
    	
    	String prompt = "My name is Guybrush Threepwood. If it's clear what my name is, only answer: \"--OK--\"";		
    	String answer = prompter.chatLLM(prompt);    		
    	assertTrue(answer.contains("--OK--"));

    	prompt = "What's my name?";		
    	answer = prompter.queryLLM_NoHistory(prompt);
    	assertFalse(answer.contains("Guybrush Threepwood"));
    }

    @Test
    public void simpleConversationWithSomeMemoryTest() {
    	DummyPrompter prompter = new DummyPrompter();
    	
    	String prompt = "My name is Guybrush Threepwood. If it's clear what my name is, only answer: \"--OK--\"";		
    	String answer = prompter.chatLLM(prompt);    		
    	assertTrue(answer.contains("--OK--"));

    	prompt = "What's my name?";		
    	answer = prompter.queryLLM(prompt);
    	assertTrue(answer.contains("Guybrush Threepwood"));
    }

    @Test
    public void simpleConversationTest() {
    	DummyPrompter prompter = new DummyPrompter();
    	
    	String prompt = "My name is Guybrush Threepwood. If it's clear what my name is, only answer: \"--OK--\"";		
    	String answer = prompter.chatLLM(prompt);    		
    	assertTrue(answer.contains("--OK--"));

    	prompt = "What's my name?";		
    	answer = prompter.chatLLM(prompt);
    	assertTrue(answer.contains("Guybrush Threepwood"));
    }
    
    @Test
    public void advancedConversationTest() {
    	DummyPrompter prompter = new DummyPrompter();
    	
    	// PROMPTER must recall this information
    	String prompt = "My name is Guybrush Threepwood. If it's clear what my name is, only answer: \"--OK--\"";		
    	String answer = prompter.chatLLM(prompt);    		
    	assertTrue(answer.contains("--OK--"));

    	// PROMPTER should not recall this information
    	prompt = "Update your information about my name, from now call me LeChuck. If it's clear what my current name is, only answer: \"--OK--\"";		
    	answer = prompter.queryLLM(prompt);    		
    	assertTrue(answer.contains("--OK--"));

    	prompt = "What's my name?";		
    	answer = prompter.chatLLM(prompt);
    	assertFalse(answer.contains("LeChuck"));
    	
    	// PROMPTER should not recall anything
    	prompt = "What's my name? If you ignore it, simply answer: \"--NONE--\"";		
    	answer = prompter.queryLLM_NoHistory(prompt);
    	assertTrue(answer.contains("--NONE--"));

    }
}
