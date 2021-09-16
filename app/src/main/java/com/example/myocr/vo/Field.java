package com.example.myocr.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Field {

    @SerializedName("valueType")
    @Expose
    private String valueType;
    @SerializedName("boundingPoly")
    @Expose
    private BoundingPoly boundingPoly;
    @SerializedName("inferText")
    @Expose
    private String inferText;
    @SerializedName("inferConfidence")
    @Expose
    private Double inferConfidence;

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public BoundingPoly getBoundingPoly() {
        return boundingPoly;
    }

    public void setBoundingPoly(BoundingPoly boundingPoly) {
        this.boundingPoly = boundingPoly;
    }

    public String getInferText() {
        return inferText;
    }

    public void setInferText(String inferText) {
        this.inferText = inferText;
    }

    public Double getInferConfidence() {
        return inferConfidence;
    }

    public void setInferConfidence(Double inferConfidence) {
        this.inferConfidence = inferConfidence;
    }

}