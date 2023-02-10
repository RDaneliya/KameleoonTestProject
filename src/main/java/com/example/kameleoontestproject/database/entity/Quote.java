package com.example.kameleoontestproject.database.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "quotes")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @ToString.Exclude
    @OneToMany(mappedBy = "quote", orphanRemoval = true)
    @OrderBy("timestamp DESC")
    private List<Score> scores = new ArrayList<>();

    @Column(name = "text", nullable = false, unique = true)
    private String text;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @PrePersist
    private void onCreate() {
        timestamp = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quote quote)) {
            return false;
        }
        return id.equals(quote.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
