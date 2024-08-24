package com.vote.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "voteRecord")
public class VotingRecord {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;  // 每條投票記錄的 ID

    @Column(name = "VoterName")
    private String voterName;  // 投票人姓名
    
    @Column(name = "Gender")
    private Integer gender;  // 投票人性別（男：0, 女：1）

    @Column(name = "VotingItemId", nullable = false)
    private Integer votingItemId;  // 投票項目編號

    @Column(name = "CreatedTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;  // 創建時間

    @PrePersist
    public void onCreate() {
        if (createdTime == null) {
            createdTime = new Date();
        }
    }
}
