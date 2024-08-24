package com.vote.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vote.model.VotingItem;
import com.vote.repository.VotingItemRepository;


@Service
public class VotingItemService {

	@Autowired
    private VotingItemRepository voteItemRepository;

    // 獲取所有投票項目
    public List<VotingItem> getAllVoteItems() {
        return voteItemRepository.findAll();
    }

    // 根據 ID 獲取單個投票項目
    public VotingItem getVoteItemById(Integer id) {
        Optional<VotingItem> voteItem = voteItemRepository.findById(id);
        return voteItem.orElse(null);
    }

    // 創建新的投票項目
    public VotingItem createVoteItem(VotingItem voteItem) {
        return voteItemRepository.save(voteItem);
    }

    // 更新現有的投票項目
    public VotingItem updateVoteItem(Integer id,VotingItem voteItem) {
        return voteItemRepository.save(voteItem);
    }

    // 刪除指定的投票項目
    public void deleteVoteItem(Integer id) {
        voteItemRepository.deleteById(id);
    }
}