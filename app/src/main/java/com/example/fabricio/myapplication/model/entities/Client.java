package com.example.fabricio.myapplication.model.entities;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.example.fabricio.myapplication.model.persistence.MemoryClientRepository;
import com.example.fabricio.myapplication.model.persistence.SQliteClientRepository;

import java.io.Serializable;
import java.util.List;

public class Client implements Serializable, Parcelable {

    private Integer id;
    private String name;
    private Integer age;
    private String  phone;

    private String zipCode;
    private String typePatio;
    private String patio;
    private String district;
    private String city;
    private String country;
    private Integer number;


    public Client(){
        super();
    }

    public Client(Parcel in){
        super();

        readToParcel(in);
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTypePatio() {
        return typePatio;
    }

    public void setTypePatio(String typePatio) {
        this.typePatio = typePatio;
    }

    public String getPatio() {
        return patio;
    }

    public void setPatio(String patio) {
        this.patio = patio;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (id != null ? !id.equals(client.id) : client.id != null) return false;
        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        if (age != null ? !age.equals(client.age) : client.age != null) return false;
        if (phone != null ? !phone.equals(client.phone) : client.phone != null) return false;
        if (zipCode != null ? !zipCode.equals(client.zipCode) : client.zipCode != null)
            return false;
        if (typePatio != null ? !typePatio.equals(client.typePatio) : client.typePatio != null)
            return false;
        if (patio != null ? !patio.equals(client.patio) : client.patio != null) return false;
        if (district != null ? !district.equals(client.district) : client.district != null)
            return false;
        if (city != null ? !city.equals(client.city) : client.city != null) return false;
        if (country != null ? !country.equals(client.country) : client.country != null)
            return false;
        return !(number != null ? !number.equals(client.number) : client.number != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
        result = 31 * result + (typePatio != null ? typePatio.hashCode() : 0);
        result = 31 * result + (patio != null ? patio.hashCode() : 0);
        result = 31 * result + (district != null ? district.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", typePatio='" + typePatio + '\'' +
                ", patio='" + patio + '\'' +
                ", district='" + district + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", number=" + number +
                '}';
    }

    public void delete() {
        SQliteClientRepository.getInstance().delete(this);
    }

    public void save(){
        SQliteClientRepository.getInstance().save(this);
    }

    public  static List<Client> getAll(){
        return SQliteClientRepository.getInstance().getAll();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id == null ? -1 : id);
        dest.writeString(name == null ? "" : name);
        dest.writeInt(age == null ? -1 : age);
        dest.writeString(phone == null ? "" : phone);

        dest.writeString(zipCode == null? "" : zipCode);
        dest.writeString(typePatio == null? "" : typePatio);
        dest.writeString(patio == null? "" : patio);
        dest.writeString(district == null? "" : district);
        dest.writeString(city == null? "" : city);
        dest.writeString(country == null? "" : country);
        dest.writeInt(number == null? -1 : number);
    }

    public void readToParcel(Parcel in) {
        int partialId = in.readInt();
        id = partialId == -1? null : partialId;
        name = in.readString();
        int partialAge = in.readInt();
        age = partialAge == -1? null : partialAge;
        phone = in.readString();

        zipCode = in.readString();
        typePatio = in.readString();
        patio = in.readString();
        district = in.readString();
        city = in.readString();
        country = in.readString();
        int partialNumber = in.readInt();
        number = partialNumber == -1? null : partialNumber ;
    }

    public static final Parcelable.Creator<Client> CREATOR = new Parcelable.Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel source) {
            return new Client(source);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };
}
