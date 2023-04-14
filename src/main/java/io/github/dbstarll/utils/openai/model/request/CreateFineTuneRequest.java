package io.github.dbstarll.utils.openai.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.dbstarll.utils.openai.model.fragment.HyperParams;

public final class CreateFineTuneRequest extends HyperParams {
    private String trainingFile;
    private String validationFile;
    private String model;
    private Boolean computeClassificationMetrics;
    @JsonProperty("classification_n_classes")
    private Integer classificationNumClasses;
    private String classificationPositiveClass;
    private String suffix;

    /**
     * The ID of an uploaded file that contains training data.
     *
     * <p>
     * string
     * Required
     * </p>
     *
     * @return trainingFile
     */
    public String getTrainingFile() {
        return trainingFile;
    }

    /**
     * set trainingFile.
     *
     * @param trainingFile trainingFile
     */
    public void setTrainingFile(final String trainingFile) {
        this.trainingFile = trainingFile;
    }

    /**
     * The ID of an uploaded file that contains validation data.
     *
     * <p>
     * string
     * Optional
     * </p>
     *
     * @return validationFile
     */
    public String getValidationFile() {
        return validationFile;
    }

    /**
     * set validationFile.
     *
     * @param validationFile validationFile
     */
    public void setValidationFile(final String validationFile) {
        this.validationFile = validationFile;
    }

    /**
     * The name of the base model to fine-tune. You can select one of "ada", "babbage", "curie", "davinci",
     * or a fine-tuned model created after 2022-04-21.
     *
     * <p>
     * string
     * Optional
     * Defaults to curie
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
     * If set, we calculate classification-specific metrics such as accuracy and F-1 score using the
     * validation set at the end of every epoch. These metrics can be viewed in the results file.
     *
     * <p>
     * In order to compute classification metrics, you must provide a validation_file.
     * Additionally, you must specify classification_n_classes for multiclass classification
     * or classification_positive_class for binary classification.
     * </p>
     *
     * <p>
     * boolean
     * Optional
     * Defaults to false
     * </p>
     *
     * @return computeClassificationMetrics
     */
    public Boolean getComputeClassificationMetrics() {
        return computeClassificationMetrics;
    }

    /**
     * set computeClassificationMetrics.
     *
     * @param computeClassificationMetrics computeClassificationMetrics
     */
    public void setComputeClassificationMetrics(final Boolean computeClassificationMetrics) {
        this.computeClassificationMetrics = computeClassificationMetrics;
    }

    /**
     * The number of classes in a classification task.
     *
     * <p>
     * This parameter is required for multiclass classification.
     * </p>
     *
     * <p>
     * integer
     * Optional
     * Defaults to null
     * </p>
     *
     * @return classificationNumClasses
     */
    public Integer getClassificationNumClasses() {
        return classificationNumClasses;
    }

    /**
     * set classificationNumClasses.
     *
     * @param classificationNumClasses classificationNumClasses
     */
    public void setClassificationNumClasses(final Integer classificationNumClasses) {
        this.classificationNumClasses = classificationNumClasses;
    }

    /**
     * The positive class in binary classification.
     *
     * <p>
     * This parameter is needed to generate precision, recall, and F1 metrics when doing binary classification.
     * </p>
     *
     * <p>
     * string
     * Optional
     * Defaults to null
     * </p>
     *
     * @return classificationPositiveClass
     */
    public String getClassificationPositiveClass() {
        return classificationPositiveClass;
    }

    /**
     * set classificationPositiveClass.
     *
     * @param classificationPositiveClass classificationPositiveClass
     */
    public void setClassificationPositiveClass(final String classificationPositiveClass) {
        this.classificationPositiveClass = classificationPositiveClass;
    }

    /**
     * A string of up to 40 characters that will be added to your fine-tuned model name.
     *
     * <p>
     * For example, a suffix of "custom-model-name" would produce a model name like
     * ada:ft-your-org:custom-model-name-2022-02-15-04-21-04.
     * </p>
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
}
