package com.example.fabricio.myapplication.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.fabricio.myapplication.Util.AppUtil;
import com.example.fabricio.myapplication.model.persistence.SQliteUserRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tarize on 01/08/2015.
 */
public class User implements Serializable, Parcelable {
    private Integer id;
    private String userName;
    private String password;

    public User(){
        super();
    }

    public User(Parcel in){
        super();

        readToParcel(in);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        if (!userName.equals(user.userName)) return false;
        return password.equals(user.password);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + userName.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }


    public boolean authentication() {
        if (!(AppUtil.stringIsNullOrEmpty(password) && AppUtil.stringIsNullOrEmpty(password))) {
            return SQliteUserRepository.getInstance().search(this).size() > 0;
        }
        return false;
    }

    public void save() {
        SQliteUserRepository.getInstance().save(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id == null ? -1 : id);
        dest.writeString(userName == null ? "" : userName);
        dest.writeString(password == null ? "" : password);
    }

    public void readToParcel(Parcel in) {
        int partialId = in.readInt();
        id = partialId == -1? null : partialId;
        userName = in.readString();
        password = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
