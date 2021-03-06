package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/* Klasa nesmije imati konstruktor */
/* Klasa mora imati get i set metode za sve atribute*/
public class Teacher extends Table {
    @Entity(type = "INTEGER", size = 32, primary = true)
    int id;

    @Entity(type="VARCHAR", size=50, isnull = false)
    String firstname;

    @Entity(type = "VARCHAR", size=50, isnull = false)
    String lastname;

    @Entity(type="VARCHAR", size = 150, isnull = false)
    String email;

    @Entity(type = "VARCHAR", size = 256, isnull = false)
    String password;

    @Entity(type="BLOB", size=10000000, isnull = false)
    SerialBlob teacherImage;


    /* Radi prikaza u tablici moramo vratiti od SerialBlob-a sliku */
    public ImageView getTeacherImage() {
        try{
            return new ImageView(new Image(teacherImage.getBinaryStream()));
        } catch (Exception e) {
            return null;
        }

    }

    public void setTeacherImage(SerialBlob teacherImage) {
        this.teacherImage = teacherImage;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Teacher login (String email, String password) throws Exception {
        String sql = "SELECT id FROM Teacher WHERE email=? AND password=?";
        PreparedStatement query = Database.CONNECTION.prepareStatement(sql);
        query.setString(1, email);
        query.setString(2, password);

        ResultSet rs = query.executeQuery();

        if (rs.next()) {
            return (Teacher) Teacher.get(Teacher.class, rs.getInt(1));
        } else {
            return null;
        }

    }
}
