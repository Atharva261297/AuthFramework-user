package com.atharva.auth.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(UserId.class)
@Table(name = "data", schema = "user_data")
public class UserModel {

    @Id
    private String id;

    @Id
    private String projectId;

    @Column
    private String data;

}
