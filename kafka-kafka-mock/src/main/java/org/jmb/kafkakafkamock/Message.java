package org.jmb.kafkakafkamock;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Message {

    @JsonProperty("specversion")
    private String specVersion = "1.0";
    private String type = "org.jmb.kafkakafkamock";
    private String source = "http://example.com";
    private String id;
    private String time;
    @JsonProperty("contenttype")
    private String contentType;
    @JsonProperty("data")
    private MessageDetails data;

    public String getSpecVersion() {
        return specVersion;
    }
    public void setSpecVersion(String specVersion) {
        this.specVersion = specVersion;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public MessageDetails getData() {
        return data;
    }
    public void setData(MessageDetails data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return "Message [specVersion=" + specVersion + ", type=" + type + ", source=" + source + ", id=" + id
                + ", time=" + time + ", contentType=" + contentType + ", data=" + data + "]";
    }
}

@RegisterForReflection
class MessageDetails {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageDetails [message=" + message + "]";
    }
}
