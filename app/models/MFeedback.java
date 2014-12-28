package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.sql.Timestamp;
import java.util.List;

/**
 * User: ecsark
 * Date: 10/28/14
 * Time: 22:28 PM
 */

@Entity
public class MFeedback extends Model {

    @Transient
    public static java.util.Date date= new java.util.Date();

    @Id
    public long feedbackId;
    public Long userId;
    public Timestamp createdTime;
    public String content;
    public String email;
    public String contact;
    public Timestamp resolved;

    //public String name;

    public MFeedback() {}

    public void resolve () {
        resolved = new Timestamp(date.getTime());
        save();
    }

    public static MFeedback create (String text, Long userId) {

        MFeedback feedback = new MFeedback();
        feedback.content = text;
        feedback.createdTime = new Timestamp(date.getTime());
        if (userId != null)
            feedback.userId = userId;

        feedback.save();
        return feedback;
    }


    public static Finder<Long, MFeedback> find = new Finder<>(Long.class, MFeedback.class);

    public static List<MFeedback> getAllFeedbacks() {
        return find.all();
    }

    public static void resolve (long feedbackId) {
        find.byId(feedbackId).resolve();
    }

    public static List<MFeedback> getAllUnresolvedFeedback() {
        return find.where().isNull("resolved").findList();
    }
}
