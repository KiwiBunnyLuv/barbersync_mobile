package com.example.barbersync;
import android.os.Parcel;
public class Client{
    private int id;
    private String name;
    private String email;
    private String address;
    private String city;
    private String province;
    private String postal_code;
    private String phone;

    // Constructors
    public Client() {
    }

    public Client(int id, String name, String email, String address, String city,
                  String province, String postal_code, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.city = city;
        this.province = province;
        this.postal_code = postal_code;
        this.phone = phone;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static Client CLIENT_COURANT = null;

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", postal_code='" + postal_code + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
    protected Client(Parcel in) {
        id = in.readInt();
        name = in.readString();
        email = in.readString();
        address = in.readString();
        city = in.readString();
        province = in.readString();
        postal_code = in.readString();
        phone = in.readString();
    }

}