package com.michaelyi.personalwebsite.status;

import com.michaelyi.personalwebsite.util.HttpResponse;

public class StatusResponse extends HttpResponse {
    private Status status;

    public StatusResponse(Status status) {
        this.status = status;
    }

    public StatusResponse() {
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
