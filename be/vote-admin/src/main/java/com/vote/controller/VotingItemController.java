package com.vote.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.vote.model.VotingItem;
import com.vote.service.VotingItemService;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "http://localhost:5173")
public class VotingItemController {

    @Autowired
    private VotingItemService votingItemService;

    // 獲取所有投票項目
    @GetMapping
    public ResponseEntity<List<VotingItem>> getAllVoteItems() {
        List<VotingItem> voteItems = votingItemService.getAllVoteItems();
        return new ResponseEntity<>(voteItems, HttpStatus.OK);
    }

    // 創建新的投票項目
    @PostMapping
    public ResponseEntity<VotingItem> createVoteItem(
            @RequestParam("votingItemName") String votingItemName,
            @RequestParam("status") Integer status,
            @RequestParam("startTime") String startTimeStr,
            @RequestParam("endTime") String endTimeStr,
            @RequestParam(value = "img", required = false) MultipartFile img) {

        try {
            // 使用 ISO_OFFSET_DATE_TIME 解析日期時間字符串
            DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            OffsetDateTime startTime = OffsetDateTime.parse(startTimeStr, formatter);
            OffsetDateTime endTime = OffsetDateTime.parse(endTimeStr, formatter);
            
            // 將 OffsetDateTime 轉換為 Date
            Date startDate = Date.from(startTime.toInstant());
            Date endDate = Date.from(endTime.toInstant());

            VotingItem voteItem = new VotingItem();
            voteItem.setVotingItemName(votingItemName);
            voteItem.setStatus(status);
            voteItem.setStartTime(startDate);
            voteItem.setEndTime(endDate);

            if (img != null && !img.isEmpty()) {
                byte[] imgBytes = img.getBytes();
                voteItem.setImg(imgBytes);
            }

            VotingItem createdVoteItem = votingItemService.createVoteItem(voteItem);
            return new ResponseEntity<>(createdVoteItem, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 更新指定的投票項目
    @PutMapping("/{id}")
    public ResponseEntity<VotingItem> updateVoteItem(
            @PathVariable Integer id,
            @RequestParam("votingItemName") String votingItemName,
            @RequestParam("status") Integer status,
            @RequestParam("startTime") String startTimeStr,
            @RequestParam("endTime") String endTimeStr,
            @RequestParam(value = "img", required = false) MultipartFile img) {

        final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 檔案大小限制為5MB
        
        if (img != null && img.getSize() > MAX_FILE_SIZE) {
            return new ResponseEntity<>(HttpStatus.PAYLOAD_TOO_LARGE); // 文件過大不給過
        }

        try {
            // 查找現有的投票項目
            VotingItem existingVoteItem = votingItemService.getVoteItemById(id);
            if (existingVoteItem == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // 使用 ISO_OFFSET_DATE_TIME 解析日期時間字符串
            DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            OffsetDateTime startTime = OffsetDateTime.parse(startTimeStr, formatter);
            OffsetDateTime endTime = OffsetDateTime.parse(endTimeStr, formatter);
            
            // 將 OffsetDateTime 轉換為 Date
            Date startDate = Date.from(startTime.toInstant());
            Date endDate = Date.from(endTime.toInstant());

            // 更新投票項目的字段
            existingVoteItem.setVotingItemName(votingItemName);
            existingVoteItem.setStatus(status);
            existingVoteItem.setStartTime(startDate);
            existingVoteItem.setEndTime(endDate);

            if (img != null && !img.isEmpty()) {
                byte[] imgBytes = img.getBytes();
                existingVoteItem.setImg(imgBytes);
            }

            // 保存更新後的投票項目
            VotingItem updatedVoteItem = votingItemService.updateVoteItem(id, existingVoteItem);
            return new ResponseEntity<>(updatedVoteItem, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 刪除指定的投票項目
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoteItem(@PathVariable Integer id) {
        votingItemService.deleteVoteItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private String saveFile(MultipartFile file) throws IOException {
        return "path/to/saved/file";
    }
}
