package io.github.dbstarll.utils.openai.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Map;

public final class CompletionRequest implements Serializable {
    @JsonProperty(required = true)
    private String model;
    private String prompt;
    private String suffix;
    private Integer maxTokens;
    private Float temperature;
    private Float topP;
    private Integer n;
    private Boolean stream;
    private Integer logprobs;
    private Boolean echo;
    private String stop;
    private Float presencePenalty;
    private Float frequencyPenalty;
    private Integer bestOf;
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
    public String getModel() {
        return model;
    }

    /**
     * set model.
     *
     * @param model model
     */
    public void setModel(final String model) {
        this.model = model;
    }

    /**
     * The prompt(s) to generate completions for, encoded as a string, array of strings,
     * array of tokens, or array of token arrays.
     *
     * <p>
     * string or array
     * Optional
     * Defaults to <|endoftext|>
     * </p>
     *
     * @return prompt
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * set prompt.
     *
     * @param prompt prompt
     */
    public void setPrompt(final String prompt) {
        this.prompt = prompt;
    }

    /**
     * The suffix that comes after a completion of inserted text.
     *
     * <p>
     * string
     * Optional
     * Defaults to null
     * </p>
     *
     * @return suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * set suffix.
     *
     * @param suffix suffix
     */
    public void setSuffix(final String suffix) {
        this.suffix = suffix;
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
    public Integer getMaxTokens() {
        return maxTokens;
    }

    /**
     * set maxTokens.
     *
     * @param maxTokens maxTokens
     */
    public void setMaxTokens(final Integer maxTokens) {
        this.maxTokens = maxTokens;
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
    public Float getTemperature() {
        return temperature;
    }

    /**
     * set temperature.
     *
     * @param temperature temperature
     */
    public void setTemperature(final Float temperature) {
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
    public Float getTopP() {
        return topP;
    }

    /**
     * set topP.
     *
     * @param topP topP
     */
    public void setTopP(final Float topP) {
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
    public Integer getN() {
        return n;
    }

    /**
     * set n.
     *
     * @param n n
     */
    public void setN(final Integer n) {
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
    public Boolean getStream() {
        return stream;
    }

    /**
     * set stream.
     *
     * @param stream stream
     */
    public void setStream(final Boolean stream) {
        this.stream = stream;
    }

    /**
     * Include the log probabilities on the logprobs most likely tokens, as well the chosen tokens.
     * For example, if logprobs is 5, the API will return a list of the 5 most likely tokens.
     * The API will always return the logprob of the sampled token,
     * so there may be up to logprobs+1 elements in the response.
     *
     * <p>
     * The maximum value for logprobs is 5. If you need more than this,
     * please contact us through our Help center and describe your use case.
     * </p>
     *
     * <p>
     * integer
     * Optional
     * Defaults to null
     * </p>
     *
     * @return logprobs
     */
    public Integer getLogprobs() {
        return logprobs;
    }

    /**
     * set logprobs.
     *
     * @param logprobs logprobs
     */
    public void setLogprobs(final Integer logprobs) {
        this.logprobs = logprobs;
    }

    /**
     * Echo back the prompt in addition to the completion.
     *
     * <p>
     * boolean
     * Optional
     * Defaults to false
     * </p>
     *
     * @return echo
     */
    public Boolean getEcho() {
        return echo;
    }

    /**
     * set echo.
     *
     * @param echo echo
     */
    public void setEcho(final Boolean echo) {
        this.echo = echo;
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
    public String getStop() {
        return stop;
    }

    /**
     * set stop.
     *
     * @param stop stop
     */
    public void setStop(final String stop) {
        this.stop = stop;
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
    public Float getPresencePenalty() {
        return presencePenalty;
    }

    /**
     * set presencePenalty.
     *
     * @param presencePenalty presencePenalty
     */
    public void setPresencePenalty(final Float presencePenalty) {
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
    public Float getFrequencyPenalty() {
        return frequencyPenalty;
    }

    /**
     * set frequencyPenalty.
     *
     * @param frequencyPenalty frequencyPenalty
     */
    public void setFrequencyPenalty(final Float frequencyPenalty) {
        this.frequencyPenalty = frequencyPenalty;
    }

    /**
     * Generates best_of completions server-side and returns the "best"
     * (the one with the highest log probability per token). Results cannot be streamed.
     *
     * <p>
     * When used with n, best_of controls the number of candidate completions and n
     * specifies how many to return – best_of must be greater than n.
     * </p>
     *
     * <p>
     * Note: Because this parameter generates many completions, it can quickly consume your token quota.
     * Use carefully and ensure that you have reasonable settings for max_tokens and stop.
     * </p>
     *
     * <p>
     * integer
     * Optional
     * Defaults to 1
     * </p>
     *
     * @return bestOf
     */
    public Integer getBestOf() {
        return bestOf;
    }

    /**
     * set bestOf.
     *
     * @param bestOf bestOf
     */
    public void setBestOf(final Integer bestOf) {
        this.bestOf = bestOf;
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
     * As an example, you can pass {"50256": -100} to prevent the <|endoftext|> token from being generated.
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
    public Map<String, Float> getLogitBias() {
        return logitBias;
    }

    /**
     * set logitBias.
     *
     * @param logitBias logitBias
     */
    public void setLogitBias(final Map<String, Float> logitBias) {
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
    public String getUser() {
        return user;
    }

    /**
     * set user.
     *
     * @param user user
     */
    public void setUser(final String user) {
        this.user = user;
    }
}
