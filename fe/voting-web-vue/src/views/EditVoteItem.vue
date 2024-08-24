<template>
  <div class="edit-container">
  <h2>投票項目編輯</h2>

  <!-- Search Input -->
  <div class="search-container">
      <input v-model="searchKeyword" @input="searchVoteItems" placeholder="搜尋投票項目..." />
    </div>
  
  <div class="vote-item-list">
    <button @click="openAddDialog" class="add-btn">新增投票項目</button>
    
    <div class="vote-items-container">
      <div v-for="item in voteItems" :key="item.votingItemId" class="vote-item-card">
        <div class="vote-item-details">
          <h3>{{ item.votingItemName }}</h3>
          <p>狀態: {{ item.status === 1 ? '啟用' : '停用' }}</p>
          <img v-if="item.img" :src="'data:image/jpeg;base64,' + item.img" alt="投票項目圖片" class="vote-item-image" />
          <p>開始時間:<br> {{ formatDateTime(item.startTime) }}</p>
          <p>結束時間:<br> {{ formatDateTime(item.endTime) }}</p>
        </div>
        <div class="action-buttons">
          <button @click="openEditDialog(item)">修改</button>
          <button @click="deleteVoteItemHandler(item.votingItemId)">刪除</button>
        </div>
      </div>
    </div>
    
    <Modal :visible="showAddDialog" @close="closeAddDialog">
      <h2>新增投票項目</h2>
      <form @submit.prevent="handleAddVoteItem">
        <div class="form-group">
          <label for="name">項目名稱：</label>
          <input v-model="newVoteItem.votingItemName" id="name" required />
        </div>
        <div class="form-group">
          <label for="status">狀態：</label>
          <select v-model="newVoteItem.status" id="status">
            <option value="0">停用</option>
            <option value="1">啟用</option>
          </select>
        </div>
        <div class="form-group">
          <label for="img">上傳圖片：</label>
          <input type="file" @change="handleAddImageUpload" id="img" />
          <img v-if="newVoteItem.imagePreview" :src="newVoteItem.imagePreview" alt="預覽圖片" class="image-preview" />
        </div>
        <div class="form-group">
          <label for="start-time">開始時間：</label>
          <input type="datetime-local" v-model="newVoteItem.startTime" id="start-time" required />
        </div>
        <div class="form-group">
          <label for="end-time">結束時間：</label>
          <input type="datetime-local" v-model="newVoteItem.endTime" id="end-time" required />
        </div>
        <button type="submit" class="submit-btn">送出</button>
      </form>
    </Modal>
    
    <Modal :visible="showEditDialog" @close="closeEditDialog">
      <h2>修改投票項目</h2>
      <form @submit.prevent="handleEditVoteItem">
        <div class="form-group">
          <label for="edit-name">項目名稱：</label>
          <input v-model="editVoteItem.votingItemName" id="edit-name" required />
        </div>
        <div class="form-group">
          <label for="edit-status">狀態：</label>
          <select v-model="editVoteItem.status" id="edit-status">
            <option value="0">停用</option>
            <option value="1">啟用</option>
          </select>
        </div>
        <div class="form-group">
          <label for="edit-img">上傳圖片：</label>
          <input type="file" @change="handleEditImageUpload" id="edit-img" />
          <img v-if="editVoteItem.imagePreview" :src="editVoteItem.imagePreview" alt="預覽圖片" class="image-preview" />
        </div>
        <div class="form-group">
          <label for="edit-start-time">開始時間：</label>
          <input type="datetime-local" v-model="editVoteItem.startTime" id="edit-start-time" required />
        </div>
        <div class="form-group">
          <label for="edit-end-time">結束時間：</label>
          <input type="datetime-local" v-model="editVoteItem.endTime" id="edit-end-time" required />
        </div>
        <button type="submit" class="submit-btn">送出</button>
      </form>
    </Modal>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getAllVoteItems, deleteVoteItem, createVoteItem, updateVoteItem } from '@/services/voteService';
import Modal from '@/components/Modal.vue';

