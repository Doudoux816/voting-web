<template>
  <div>
    <h1>投票頁面</h1>
    
    <div>
      <label for="voterName">請輸入您的姓名:</label>
      <input type="text" v-model="voterName" id="voterName" placeholder="輸入您的姓名" />
    </div>

    <h2>選擇投票項目:</h2>
    <div class="vote-items-container" v-if="voteItems.length > 0">
      <div v-for="item in voteItems" :key="item.votingItemId" class="vote-item-card">
        <input
          type="checkbox"
          :id="'item-' + item.votingItemId"
          :value="item.votingItemId"
          v-model="selectedItemIds"
          class="vote-item-checkbox"
        />
        <label :for="'item-' + item.votingItemId" class="vote-item-label">{{ item.votingItemName }}</label>
        <img v-if="item.img" :src="'data:image/jpeg;base64,' + item.img" alt="Item Image" class="vote-item-image" />
        <p>得票數: {{ item.voteCount }}</p> 
      </div>
    </div>
    
    <button @click="submitVotes" class="submit-button">提交投票</button>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import VoteService from '@/services/voteService';

export default {
  setup() {
    const voteItems = ref([]);
    const selectedItemIds = ref([]);
    const voterName = ref('');

    const fetchVoteItems = async () => {
      try {
        voteItems.value = await VoteService.getVoteItems();
      } catch (error) {
        console.error('Failed to fetch voting items:', error);
      }
    };

    const submitVotes = async () => {
      if (!voterName.value.trim()) {
        alert('請輸入您的姓名');
        return;
      }
      
      if (selectedItemIds.value.length === 0) {
        alert('請選擇至少一個投票項目');
        return;
      }

      try {
        await VoteService.castVotes(voterName.value, selectedItemIds.value);
        alert('投票成功');
        voterName.value = '';
        selectedItemIds.value = [];
        fetchVoteItems();
      } catch (error) {
        console.error('Failed to submit votes:', error);
        alert('投票失敗');
      }
    };

    onMounted(() => {
      fetchVoteItems();
    });

    return {
      voteItems,
      selectedItemIds,
      voterName,
      submitVotes,
    };
  },
};
</script>

<style scoped>
.vote-items-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.vote-item-card {
  background: #fff; 
  border: 1px solid #ddd; 
  border-radius: 8px; 
  padding: 16px; 
  width: 300px; 
  box-shadow: 0 2px 4px rgba(0,0,0,0.1); 
  text-align: center; 
}

.vote-item-checkbox {
  display: block;
  margin: 0 auto 8px; 
}

.vote-item-label {
  display: block;
  margin-bottom: 8px;
  font-weight: bold; 
}

.vote-item-image {
  max-width: 100%; 
  height: auto; 
}

.submit-button {
  margin-top: 20px; 
  padding: 10px 20px; 
  background-color: #007bff;
  color: #fff; 
  border: none; 
  border-radius: 5px; 
  cursor: pointer; 
  font-size: 16px; 
}

.submit-button:hover {
  background-color: #0056b3;
}
</style>
