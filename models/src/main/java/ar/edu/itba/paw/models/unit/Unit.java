package ar.edu.itba.paw.models.unit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_units")
public class Unit {
    @Id
    @Column(name = "unit_id")
    private Long id;

    @Column(name = "unit_name", nullable = false)
    private String unitName;
}
