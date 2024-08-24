<template>
    <div>
    <h1>投票項目列表</h1>
    <ul>
        <li v-for="item in voteItems" :key="item.votingItemId">
        {{ item.votingItemName }} (狀態: {{ item.status }})
        </li>
    </ul>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
// import { getAllVoteItems } from '@/services/voteService';

  // 定義響應式變數
const voteItems = ref([]);

  // 當組件掛載時，調用函數以獲取投票項目
onMounted(async () => {
    try {
    // voteItems.value = await getAllVoteItems();
    } catch (error) {
    console.error('獲取投票項目失敗:', error);
    }
});
</script>

<style scoped>
  /* 添加樣式 */
</style>