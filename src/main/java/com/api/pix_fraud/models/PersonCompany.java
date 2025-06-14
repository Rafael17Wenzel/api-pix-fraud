package com.api.pix_fraud.models;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "person_company")
public class PersonCompany {

    @Id
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private Person person;

    @Column(nullable = false, unique = true, length = 18)
    private String cnpj;

    @Column(name = "corporate_name", nullable = false, length = 255)
    private String corporateName;

    @Column(name = "company_name", nullable = false, length = 255)
    private String companyName;



    public PersonCompany() {
    }

    public PersonCompany(Person person, String cnpj, String corporateName, String companyName) {
        this.person = person;
        this.cnpj = cnpj;
        this.corporateName = corporateName;
        this.companyName = companyName;
    }



    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Person getPerson() {
        return person; }
    public void setPerson(Person person) {
        this.person = person;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonCompany)) return false;
        PersonCompany that = (PersonCompany) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
