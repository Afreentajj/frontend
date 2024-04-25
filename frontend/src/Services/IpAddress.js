export const token = "eyJhbGciOiJIUzI1NiJ9.eyJmaXJzdE5hbWUiOiJhYmR1bGxhaCIsInJvbGUiOiJVU0VSIiwic3ViIjoicmFzaGFkYnM1NTVAZ21haWwuY29tIiwiaWF0IjoxNzEzNTIzMDc3LCJleHAiOjE3MTM1MjY2Nzd9.upE3abwpf1i2rWT-L_gTvGbaJEIL5YDPq_J-46d-vYE";

// Define your headers here
export const myHeaders = new Headers({
    "Content-Type": "application/json",
    "Authorization": `Bearer ${token}`,
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-Headers": "Content-Type",
    "Access-Control-Allow-Methods": "POST,PATCH,OPTIONS,GET"
});

// Function to get the batch IP
export const getBatchIp = () => {
    return import.meta.env.VITE_BATCH_SERVICE_URL;
}

// Function to get the progress IP
export const getProgressIp = () => {
    return import.meta.env.VITE_PROGRESS_SERVICE_URL;
}
