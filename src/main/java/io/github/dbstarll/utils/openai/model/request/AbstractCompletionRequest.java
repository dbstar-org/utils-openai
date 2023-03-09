package io.github.dbstarll.utils.openai.model.request;

import java.io.Serializable;
import java.util.Map;

public abstract class AbstractCompletionRequest implements Serializable {
    private String model;
    private Float temperature;
    private Float topP;
    private Integer n;
    private Boolean stream;
    private String stop;
    private Integer maxTokens;
    private Float presencePenalty;
    private Float frequencyPenalty;
    private Map<String, Float> logitBias;
    private String user;

    /**
     * ID of the model to use. You can use the List models API to see all of your available models,
     * or see our Model overview for descriptions of them.
     *
     * <p>
     * string
     * Required
     * </p>
     *
     * @return model
     */
    public final String getModel() {
        return model;
    }

    /**
     * set model.
     *
     * @param model model
     */
    public final void setModel(final String model) {
        this.model = model;
    }

    /**
     * What sampling temperature to use, between 0 and 2. Higher values like 0.8 will make the output more random,
     * while lower values like 0.2 will make it more focused and deterministic.
     *
     * <p>
     * We generally recommend altering this or top_p but not both.
     * </p>
     *
     * <p>
     * number
     * Optional
     * Defaults to 1
     * </p>
     *
     * @return temperature
     */
    public final Float getTemperature() {
        return temperature;
    }

    /**
     * set temperature.
     *
     * @param temperature temperature
     */
    public final void setTemperature(final Float temperature) {
        this.temperature = temperature;
    }

    /**
     * An alternative to sampling with temperature, called nucleus sampling,
     * where the model considers the results of the tokens with top_p probability mass.
     * So 0.1 means only the tokens comprising the top 10% probability mass are considered.
     *
     * <p>
     * We generally recommend altering this or temperature but not both.
     * </p>
     *
     * <p>
     * number
     * Optional
     * Defaults to 1
     * </p>
     *
     * @return topP
     */
    public final Float getTopP() {
        return topP;
    }

    /**
     * set topP.
     *
     * @param topP topP
     */
    public final void setTopP(final Float topP) {
        this.topP = topP;
    }

    /**
     * How many completions to generate for each prompt.
     *
     * <p>
     * integer
     * Optional
     * Defaults to 1
     * </p>
     *
     * @return n
     */
    public final Integer getN() {
        return n;
    }

    /**
     * set n.
     *
     * @param n n
     */
    public final void setN(final Integer n) {
        this.n = n;
    }

    /**
     * Whether to stream back partial progress. If set, tokens will be sent as data-only server-sent
     * events as they become available, with the stream terminated by a data: [DONE] message.
     *
     * <p>
     * boolean
     * Optional
     * Defaults to false
     * </p>
     *
     * @return stream
     */
    public final Boolean getStream() {
        return stream;
    }

    /**
     * set stream.
     *
     * @param stream stream
     */
    public final void setStream(final Boolean stream) {
        this.stream = stream;
    }

    /**
     * Up to 4 sequences where the API will stop generating further tokens.
     * The returned text will not contain the stop sequence.
     *
     * <p>
     * string or array
     * Optional
     * Defaults to null
     * </p>
     *
     * @return stop
     */
    public final String getStop() {
        return stop;
    }

    /**
     * set stop.
     *
     * @param stop stop
     */
    public final void setStop(final String stop) {
        this.stop = stop;
    }

    /**
     * The maximum number of tokens to generate in the completion.
     *
     * <p>
     * integer
     * Optional
     * Defaults to 16
     * </p>
     *
     * @return maxTokens
     */
    public final Integer getMaxTokens() {
        return maxTokens;
    }

    /**
     * set maxTokens.
     *
     * @param maxTokens maxTokens
     */
    public final void setMaxTokens(final Integer maxTokens) {
        this.maxTokens = maxTokens;
    }

    /**
     * Number between -2.0 and 2.0. Positive values penalize new tokens based on whether they appear
     * in the text so far, increasing the model's likelihood to talk about new topics.
     *
     * <p>
     * number
     * Optional
     * Defaults to 0
     * </p>
     *
     * @return presencePenalty
     */
    public final Float getPresencePenalty() {
        return presencePenalty;
    }

    /**
     * set presencePenalty.
     *
     * @param presencePenalty presencePenalty
     */
    public final void setPresencePenalty(final Float presencePenalty) {
        this.presencePenalty = presencePenalty;
    }

    /**
     * Number between -2.0 and 2.0. Positive values penalize new tokens based on their existing frequency
     * in the text so far, decreasing the model's likelihood to repeat the same line verbatim.
     *
     * <p>
     * number
     * Optional
     * Defaults to 0
     * </p>
     *
     * @return frequencyPenalty
     */
    public final Float getFrequencyPenalty() {
        return frequencyPenalty;
    }

    /**
     * set frequencyPenalty.
     *
     * @param frequencyPenalty frequencyPenalty
     */
    public final void setFrequencyPenalty(final Float frequencyPenalty) {
        this.frequencyPenalty = frequencyPenalty;
    }

    /**
     * Modify the likelihood of specified tokens appearing in the completion.
     *
     * <p>
     * Accepts a json object that maps tokens (specified by their token ID in the GPT tokenizer) to an
     * associated bias value from -100 to 100. You can use this tokenizer tool (which works for both
     * GPT-2 and GPT-3) to convert text to token IDs. Mathematically, the bias is added to the logits
     * generated by the model prior to sampling. The exact effect will vary per model, but values
     * between -1 and 1 should decrease or increase likelihood of selection; values like -100 or 100
     * should result in a ban or exclusive selection of the relevant token.
     * </p>
     *
     * <p>
     * As an example, you can pass {"50256": -100} to prevent the &lt;|endoftext|&gt; token from being generated.
     * </p>
     *
     * <p>
     * map
     * Optional
     * Defaults to null
     * </p>
     *
     * @return logitBias
     */
    public final Map<String, Float> getLogitBias() {
        return logitBias;
    }

    /**
     * set logitBias.
     *
     * @param logitBias logitBias
     */
    public final void setLogitBias(final Map<String, Float> logitBias) {
        this.logitBias = logitBias;
    }

    /**
     * A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse.
     *
     * <p>
     * string
     * Optional
     * </p>
     *
     * @return user
     */
    public final String getUser() {
        return user;
    }

    /**
     * set user.
     *
     * @param user user
     */
    public final void setUser(final String user) {
        this.user = user;
    }
}
