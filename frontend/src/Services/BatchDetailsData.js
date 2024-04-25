import { getBatchIp } from "./IpAddress";
const BatchIp=getBatchIp();
export async function fetchBatchDetails(batchId) {
  try {
    const response = await fetch(`${BatchIp}/batch/id/${batchId}`);
    if (!response.ok) {
      throw new Error("Failed to fetch batch details");
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error fetching batch details:", error);
    return null;
  }
}
// BatchEmployee.js
export async function deleteTraineesFromBatch(batchId, selectedUsers) {
  console.log({ userIds: selectedUsers });
  try {
    const response = await fetch(
      `${BatchIp}/batch/batch-id/employees/${batchId}`,
      {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(selectedUsers), // send the selectedUsers directly
      }
    );
    if (!response.ok) {
      console.log("Couldn't delete");
      throw new Error("Failed to delete trainees from batch");
    }
    // Return response data if needed
    console.log(response.json());
    return response.json();
  } catch (error) {
    throw new Error(`Error deleting trainees from batch: ${error.message}`);
  }
}
// deleteBatch.js
export async function deleteBatch(batchId) {
  try {
    const response = await fetch(`${BatchIp}/batch/batch-id/${batchId}`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(), // send the selectedUsers directly
    });
    if (!response.ok) {
      console.log("Couldn't delete");
      throw new Error("Failed to delete batch");
    }
    // Return response data if needed
    console.log(response.json());
    return response.json();
  } catch (error) {
    throw new Error(`Error deleting batch: ${error.message}`);
  }
}