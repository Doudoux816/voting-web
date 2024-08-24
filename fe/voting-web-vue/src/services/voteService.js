import axios from 'axios';

// Define API_BASE_URL
const API_BASE_URL = 'http://localhost:8080/vote'; // 替換為你的 API 基礎 URL

// Create an axios instance
const useAxios = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const getAllVoteItems = async () => {
    try {
      const response = await useAxios.get('/api/items');
      return response.data;
    } catch (error) {
      console.error('獲取所有投票項目失敗:', error);
      throw error;
    }
  };

export const getVoteItem = async (id) => {
    try {
      const response = await useAxios.get(`/api/items/${id}`);
      return response.data;
    } catch (error) {
      console.error('獲取投票項目失敗:', error);
      throw error;
    }
  };

export const createVoteItem = async (voteItem) => {
  const headers = {
    'Content-Type': 'multipart/form-data',
  };

  try {
    const response = await useAxios.post('/api/items', voteItem,{ headers });
    return response.data;
  } catch (error) {
    console.error('創建投票項目失敗:', error);
    throw error;
  }
};

export const updateVoteItem = async (id, voteItem) => {
  const headers = {
    'Content-Type': 'multipart/form-data',
  };
  try {
    const response = await useAxios.put(`/api/items/${id}`, voteItem,{ headers });
    return response.data;
  } catch (error) {
    console.error('更新投票項目失敗:', error);
    throw error;
  }
};

export const deleteVoteItem = async (id) => {
  try {
    await useAxios.delete(`/api/items/${id}`);
  } catch (error) {
    console.error('刪除投票項目失敗:', error);
    throw error;
  }
};

export const searchVoteItems = async (keyword) => {
    try {
      const response = await useAxios.get('/api/items/search', { params: { keyword } });
      return response.data;
    } catch (error) {
      console.error('Failed to search vote items:', error);
      throw error;
    }
  };