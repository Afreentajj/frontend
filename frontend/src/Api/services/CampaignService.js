import axios from "../axios";

const getMainDash = async () => {
    try {
      const response = await axios.get('/api/campaigns/mainDash', {
        headers: {
          'Content-Type': 'application/json',
          //  'Authorization': `${auth.accessToken}`, // Include the JWT token in the Authorization header
        },
        withCredentials: true, // Include cookies in the request
      });

      console.log(response.data);
    } catch (error) {
      console.error('Error:', error);
    }
  };

const getSorted = async () => {
    try {
      const response = await axios.get('/api/campaigns/sort?asc=1', {
        headers: {
          'Content-Type': 'application/json',
          // 'Authorization': `Bearer ${auth.accessToken}`, // Include the JWT token in the Authorization header
        },
        withCredentials: true, // Include cookies in the request
      });

      console.log(response.data);
    } catch (error) {
      console.error('Error:', error);
    }
  };

  const addCampaign = async (campaignData) => {
    try {
      const response = await axios.post(
        '/api/campaigns',
        campaignData ,
        { withCredentials: true } // Include credentials in the request
      );
      console.log(response.data);
      return response.data;
    } catch (error) {
      if (error.response) {
        console.error('Response error:', error.response.status);
      } else if (error.request) {
        console.error('Request error:', error.request);
      } else {
        console.error('Error:', error.message);
      }
      return null;
    }
  };


const searchCampaigns = async (query) => {
  try {
    const response = await axios.get('/api/campaigns/masterSearch', {
      params: { query }, // Include the query parameter
      withCredentials: true, // Include credentials in the request
    });
    return response.data; // Assuming the response contains the data you need
  } catch (error) {
    console.error('Error searching campaigns:', error);
    throw error; // Handle or rethrow the error as needed
  }
};


const filterCampaigns = async (type) => {
  try {
    const response = await axios.get(`/api/campaigns/filter/${type}` ,  
    {
      withCredentials: true, // Include credentials in the request if needed
    });
    return response.data; // Return the filtered campaigns data
  } catch (error) {
    console.error('Error filtering campaigns:', error);
    throw error; // Throw the error for handling in the calling component
  }
};





  







 const CampaignService = {
    getMainDash,
    getSorted,
    addCampaign,
    searchCampaigns,
    filterCampaigns
}
export default CampaignService;