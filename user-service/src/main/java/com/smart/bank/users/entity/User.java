package com.smart.bank.users.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Table(name = "user_tbl")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false,unique = true)
    private String email;

    private String phone;

    private String address;

    private String password;

    private String username;

    private String role;

    private String status;

    private String country;



}
