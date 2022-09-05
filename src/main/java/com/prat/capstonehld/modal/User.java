package com.prat.capstonehld.modal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.prat.capstonehld.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@Table(name="users",schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="username",nullable = false)
    private String username;
    @Column(name="password",nullable = false)
    private String password;

    @Column(name="bank_id",nullable = false)
    private long bankId;

     @OneToOne(fetch = FetchType.LAZY)
     @JoinColumn(name="account_id",nullable = false)
     @JsonBackReference
     private Account account;

     public User(UserDto userDto,Account account)
     {
//         this.id= userDto.getId();
         this.username=userDto.getUsername();
         this.password=userDto.getPassword();
       this.bankId= Long.parseLong(userDto.getBankId());
//       this.bankId= Long.parseLong("7889b");
         this.account=account;
     }

    public User(String username, String password, long bankId, Account account) {
        this.username = username;
        this.password = password;
        this.bankId = bankId;
        this.account = account;
    }

    public User(String username, String password) {
         this.username=username;
         this.password=password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", bankId=" + bankId +
                '}';
    }


}
