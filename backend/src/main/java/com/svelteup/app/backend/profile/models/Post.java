package com.svelteup.app.backend.profile.models;

import com.svelteup.app.backend.modelcontroller.models.usermodels.OwningUserNonPrimaryKeySurrogateEntity;

import javax.persistence.*;
import java.sql.Date;

@Entity()
@Table()
public class Post extends OwningUserNonPrimaryKeySurrogateEntity {
    @Id()
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long postId;
    Date postDate;
    String postBody;
}
