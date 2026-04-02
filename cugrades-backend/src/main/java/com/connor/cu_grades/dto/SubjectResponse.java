package com.connor.cu_grades.dto;

public class SubjectResponse {
    private int id;
    private String code;
    private String name;
    public SubjectResponse(int id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public int getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
}
