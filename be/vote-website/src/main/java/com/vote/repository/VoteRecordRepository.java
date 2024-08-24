package com.vote.repository;

import com.vote.model.VoteRecord;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRecordRepository extends JpaRepository<VoteRecord, Integer> {

	// 計算每個投票項目的總票數
	@Query("SELECT vr.votingItemId, COUNT(vr) FROM VoteRecord vr GROUP BY vr.votingItemId")
    List<Object[]> countVotesByItem();

    // 根據投票項目 ID 查詢投票記錄
    @Query("SELECT v FROM VoteRecord v WHERE v.votingItemId = :votingItemId")
    List<VoteRecord> findByVotingItemId(@Param("votingItemId") Integer votingItemId);

    // 根據投票者姓名查詢投票記錄
    @Query("SELECT v FROM VoteRecord v WHERE v.voterName = :voterName")
    List<VoteRecord> findByVoterName(@Param("voterName") String voterName);
    
    Integer countByVotingItemId(Integer votingItemId);

}
