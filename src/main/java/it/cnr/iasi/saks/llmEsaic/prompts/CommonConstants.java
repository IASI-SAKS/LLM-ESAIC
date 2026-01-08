package it.cnr.iasi.saks.llmEsaic.prompts;

public class CommonConstants {
	
	protected static final String ANSWER_LANGUAGE = "Italian";

	protected static final String UNSET = "THIS ITEM HAS NOT BEEN SET";

	protected static final String ESAIC_PATH = "src/main/resources/ESAIC";

	private static final int N_REPETITIONS_PER_SINGLE_PROMPT = 5;

    public static String getUNSET() {
		return UNSET;
	}

	public static String getESAICDefaultPath() {
		return ESAIC_PATH;
	}

	public static int getRepetitionsPerSinglePrompt() {
		return N_REPETITIONS_PER_SINGLE_PROMPT;
	}

	public static String getAnswerLanguage() {
		return ANSWER_LANGUAGE;
	}
}
