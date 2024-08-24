CREATE TABLE voteRecord (
    Id INT AUTO_INCREMENT PRIMARY KEY, -- 每條投票記錄的 ID
    VoterName VARCHAR(255), -- 投票人姓名
    Gender INT, -- 投票人性別（男：0, 女：1）
    VotingItemId INT NOT NULL, -- 投票項目編號
    CreatedTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 創建時間
    FOREIGN KEY (VotingItemId) REFERENCES voteItem(VotingItemId) -- 外鍵約束
);
