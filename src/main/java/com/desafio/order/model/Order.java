package com.desafio.order.model;

import com.desafio.order.enuns.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String paymentId;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus;
    private double totalAmount;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
