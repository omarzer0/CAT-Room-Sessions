package az.zero.cat_room_session.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "contact")
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    @SerializedName("phone_number")
    private String phone;
    private String image;

    public Contact(String name, String phone, String image) {
        this.name = name;
        this.phone = phone;
        this.image = image;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
