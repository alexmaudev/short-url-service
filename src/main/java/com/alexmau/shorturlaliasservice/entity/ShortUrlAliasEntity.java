package com.alexmau.shorturlaliasservice.entity;

import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = ShortUrlAliasEntity.TABLE_NAME)
public class ShortUrlAliasEntity {

    protected static final String TABLE_NAME = "shorturl";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    private String alias;

    private String url;

    public ShortUrlAliasEntity(String alias, String url) {
        this.alias = alias;
        this.url = url;
    }
}
