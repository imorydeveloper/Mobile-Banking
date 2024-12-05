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
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String alias;
    @Column(name = "act_no")
    private String actNo;
    @Column(name = "act_name")
    private String actName;
    private BigDecimal balance;
    @Column(name = "transfer_limit")
    private BigDecimal transferLimit;
    @Column(name = "is_hidden")
    private Boolean isHidden;

    @OneToOne
    private Card card;

    @ManyToOne
    @JoinTable(
            name = "user_accounts",
            joinColumns = @JoinColumn(name = "account_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    )
    private User user;

    @OneToMany(mappedBy = "owner")
    private List<Transaction>transactionOfOwner;

    @ManyToOne
    private AccountType accountType;


}
