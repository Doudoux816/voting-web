package com.vote.controller;

import com.vote.model.VoteItem;
import com.vote.model.VoteRecord;
import com.vote.service.VoteItemService;
import com.vote.service.VoteRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vote")
@CrossOrigin(origins = "http://localhost:5173")
public class VoteController {

    @Autowired
    private VoteItemService voteItemService;

    @Autowired
    private VoteRecordService voteRecordService;
    // 1. 顯示所有投票項目
    @GetMapping("/items")
    public ResponseEntity<List<VoteItem>> getAllVoteItems() {
        List<VoteItem> voteItems = voteItemService.getAllVoteItems();
        return ResponseEntity.ok(voteItems);
    }

    // 2. 計算個項目總票數
    @GetMapping("/vote-counts")
    public ResponseEntity<Map<Integer, Long>> getVoteCounts() {
        Map<Integer, Long> voteCounts = voteRecordService.getVoteCounts();
        return ResponseEntity.ok(voteCounts);
    }

    // 3. 投票功能
    @PostMapping("/vote")
    public ResponseEntity<String> castVotes(@RequestBody Map<String, Object> request) {
        // Extract voter name and list of voting item IDs from the request
        String voterName = (String) request.get("voterName");
        List<Integer> votingItemIds = (List<Integer>) request.get("selectedItemIds");

        if (voterName == null || votingItemIds == null) {
            return ResponseEntity.badRequest().body("Invalid request data");
        }

        // Call the service to process the votes
        voteRecordService.castVotes(voterName, votingItemIds);
        return ResponseEntity.ok("Votes successfully cast");
    }

    // 4. 投票人輸入姓名功能
    @PostMapping("/vote-record")
    public ResponseEntity<String> recordVote(@RequestParam("voterName") String voterName, @RequestParam("votingItemId") Integer votingItemId) {
        if (voterName == null || votingItemId == null) {
            return ResponseEntity.badRequest().body("Invalid input");
        }

        VoteRecord voteRecord = new VoteRecord();
        voteRecord.setVoterName(voterName);
        voteRecord.setVotingItemId(votingItemId);

        voteRecordService.saveVoteRecord(voteRecord);
        return ResponseEntity.ok("Vote recorded successfully");
    }

    // 5. 按照投票項目名稱進行模糊查詢
    @GetMapping("/items/search")
    public ResponseEntity<List<VoteItem>> searchVoteItems(@RequestParam("keyword") String keyword) {
        List<VoteItem> voteItems = voteItemService.searchVoteItems(keyword);
        return ResponseEntity.ok(voteItems);
    }

    // 6. 排序功能（票數、項目編號）
    @GetMapping("/items/sorted")
    public ResponseEntity<List<VoteItem>> getSortedVoteItems(
            @RequestParam(value = "sortBy", defaultValue = "votingItemId") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        List<VoteItem> voteItems = voteItemService.getSortedVoteItems(sortBy, direction);
        return ResponseEntity.ok(voteItems);
    }
}
