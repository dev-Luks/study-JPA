package com.example.advanced.entity.board;

import com.example.advanced.audit.Period;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter @Setter @ToString
@Table(name = "TBL_REPLY")
public class Reply extends Period {
    @Id @GeneratedValue
    private Long id;
    @NotNull private String replyContent;
}





















