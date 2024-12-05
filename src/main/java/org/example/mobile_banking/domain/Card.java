package org.example.mobile_banking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 12, nullable = false,unique = true)
    private String number;
    @Column(length = 100, nullable = false)
    private String holder;
    @Column(nullable = false)
    private Integer cvv;
    @Column(name = "issue_at",nullable = false)
    private LocalDate issueAt;
    @Column(name = "expired_at",nullable = false)
    private LocalDate expiredAt;
    @Column(name = "is_deleted",nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    private CardType cardType;

    @OneToOne(mappedBy = "card")
    private Account account;

}
