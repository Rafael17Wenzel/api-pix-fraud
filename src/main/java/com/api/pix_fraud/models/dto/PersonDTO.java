package com.api.pix_fraud.models.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class PersonDTO {

    private Long id;
    private String email;
    private String phone;
    private boolean active = false;
    private LocalDateTime createdAt;



    public PersonDTO() {
    }

    public PersonDTO(Long id, String email, String phone, boolean active) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.active = active;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonDTO)) return false;
        PersonDTO person = (PersonDTO) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
