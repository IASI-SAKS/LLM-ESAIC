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
package it.cnr.iasi.saks.llm.impl;

import it.cnr.iasi.saks.llm.AbstractPrompter;

public class DummyPrompter extends AbstractPrompter {

	public DummyPrompter () {
	}
	
	public String oneShotInteraction() {
		String prompt = "In very few words, who is Guybrush Threepwood?";
		
		String answer = this.queryLLM(prompt);
		
		return answer;
	}
	
}
