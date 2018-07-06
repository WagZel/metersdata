package com.waggi.metersdata.data.domain;

import com.waggi.metersdata.Constants;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "meters_data")
public class MetersData implements Serializable {

    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private Long id;

    @Version
    private Long version;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @CreationTimestamp
    private Date dateCreated;

    @Enumerated(EnumType.STRING)
    @NotNull
    private MeterType meterType;

    @Basic(optional = false)
    @NotNull @PositiveOrZero
    private Double data;

    private String description;

    public MetersData() {}

    public MetersData(String meterType, Double data, String description) {
        this.meterType = MeterType.valueOf(meterType);
        this.data = data;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public MeterType getMeterType() {
        return meterType;
    }

    public void setMeterType(MeterType meterType) {
        this.meterType = meterType;
    }

    public Double getData() {
        return data;
    }

    public void setData(Double data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public enum MeterType {
        COLD_WATER,
        HEAT_WATER,
        HEATING,
        ELECTRICITY_DAY,
        ELECTRICITY_NIGHT
    }
}
