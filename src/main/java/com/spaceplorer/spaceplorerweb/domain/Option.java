package com.spaceplorer.spaceplorerweb.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@NoArgsConstructor
@Table(name = "option")
public abstract class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //옵션 명
    private String optionName;

    //옵션 가격
    private Long cost;

    //옵션 타입
    @Enumerated(EnumType.STRING)
    private OptionType optionType;

    public Option(String optionName, Long cost, OptionType optionType) {
        this.optionName = optionName;
        this.cost = cost;
        this.optionType = optionType;
    }
}
