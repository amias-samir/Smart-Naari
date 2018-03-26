
package com.nepal.naxa.smartnaari.services.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Properties_ {

    @SerializedName("DISTRICT")
    @Expose
    private String dISTRICT;
    @SerializedName("centroid_1")
    @Expose
    private Double centroid1;
    @SerializedName("centroid_2")
    @Expose
    private Double centroid2;
    @SerializedName("x_min")
    @Expose
    private Double xMin;
    @SerializedName("x_max")
    @Expose
    private Double xMax;
    @SerializedName("y_min")
    @Expose
    private Double yMin;
    @SerializedName("y_max")
    @Expose
    private Double yMax;

    public String getDISTRICT() {
        return dISTRICT;
    }

    public void setDISTRICT(String dISTRICT) {
        this.dISTRICT = dISTRICT;
    }

    public Double getCentroid1() {
        return centroid1;
    }

    public void setCentroid1(Double centroid1) {
        this.centroid1 = centroid1;
    }

    public Double getCentroid2() {
        return centroid2;
    }

    public void setCentroid2(Double centroid2) {
        this.centroid2 = centroid2;
    }

    public Double getXMin() {
        return xMin;
    }

    public void setXMin(Double xMin) {
        this.xMin = xMin;
    }

    public Double getXMax() {
        return xMax;
    }

    public void setXMax(Double xMax) {
        this.xMax = xMax;
    }

    public Double getYMin() {
        return yMin;
    }

    public void setYMin(Double yMin) {
        this.yMin = yMin;
    }

    public Double getYMax() {
        return yMax;
    }

    public void setYMax(Double yMax) {
        this.yMax = yMax;
    }

}
