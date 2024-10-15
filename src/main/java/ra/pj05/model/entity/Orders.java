package ra.pj05.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import ra.pj05.constants.OrderStatus;


import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private Date createdAt;
    private String district;
    private String note;
    private String phone;
    private String province;
    private Date receiveAt;
    private String receiveName;
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String streetAddress;
    private Double totalDiscountedPrice;
    private Double totalPrice;
    private Double totalPriceAfterCoupons;
    private String ward;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupons_id")
    private Coupons coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrderDetail> orderDetails;


}
