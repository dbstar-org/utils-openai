package io.github.dbstarll.utils.openai.model.request;

public final class CompletionRequest extends AbstractCompletionRequest {
    private String prompt;
    private String suffix;
    private Integer logprobs;
    private Boolean echo;
    private Integer bestOf;

    /**
     * The prompt(s) to generate completions for, encoded as a string, array of strings,
     * array of tokens, or array of token arrays.
     *
     * <p>
     * string or array
     * Optional
     * Defaults to &lt;|endoftext|&gt;
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
}
