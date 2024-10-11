// Doctor.java
package com.example.demo.dao.doctor;

import com.example.demo.dao.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "doctors")
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_doctor")
    private Long idDoctor;

    @OneToOne
    @JoinColumn(name = "id_user")
    private User user;
    private String specialty;
    private String phone;
    private boolean active = true;
}
