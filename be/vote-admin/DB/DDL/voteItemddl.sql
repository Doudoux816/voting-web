CREATE TABLE voteItem (
    VotingItemId INT AUTO_INCREMENT PRIMARY KEY, -- 投票項目編號
    VotingItemName VARCHAR(255) NOT NULL, -- 投票項目名稱
    img LONGBLOB, -- 投票項目圖片
    Status INT NOT NULL, -- 投票項目狀態(0: 停用, 1: 啟用)
    StartTime TIMESTAMP, -- 開始時間
    EndTime TIMESTAMP, -- 結束時間
    CreatedTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 建立時間
    ModifiedTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 修改時間
    CreatedBy VARCHAR(255), -- 建立人
    ModifiedBy VARCHAR(255), -- 修改人
    VoteCount INT DEFAULT 0 -- 票數
);
