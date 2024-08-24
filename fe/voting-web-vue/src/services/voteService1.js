import axios from 'axios';

const API_BASE_URL = 'http://localhost:8081/';  // Adjust this URL as needed

const VoteService1 = {
  async getVoteItems() {
    try {
      const response = await axios.get(`${API_BASE_URL}/vote-items`);
      return response.data;
    } catch (error) {
      console.error('Failed to fetch voting items:', error);
      throw error;
    }
  },

  async castVotes(voterName, votingItemIds) {
    try {
      const response = await axios.post(`${API_BASE_URL}/vote-records`, {
        voterName,
        votingItemIds
      });
      return response.data;
    } catch (error) {
      console.error('Failed to submit votes:', error);
      throw error;
    }
  }
};

export default VoteService1;

