package com.ghostchat;

class PrivateMessage {
    private String to;
    private String message;
    private String sent;

    public PrivateMessage(String to, String message, String sent){
        this.to = to;
        this.message = message;
        this.sent = sent;
    }

    public PrivateMessage(){

    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "To: " + this.to + "Message: " + this.message + "Sent: " + this.sent;
    }
}
