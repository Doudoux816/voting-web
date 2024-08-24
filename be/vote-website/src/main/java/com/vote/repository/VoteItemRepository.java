package com.vote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vote.model.VoteItem;

@Repository
public interface VoteItemRepository extends JpaRepository<VoteItem, Integer> {
    
    @Query("SELECT v FROM VoteItem v WHERE v.votingItemName LIKE %:keyword%")
    List<VoteItem> searchByName(@Param("keyword") String keyword);

    List<VoteItem> findAllByOrderByVotingItemNameAsc();  // 按投票項目名稱排序
    List<VoteItem> findAllByOrderByVotingItemIdAsc();    // 按投票項目編號排序
}