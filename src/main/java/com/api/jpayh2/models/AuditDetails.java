package com.api.jpayh2.models;

import lombok.Data;

@Data
public class AuditDetails {

    private String createdBy;
    private String roleName;

    public AuditDetails() {
    }

    public AuditDetails(String createdBy, String roleName) {
        this.createdBy = createdBy;
        this.roleName = roleName;
    }

}
