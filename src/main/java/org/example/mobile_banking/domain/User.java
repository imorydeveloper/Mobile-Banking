package org.example.mobile_banking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    @Column(unique = true,nullable = false)
    private String uuid;
    @Column(name = "national_card_id",length = 20,nullable = false,unique = true)
    private String nationalCardId;
    @Column(length = 100,nullable = false)
    private String name;
    @Column(length = 10,nullable = false)
    private String gender;
    @Column(name = "phone_number",length = 10,nullable = false,unique = true)
    private String phoneNumber;
    @Column(length = 40,nullable = false,unique = true)
    private String email;
    @Column(length = 4,nullable = false)
    private Integer pin;
    @Column(nullable = false)
    private String password;
    @Column(name = "profile_image")
    private String profileImage;
    @Column(name = "student_id_card",length = 20,unique = true)
    private String studentIdCard;
    private String street;
    private String village;
    private String commune;
    private String district;
    private String city;
    private String position;
    @Column(name = "employee_type")
    private String employeeType;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "monthly_income_range")
    private BigDecimal monthlyIncomeRange;
    @Column(name = "main_source_of_income")
    private String mainSourceOfIncome;
    @Column(name = "is_blocked")
    private Boolean isBlocked;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @Column(name = "is_verified")
    private Boolean isVerified;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<Role> roles;
}
