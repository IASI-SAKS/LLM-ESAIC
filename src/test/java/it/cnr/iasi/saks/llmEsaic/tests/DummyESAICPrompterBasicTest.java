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

import org.junit.jupiter.api.Test;

import it.cnr.iasi.saks.llmEsaic.impl.DummyESAICPrompter;
import it.cnr.iasi.saks.llmEsaic.prompts.CommonConstants;
import it.cnr.iasi.saks.llmEsaic.utils.ESAICPrompterTestingFactory;

public class DummyESAICPrompterBasicTest {

    @Test
    public void queryLLMLastPrompt_AnswerNotCorrect() {
    	DummyESAICPrompter prompter = ESAICPrompterTestingFactory.getInstance().getDummyPrompter();
    	
    	int h0 = prompter.currentHistorySize();
    	
    	// PROMPTER must recall this information
    	String prompt = "My name is Guybrush Threepwood. If it's clear what my name is, only answer: \"--OK--\"";		
    	String answer = prompter.chatLLM(prompt);    		
    	assertTrue(answer.contains("--OK--"));
    	int h1 = prompter.currentHistorySize();
    	assertEquals(h0+2, h1);
    	
    	// assuming the PROMPTER gave the wrong answer, repeating the last prompt (which should be unset) and history must be unchanged
    	answer = prompter.queryRecommendationGrade_LastAnswerNotCorrect("I won't call you Guybrush Threepwood");
    	assertTrue(answer.contains(CommonConstants.getUNSET()));
    	int h2 = prompter.currentHistorySize();
    	assertEquals(h1, h2);

    	// PROMPTER should not recall this information, thus history must be unchanged
    	answer = prompter.queryRecommendationGrade(1, 2);    		
    	int h3 = prompter.currentHistorySize();
    	assertEquals(h2, h3);
    	
    	// assuming the PROMPTER gave the wrong answer, repeating the prompt but history must be unchanged 
    	answer = prompter.queryRecommendationGrade_LastAnswerNotCorrect(CommonConstants.getUNSET());
    	int h4 = prompter.currentHistorySize();
    	assertEquals(h3, h4);
    	
    }
}
