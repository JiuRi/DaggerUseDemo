package jiuri.com.dagger2demo.ui.fragment.riji;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by user103 on 2017/9/20.
 */
@Entity
public class DateInfo {
    @Id
    private Long id;
    @Property(nameInDb = "date")
    private String date;
    @Property(nameInDb = "title")
    private String title;
    @Property(nameInDb = "body")
    private String body;
    public String getBody() {
        return this.body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 710318829)
    public DateInfo(Long id, String date, String title, String body) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.body = body;
    }
    @Generated(hash = 560100579)
    public DateInfo() {
    }


}
