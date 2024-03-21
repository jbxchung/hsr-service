package dev.jbxchung.hsr.dto;

public class ApiResponse<T> {
    private Boolean status;
    private T payload;

    public ApiResponse(Boolean status, T payload) {
        this.status = status;
        this.payload = payload;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
