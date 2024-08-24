package com.vote.service;

import com.vote.model.VoteRecord;
import com.vote.repository.VoteRecordRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VoteRecordService {

    @Autowired
    private VoteRecordRepository voteRecordRepository;

    // 計算每個投票項目的總票數
    public Map<Integer, Long> getVoteCounts() {
        List<Object[]> results = voteRecordRepository.countVotesByItem();
        Map<Integer, Long> voteCounts = new HashMap<>();

        for (Object[] result : results) {
            Integer votingItemId = (Integer) result[0];
            Long count = (Long) result[1];
            voteCounts.put(votingItemId, count);
        }

        return voteCounts;
    }

    // 保存投票記錄
    public void saveVoteRecord(VoteRecord voteRecord) {
        voteRecordRepository.save(voteRecord);
    }

    @Transactional
    public void castVotes(String voterName, List<Integer> votingItemIds) {
        for (Integer itemId : votingItemIds) {
            VoteRecord voteRecord = new VoteRecord();
            voteRecord.setVoterName(voterName);
            voteRecord.setVotingItemId(itemId);
            // 設置 gender 欄位（如果需要）
            voteRecordRepository.save(voteRecord);
            // 輸出日誌檢查每條記錄
            System.out.println("Saved VoteRecord: " + voteRecord);
        }
    }
}
