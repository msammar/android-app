package se.lublin.mumla.model.location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Device {

    @SerializedName("battery")
    @Expose
    private Integer battery;

    @SerializedName("long")
    @Expose
    private Double _long;

    @SerializedName("lat")
    @Expose
    private Double lat;

    public Integer getBattery() {
        return battery;
    }

    public void setBattery(Integer battery) {
        this.battery = battery;
    }

    public Double getLong() {
        return _long;
    }

    public void setLong(Double _long) {
        this._long = _long;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
