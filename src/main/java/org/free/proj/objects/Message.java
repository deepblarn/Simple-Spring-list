package org.free.proj.objects;

public class Message {

    private String className;
    private String header;
    private String message;

    public Message(String className, String header, String message) {
        this.className = className;
        this.header = header;
        this.message = message;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
