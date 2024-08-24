import axios from 'axios';

const API_BASE_URL = 'http://localhost:8081/api/vote';  // Adjust this URL as needed
// http://localhost:8081/api/vote/items
// http://localhost:8081/api/vote/vote-records
const VoteService = {
  async getVoteItems() {
    try {
      const response = await axios.get(`${API_BASE_URL}/items`);
      return response.data;
    } catch (error) {
      console.error('Failed to fetch voting items:', error);
      throw error;
    }
  },

  async castVotes(voterName, selectedItemIds) {
    try {
      const response = await axios.post(`${API_BASE_URL}/vote`, {
        voterName,
        selectedItemIds,
        
      });
      return response.data;
    } catch (error) {
      console.error('Failed to submit votes:', error);
      throw error;
    }
  }
};

export default VoteService;