const voteItems = ref([]);
const searchKeyword = ref('');
const showAddDialog = ref(false);
const showEditDialog = ref(false);
const formatDateTime = (dateTime) => {
  if (!dateTime) return '';
  const date = new Date(dateTime);
  
  return date.toLocaleString('zh-TW', { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', hour12: true });
};


const newVoteItem = ref({
  votingItemName: '',
  status: 1,
  img: null,
  imagePreview: null,
  startTime: '',
  endTime: ''
});


const editVoteItem = ref(null);

const loadVoteItems = async () => {
  try {
    voteItems.value = await getAllVoteItems();
  } catch (error) {
    console.error('獲取投票項目列表失敗:', error);
  }
};

const handleAddVoteItem = async () => {
  try {
    const formData = new FormData();
    formData.append('votingItemName', newVoteItem.value.votingItemName);
    formData.append('status', newVoteItem.value.status);
    formData.append('startTime', new Date(newVoteItem.value.startTime).toISOString()); // 確保格式正確
    formData.append('endTime', new Date(newVoteItem.value.endTime).toISOString()); // 確保格式正確
    if (newVoteItem.value.img) {
      formData.append('img', newVoteItem.value.img);
    }

    // 輸出 FormData 內容以進行調試
    for (const [key, value] of formData.entries()) {
      console.log(`${key}: ${value}`);
    }

    await createVoteItem(formData);
    await loadVoteItems();  // 更新投票項目列表
    closeAddDialog();
  } catch (error) {
    console.error('新增投票項目失敗:', error);
  }
};



const handleEditVoteItem = async () => {
  try {
    if (editVoteItem.value) {
      const formData = new FormData();
      formData.append('votingItemName', editVoteItem.value.votingItemName);
      formData.append('status', editVoteItem.value.status);
      formData.append('startTime', new Date(editVoteItem.value.startTime).toISOString()); // 转换为 ISO 字符串
      formData.append('endTime', new Date(editVoteItem.value.endTime).toISOString()); // 转换为 ISO 字符串
      if (editVoteItem.value.img) {
        formData.append('img', editVoteItem.value.img);
      }
      console.log('FormData:', formData); 
      await updateVoteItem(editVoteItem.value.votingItemId, formData);
      await loadVoteItems();  
      closeEditDialog();
    }
  } catch (error) {
    console.error('修改投票項目失敗:', error);
  }
};

const handleAddImageUpload = (event) => {
  const file = event.target.files[0];
  if (file) {
    newVoteItem.value.img = file; 

    const reader = new FileReader();
    reader.onloadend = () => {
      newVoteItem.value.imagePreview = reader.result; 
    };
    reader.readAsDataURL(file);
  }
};

const handleEditImageUpload = (event) => {
  const file = event.target.files[0];
  if (file) {
    editVoteItem.value.img = file; 

    const reader = new FileReader();
    reader.onloadend = () => {
      editVoteItem.value.imagePreview = reader.result; // 显示预览图像
    };
    reader.readAsDataURL(file);
  }
};

const deleteVoteItemHandler = async (id) => {
  try {
    await deleteVoteItem(id);
    await loadVoteItems();  // 更新投票項目列表
  } catch (error) {
    console.error('刪除投票項目失敗:', error);
  }
};

const openAddDialog = () => {
  showAddDialog.value = true;
};

const closeAddDialog = () => {
  showAddDialog.value = false;
  newVoteItem.value = { votingItemName: '', status: 1, img: null, imagePreview: null, startTime: '', endTime: '' };
};

const openEditDialog = (item) => {

  const formatForInput = (dateTime) => {
    if (!dateTime) return '';
    const date = new Date(dateTime);
    return date.toISOString().slice(0, 16); 
  };

  editVoteItem.value = {
    ...item,
    imagePreview: item.img ? 'data:image/jpeg;base64,' + item.img : null,
    startTime: formatForInput(item.startTime), 
    endTime: formatForInput(item.endTime) 
  };
  showEditDialog.value = true;
};


const closeEditDialog = () => {
  showEditDialog.value = false;
  editVoteItem.value = null;
};

onMounted(loadVoteItems);
</script>

<style scoped>
.edit-container {
  padding: 20px;
  background-color: #f5f5f5;
  border-radius: 10px;
  width: 100%; /* 讓容器寬度隨瀏覽器變化 */
  max-width: 1200px; /* 最大寬度 */
  margin: 0 auto;
}

h2 {
  font-size: 2rem;
  text-align: center;
  color: #00a19b; /* 深咖啡色 */
}

.vote-item-list {
  padding: 20px;
  background-color: #f5f5f5;
  border-radius: 10px;
  max-width: 95%;
  margin: 0 auto;
}

.add-btn {
  border: solid 2px #00a19b; 
  border-radius: 0.25rem;
  height: 65px;
  padding: 10px 20px;
  background-color: white; 
  color: #00a19b; 
  cursor: pointer;
  display: block;
  margin: 20px auto;
  width: 150px;
  transition: background-color 0.3s, color 0.3s, box-shadow 0.3s; 

}

.add-btn:hover {
  background-color: rgba(255, 255, 255, 0.8); /* 半透明的白色背景 */
  color: rgba(0, 161, 155, 0.8); /* 半透明的文字顏色 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); 
}

.vote-items-container {
  display: flex;
  flex-wrap: wrap; /* 允許卡片換行 */
  justify-content: left;
  gap: 20px; /* 間距 */
}

.vote-item-card {
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 15px;
  width: calc(20% - 20px); /* 每個卡片佔據 20% 的寬度，減去間距 */
  text-align: center;
}

@media (max-width: 1200px) {
  .vote-item-card {
    width: calc(25% - 20px); /* 窄屏幕下，每個卡片佔據 25% 的寬度 */
  }
}

@media (max-width: 900px) {
  .vote-item-card {
    width: calc(33.33% - 20px); /* 更窄的屏幕下，每個卡片佔據 33.33% 的寬度 */
  }
}

@media (max-width: 600px) {
  .vote-item-card {
    width: calc(50% - 20px); /* 小屏幕下，每個卡片佔據 50% 的寬度 */
  }
}

@media (max-width: 400px) {
  .vote-item-card {
    width: calc(100% - 20px); /* 非常小的屏幕下，每個卡片佔據 100% 的寬度 */
  }
}

.vote-item-details {
  margin-bottom: 15px;
}

.vote-item-image {
  width: 100%;
  height: auto;
  border-radius: 10px;
  margin-top: 10px;
}

.action-buttons {
  display: flex;
  justify-content: space-around;
}

.submit-btn {
  padding: 10px 20px;
  background-color: #8a6d3b;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-right: 10px;
}

.submit-btn:hover {
  background-color: #5c3f22;
}

.form-group {
  margin-bottom: 15px;
}

.image-preview {
  width: 100%;
  height: auto;
  border-radius: 10px;
  margin-top: 10px;
}

.delete-btn {
  padding: 10px 20px;
  background-color: #d3816b;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.delete-btn:hover {
  background-color: #8c3a2b;
}
</style>
