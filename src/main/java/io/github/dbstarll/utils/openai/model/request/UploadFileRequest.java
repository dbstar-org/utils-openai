package io.github.dbstarll.utils.openai.model.request;

import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;

import java.io.InputStream;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public final class UploadFileRequest {
    public static final String PURPOSE_FINE_TUNE = "fine-tune";

    private final String filename;
    private final InputStream stream;

    /**
     * 构建UploadFileRequest.
     *
     * @param filename filename
     * @param stream   stream
     */
    public UploadFileRequest(final String filename, final InputStream stream) {
        this.filename = notBlank(filename, "filename is blank");
        this.stream = notNull(stream, "stream not set");
    }

    /**
     * build HttpEntity.
     *
     * @return HttpEntity
     */
    public HttpEntity buildEntity() {
        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("file", stream, ContentType.DEFAULT_BINARY, filename);
        builder.addTextBody("purpose", PURPOSE_FINE_TUNE, ContentType.MULTIPART_FORM_DATA);
        return builder.build();
    }
}
