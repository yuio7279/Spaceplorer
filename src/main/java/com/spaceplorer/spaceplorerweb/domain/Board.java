package com.spaceplorer.spaceplorerweb.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    //내용 @Lob = 텍스트제한 X
    @Lob
    private String content;

    @Column(nullable = false)
    private Long views = 0L;

/*    //작성자
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;*/

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;


    public Board(String title, String content,/*User user*/ City city) {
        this.title = title;
        this.content = content;
        //this.user = user;
        this.city = city;
    }

    public void incrementViews(){
        views += 1;
    }
}
