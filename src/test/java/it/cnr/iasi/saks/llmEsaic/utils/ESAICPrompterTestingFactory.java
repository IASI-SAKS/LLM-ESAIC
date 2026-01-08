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
package it.cnr.iasi.saks.llmEsaic.utils;

import it.cnr.iasi.saks.llmEsaic.ESAICCaseAnalyzer;
import it.cnr.iasi.saks.llmEsaic.ESAICSmartCaseAnalyzer;
import it.cnr.iasi.saks.llmEsaic.impl.DummyESAICPrompter;

public class ESAICPrompterTestingFactory {

	private static ESAICPrompterTestingFactory factory = null;

	private DummyESAICPrompter testableESAIC;
	private ESAICCaseAnalyzer caseAnalyzer;
	private ESAICSmartCaseAnalyzer smartCaseAnalyzer;
	
	private ESAICPrompterTestingFactory() {
		this.testableESAIC =  new DummyESAICPrompter();
		this.caseAnalyzer = new ESAICCaseAnalyzer(this.testableESAIC);
		this.smartCaseAnalyzer = new ESAICSmartCaseAnalyzer(this.testableESAIC, false);
	}
	
	public synchronized static ESAICPrompterTestingFactory getInstance() {
		if (factory == null) {
			factory = new ESAICPrompterTestingFactory();
		}
		return factory;
	}
	
	public DummyESAICPrompter getDummyPrompter() {
		return this.testableESAIC;
	}

	public ESAICCaseAnalyzer getESAICCaseAnalyzer() {
		return this.caseAnalyzer;
	}

	public ESAICSmartCaseAnalyzer getESAICSmartCaseAnalyzer() {
		return this.smartCaseAnalyzer;
	}

	public DummyESAICPrompter getFreshDummyPrompter() {
		DummyESAICPrompter d = new DummyESAICPrompter();
		return d;
	}

	public ESAICCaseAnalyzer getFreshESAICCaseAnalyzer() {
		DummyESAICPrompter freshESAICPrompter = new DummyESAICPrompter();		

		ESAICCaseAnalyzer a = new ESAICCaseAnalyzer(freshESAICPrompter);
		return a;
	}

	public ESAICCaseAnalyzer getFreshESAICSmartCaseAnalyzer() {
		DummyESAICPrompter freshESAICPrompter = new DummyESAICPrompter();		

		ESAICSmartCaseAnalyzer a = new ESAICSmartCaseAnalyzer(freshESAICPrompter);
		return a;
	}
}
