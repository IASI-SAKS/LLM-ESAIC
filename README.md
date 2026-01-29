# LLM-ESAIC
Interactions with an LLM about ESAIC Guidelines

# CONF
Configure the LLM in [AbstractPrompter.java](src/main/java/it/cnr/iasi/saks/llm/AbstractPrompter.java)

Customize the prompts in [ESAICPrompts.java](src/main/java/it/cnr/iasi/saks/llmEsaic/prompts/ESAICPrompts.java)

Modify the parameters in [SmartLoadingCaseTest.java](src/test/java/it/cnr/iasi/saks/llmEsaic/tests/SmartLoadingCaseTest.java)
# USAGE
    mvn clean test -Dtest=SmartLoadingCaseTest
