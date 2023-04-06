package io.github.dbstarll.utils.openai.model.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.dbstarll.utils.openai.model.DateConverter;

import java.util.Date;
import java.util.StringJoiner;

public final class File extends Base {
    private String purpose;
    private String filename;
    private int bytes;
    private String status;
    private Object statusDetails;
    private boolean deleted;

    /**
     * 设置创建时间.
     *
     * @param createdAt 创建时间
     */
    @JsonDeserialize(converter = DateConverter.class)
    public void setCreatedAt(final Date createdAt) {
        setCreated(createdAt);
    }

    /**
     * get purpose.
     *
     * @return purpose
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * set purpose.
     *
     * @param purpose purpose
     */
    public void setPurpose(final String purpose) {
        this.purpose = purpose;
    }

    /**
     * get filename.
     *
     * @return filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * set filename.
     *
     * @param filename filename
     */
    public void setFilename(final String filename) {
        this.filename = filename;
    }

    /**
     * get bytes.
     *
     * @return bytes
     */
    public int getBytes() {
        return bytes;
    }

    /**
     * set bytes.
     *
     * @param bytes bytes
     */
    public void setBytes(final int bytes) {
        this.bytes = bytes;
    }

    /**
     * get status.
     *
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * set status.
     *
     * @param status status
     */
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * get statusDetails.
     *
     * @return statusDetails
     */
    public Object getStatusDetails() {
        return statusDetails;
    }

    /**
     * set statusDetails.
     *
     * @param statusDetails statusDetails
     */
    public void setStatusDetails(final Object statusDetails) {
        this.statusDetails = statusDetails;
    }

    /**
     * get deleted.
     *
     * @return deleted
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * set deleted.
     *
     * @param deleted deleted
     */
    public void setDeleted(final boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", super.toString() + "[", "]")
                .add("purpose='" + getPurpose() + "'")
                .add("filename='" + getFilename() + "'")
                .add("bytes=" + getBytes())
                .add("status='" + getStatus() + "'")
                .add("statusDetails=" + getStatusDetails())
                .add("deleted=" + isDeleted())
                .toString();
    }
}
