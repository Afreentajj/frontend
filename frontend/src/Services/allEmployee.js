// traineesAPI.js
import {getBatchIp} from './IpAddress'
const BatchIp=getBatchIp();
export async function fetchEmployees(batchId) {
    try {
      const response = await fetch(`${BatchIp}/batch/remaining-employees/batch-id/${batchId}`);
      if (!response.ok) {
        throw new Error("Failed to fetch trainees");
      }
      const data = await response.json();
      return data;
    } catch (error) {
      console.error("Error fetching trainees:", error);
      return null;
    }
  }
  
  // batchUsers.js

  export async function sendSelectedUsers(selectedUsers, batchId) {
    try {
      // Transform selectedUsers array into an array of objects
      const transformedUsers = selectedUsers.map(userId => ({ employeeId: userId }));
      
      const response = await fetch(`${BatchIp}/batch/employee/batch-id/${batchId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
          
        },
        body: JSON.stringify(transformedUsers) // Send transformedUsers instead of userIds
      });
      

  
      if (!response.ok) {
        throw new Error('Failed to add users to batch');
      }
      // Handle response if needed
    } catch (error) {
      console.error('Error adding users to batch:', error);
      throw error; // Propagate error to caller if needed
    }
  }
  export async function sendExcelUserFile(file, batchId) {
    try {
      // Create a new FormData object
      const formData = new FormData();
      // Append the file to the FormData object
      formData.append('file', file);
  
      const response = await fetch(`${BatchIp}/batch/existing-batch/bulk/batch-id/${batchId}`, {
        method: 'POST',
        body: formData // Send the FormData object as the request body
      });
  
      if (!response.ok) {
        throw new Error('Failed to add users to batch');
      }
      // Handle response if needed
    } catch (error) {
      console.error('Error adding users to batch:', error);
      throw error; // Propagate error to caller if needed
    }
  }
  
  

  
