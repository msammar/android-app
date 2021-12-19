package se.lublin.mumla.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Network {

    @SerializedName("server_id")
    @Expose
    private Integer serverId;

    @SerializedName("network_name")
    @Expose
    private String networkName;

    @SerializedName("display_name")
    @Expose
    private String displayName;

    @SerializedName("contact_name")
    @Expose
    private String contactName;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("voip_address")
    @Expose
    private String voipAddress;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("port")
    @Expose
    private Integer port;

    @SerializedName("is_trial")
    @Expose
    private Integer isTrial;

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVoipAddress() {
        return voipAddress;
    }

    public void setVoipAddress(String voipAddress) {
        this.voipAddress = voipAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }


    public Integer getIsTrial() {
        return isTrial;
    }

    public void setIsTrial(Integer isTrial) {
        this.isTrial = isTrial;
    }
}
