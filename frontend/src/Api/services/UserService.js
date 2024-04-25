import axios from "../axios";
import axios from 'axios';
const getUser = async () => {
    try {
      const response = await axios.get('/api/campaigns/check', {
        headers: {
          'Content-Type': 'application/json',
          //  'Authorization': `${auth.accessToken}`, // Include the JWT token in the Authorization header
        },
        withCredentials: true, // Include cookies in the request
      });

      console.log("User details",response.data);
      return response.data;
    } catch (error) {
      console.error('Error:', error);
    }
  };


const userService ={
    getUser
}
export default userService