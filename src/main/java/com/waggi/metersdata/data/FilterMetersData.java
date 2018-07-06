package com.waggi.metersdata.data;

import com.waggi.metersdata.data.domain.MetersData;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FilterMetersData {

    private MetersData.MeterType meterType;
    private Date startDate;
    private Date endDate;
    private String username;
    private String sortField = "dateCreated";
    private String order = "desc";

    private static List<String> availableFields = Arrays.asList("dateCreated", "data", "meterType");

    public MetersData.MeterType getMeterType() {
        return meterType;
    }

    public void setMeterType(MetersData.MeterType meterType) {
        this.meterType = meterType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean checkSortField() {
        return availableFields.contains(this.sortField);
    }

    public boolean checkOrder() {
        return this.order.equals("asc") || this.order.equals("desc");
    }
}
