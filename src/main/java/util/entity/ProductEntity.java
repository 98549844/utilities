package util.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;


@SuppressWarnings("JpaDataSourceORMInspection")
//@Data
//@EntityListeners(AuditingEntityListener.class)
@Table(name = "product_table")
@Entity
public class ProductEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(strategy = "identity", name = "id")
    private int id;
    @Column
    private String item;
    @Column
    private String itemSn;
    @Column
    private Double price;
    @Column
    private String category;
    @Column
    Integer qty;



    public ProductEntity() {
    }

    public ProductEntity(String item, Double price, String category) {
        this.item = item;
        this.price = price;
        this.category = category;
    }


}
