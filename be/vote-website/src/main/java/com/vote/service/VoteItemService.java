package com.vote.service;

import com.vote.model.VoteItem;
import com.vote.repository.VoteItemRepository;
import com.vote.repository.VoteRecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VoteItemService {

    @Autowired
    private VoteItemRepository voteItemRepository;

    @Autowired
    private VoteRecordService voteRecordService;
    
    @Autowired
    private VoteRecordRepository voteRecordRepository;

    // 找所有投票項目
    public List<VoteItem> getAllVoteItems() {
        List<VoteItem> voteItems = voteItemRepository.findAll();

        for (VoteItem item : voteItems) {
            Integer itemId = item.getVotingItemId();
            System.out.println("Item ID: " + itemId); 

            Integer voteCount = voteRecordRepository.countByVotingItemId(itemId);
            System.out.println("Vote Count: " + voteCount); 

            item.setVoteCount(voteCount != null ? voteCount : 0); 
        }

        return voteItems;
    }




    // 狀態為1且投票時間包含今天的投票項目
    public List<VoteItem> getFilteredVoteItems() {
        Date today = new Date();
        List<VoteItem> allItems = voteItemRepository.findAll();
        List<VoteItem> filteredItems = new ArrayList<>();

        for (VoteItem item : allItems) {
            Date startTime = item.getStartTime();
            Date endTime = item.getEndTime();

            if (item.getStatus() == 1 &&
                startTime != null &&
                endTime != null &&
                (startTime.before(today) || startTime.equals(today)) &&
                (endTime.after(today) || endTime.equals(today))) {
                filteredItems.add(item);
            }
        }

        return filteredItems;
    }


    // 用名稱模糊查詢
    public List<VoteItem> searchVoteItems(String keyword) {
        return voteItemRepository.searchByName(keyword);
    }

    // 排序用
    public List<VoteItem> getVoteItemsSortedBy(String sortBy) {
        if ("votes".equals(sortBy)) {
            // 用票數
            Map<Integer, Long> voteCounts = voteRecordService.getVoteCounts();
            return voteItemRepository.findAll()
                    .stream()
                    .sorted((item1, item2) -> Long.compare(
                            voteCounts.getOrDefault(item2.getVotingItemId(), 0L),
                            voteCounts.getOrDefault(item1.getVotingItemId(), 0L)))
                    .collect(Collectors.toList());
        } else if ("id".equals(sortBy)) {
            // 用編號
            return voteItemRepository.findAllByOrderByVotingItemIdAsc();
        }
        return getAllVoteItems();
    }

    public List<VoteItem> getSortedVoteItems(String sortBy, String direction) {
        return null;
    }
}
