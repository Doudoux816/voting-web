package com.vote.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "voteItem")
public class VoteItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VotingItemId")
    private Integer votingItemId;  // 投票項目編號

    @Column(name = "VotingItemName", nullable = false)
    private String votingItemName;  // 投票項目名稱
    
    @Lob
    private byte[] img;  // 投票項目圖片

    @Column(name = "Status", nullable = false)
    private Integer status;  // 投票項目狀態(0: 停用, 1: 啟用)

    @Column(name = "StartTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;  // 開始時間

    @Column(name = "EndTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;  // 結束時間

    @Column(name = "CreatedTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;  // 建立時間

    @Column(name = "ModifiedTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;  // 修改時間

    @Column(name = "CreatedBy")
    private String createdBy;  // 建立人

    @Column(name = "ModifiedBy")
    private String modifiedBy;  // 修改人
    
    @Column(name = "VoteCount")
    private Integer voteCount = 0;  // 票數

    @PrePersist
    public void onCreate() {
        if (createdTime == null) {
            createdTime = new Date();
        }
        if (modifiedTime == null) {
            modifiedTime = new Date();
        }
    }

    @PreUpdate
    public void onUpdate() {
        modifiedTime = new Date();
    }
}
