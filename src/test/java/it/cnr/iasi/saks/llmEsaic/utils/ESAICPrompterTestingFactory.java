package it.cnr.iasi.saks.llmEsaic.utils;

import it.cnr.iasi.saks.llmEsaic.SimpleESAICPrompter;
import it.cnr.iasi.saks.llmEsaic.impl.DummyESAICPrompter;
import it.cnr.iasi.saks.llmEsaic.impl.ESAICCaseAnalyzer;

public class ESAICPrompterTestingFactory {

	private static ESAICPrompterTestingFactory factory = null;

	private DummyESAICPrompter testableESAIC;
	private ESAICCaseAnalyzer caseAnalyzer;
	
	private ESAICPrompterTestingFactory() {
		this.testableESAIC =  new DummyESAICPrompter();
		this.caseAnalyzer = new ESAICCaseAnalyzer(this.testableESAIC);
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

	public DummyESAICPrompter getFreshDummyPrompter() {
		DummyESAICPrompter d = new DummyESAICPrompter();
		return d;
	}

	public ESAICCaseAnalyzer getFreshESAICCaseAnalyzer() {
		DummyESAICPrompter freshESAICPrompter = new DummyESAICPrompter();		

		ESAICCaseAnalyzer a = new ESAICCaseAnalyzer(freshESAICPrompter);
		return a;
	}
}
