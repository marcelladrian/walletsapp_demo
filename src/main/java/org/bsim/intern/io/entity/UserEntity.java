package org.bsim.intern.io.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "userTBL")
@SequenceGenerator(name = "seqUSR", initialValue = 100, allocationSize = 10)
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -3426138405356290223L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqUSR")
    private long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)", length = 50)
    private String userName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
