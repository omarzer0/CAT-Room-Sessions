package az.zero.cat_room_session.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact")
public class Contact {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String phone;
    private int image;

    @Ignore
    public Contact(int id, String name, String phone, int image) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.image = image;
    }

    public Contact(String name, String phone, int image) {
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
