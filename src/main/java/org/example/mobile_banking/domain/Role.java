package org.example.mobile_banking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Authorities> authorities = new ArrayList<>();

    @ManyToMany(mappedBy = "roles")
    private List<User> users;


    @Override
    public String getAuthority() {
        return name;
    }
}
