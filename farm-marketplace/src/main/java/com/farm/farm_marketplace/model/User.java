package com.farm.farm_marketplace.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;




    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    private String password;
    private Role role; // FARMER or BUYER
    private String location;

}