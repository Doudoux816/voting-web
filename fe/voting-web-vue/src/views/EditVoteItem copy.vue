<template>
  <div class="vote-item-list">
    <button @click="openAddDialog" class="add-btn">新增投票項目</button>
    
    <div class="vote-items-container">
      <div v-for="item in voteItems" :key="item.votingItemId" class="vote-item-card">
        <div class="vote-item-details">
          <h3>{{ item.votingItemName }}</h3>
          <p>狀態: {{ item.status === 1 ? '啟用' : '停用' }}</p>
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
          <input type="file" @change="handleImageUpload" id="img" />
          <img v-if="newVoteItem.imagePreview" :src="newVoteItem.imagePreview" alt="預覽圖片" class="image-preview" />
        </div>
        <button type="submit" class="submit-btn">送出</button>
      </form>
    </Modal>
    <Modal :visible="showEditDialog" @close="closeEditDialog">
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
          <input type="file" @change="handleImageUpload" id="img" />
          <img v-if="editVoteItem.imagePreview" :src="editVoteItem.imagePreview" alt="預覽圖片" class="image-preview" />
        </div>
        <button type="submit" class="submit-btn">送出</button>
      </form>
    </Modal>
    <!-- <dialog v-if="showAddDialog" @close="closeAddDialog">
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
        <button type="submit" class="submit-btn">提交</button>
      </form>
    </dialog>

    <dialog v-if="showEditDialog" @close="closeEditDialog">
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
        <button type="submit" class="submit-btn">提交</button>
      </form>
    </dialog> -->
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getAllVoteItems, deleteVoteItem, createVoteItem, updateVoteItem } from '@/services/voteService';
import Modal from '@/components/Modal.vue'; // 
const voteItems = ref([]);
const showAddDialog = ref(false);
const showEditDialog = ref(false);
const newVoteItem = ref({
  votingItemName: '',
  status: 1,
  img: null,
  imagePreview: null
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
    const input = {
      img:
      img:
      img:
    }
    await createVoteItem(newVoteItem.value);
    await loadVoteItems();  // 更新投票項目列表
    closeAddDialog();
  } catch (error) {
    console.error('新增投票項目失敗:', error);
  }
};

const handleEditVoteItem = async () => {
  try {
    if (editVoteItem.value) {
      await updateVoteItem(editVoteItem.value.votingItemId, editVoteItem.value);
      await loadVoteItems();  // 更新投票項目列表
      closeEditDialog();
    }
  } catch (error) {
    console.error('修改投票項目失敗:', error);
  }
};
const handleImageUpload = (event) => {
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
  newVoteItem.value = { votingItemName: '', status: 1 };
};

const openEditDialog = (item) => {
  editVoteItem.value = { ...item };  // Copy the item for editing
  showEditDialog.value = true;
};

const closeEditDialog = () => {
  showEditDialog.value = false;
  editVoteItem.value = null;
};

onMounted(loadVoteItems);
</script>

<style scoped>
.vote-item-list {
  padding: 20px;
  background-color: #f5f5f5;
  border-radius: 10px;
  max-width: 95%;
  margin: 0 auto;
}

.add-btn {
  padding: 10px 20px;
  background-color: #8a6d3b; /* 咖啡色 */
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-bottom: 20px;
}

.vote-items-container {
  display: flex;
  flex-wrap: wrap;
}

.vote-item-card {
  padding: 10px;
  background-color: #fff;
  border: 1px solid #d1b99f; /* 淺咖啡色邊框 */
  border-radius: 5px;
  margin: 10px;
  flex: 1 1 calc(25% - 20px); /* 每行顯示4個項目 */
}

.vote-item-details {
  margin-bottom: 10px;
}

.vote-item-image {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
  margin-top: 10px;
}

.action-buttons button {
  padding: 5px 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.action-buttons button:first-child {
  background-color: #8a6d3b; /* 咖啡色 */
  color: white;
  margin-right: 10px;
}

.action-buttons button:first-child:hover {
  background-color: #6f4f28; /* 深咖啡色 */
}

.action-buttons button:last-child {
  background-color: #d3816b; /* 暗紅咖啡色 */
  color: white;
}

.action-buttons button:last-child:hover {
  background-color: #8c3a2b; /* 更深的暗紅咖啡色 */
}

.image-preview {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
  margin-top: 10px;
}

dialog {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 20px;
  background-color: #fff;
  border: 1px solid #d1b99f;
  border-radius: 5px;
  z-index: 1000;
  max-width: 90vw; /* 最大宽度为视口宽度的90% */
  max-height: 90vh; /* 最大高度为视口高度的90% */
  overflow: auto; /* 如果内容超出容器，显示滚动条 */
}
</style>

