import { getBatchIp, myHeaders } from "./IpAddress";
const BatchIp=getBatchIp();
export async function fetchBatchData() {
  try {
   console.log("Batch IP"+BatchIp);
    const response = await fetch(`${BatchIp}/batch`, {
      headers: myHeaders,
    });

    if (!response.ok) {
      throw new Error("Failed to fetch batch data");
    }

    const data = await response.json();

    const today = new Date();
    const updatedData = data.map((batch) => {
      const startDate = new Date(batch.startDate);
      const endDate = new Date(batch.endDate);
      const online = startDate <= today && today <= endDate;
      return { ...batch, online };
    });

    return updatedData;
  } catch (error) {
    console.error("Error fetching batch data:", error);
    return null;
  }
}

// batchAPI.js
export async function createBatch(data) {
  try {
    const response = await fetch(`${BatchIp}/batch`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    if (!response.ok) {
      throw new Error("Failed to create batch");
    }

    console.log("Batch created successfully");
    return true; // Indicate successful creation
  } catch (error) {
    console.error("Error creating batch:", error);
    return false; // Indicate failure
  }
}
// batchAPI.js
export async function getBatchDetails(id) {
  try {
    const response = await fetch(`${BatchIp}/batch/id/${id}`);
    if (!response.ok) {
      throw new Error("Failed to fetch batch details");
    }
    return await response.json();
  } catch (error) {
    console.error("Error fetching batch details:", error);
    return null;
  }
}

export async function updateBatch(id, data) {
  try {
    const response = await fetch(`${BatchIp}/batch/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    if (!response.ok) {
      throw new Error("Failed to update batch");
    }

    console.log("Batch updated successfully");
    return true; // Indicate successful update
  } catch (error) {
    console.error("Error updating batch:", error);
    return false; // Indicate failure
  }
}
