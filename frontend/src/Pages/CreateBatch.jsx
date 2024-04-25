import React from "react";
import { useState, useEffect } from "react";
import { Card, Input, Button, Typography } from "@material-tailwind/react";
import { useNavigate } from "react-router-dom"; // Import useHistory hook directly
import { createBatch } from "../Services/BatchData";
function CreateBatch() {
  const [batchName, setBatchName] = useState("");
  const [batchDescription, setBatchDescription] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [batchSize, setBatchSize] = useState("");
  const [duration, setDuration] = useState("");
  const navigate = useNavigate(); // Initialize useHistory hook

  useEffect(() => {
    calculateDuration();
  }, [startDate, endDate]);

  const calculateDuration = () => {
    const start = new Date(startDate);
    const end = new Date(endDate);

    if (start && end && !isNaN(start.getTime()) && !isNaN(end.getTime())) {
      const diffInTime = end.getTime() - start.getTime();
      let diffInDays = Math.floor(diffInTime / (1000 * 3600 * 24)) + 1;

      if (diffInDays < 1) {
        diffInDays = 0;
      }

      setDuration(`${diffInDays} days`);
    } else {
      setDuration("");
    }
  };

  const handleStartDateChange = (e) => {
    const newStartDate = e.target.value;
    setStartDate(newStartDate);

    // Set minimum selectable date for end date input
    document.getElementById("endDate").setAttribute("min", newStartDate);

    // If end date is before new start date, reset end date
    if (new Date(endDate) < new Date(newStartDate)) {
      setEndDate("");
    }
  };

  const handleEndDateChange = (e) => {
    const newEndDate = e.target.value;
    setEndDate(newEndDate);
  };
  const handleSubmit = async (e) => {
    e.preventDefault();

    const success = await createBatch({
      batchName,
      batchDescription,
      startDate,
      endDate,
      batchSize,
    });

    if (success) {
      navigate("/lms/batches");
    } else {
      // Handle failure case if needed
    }
  };

  // Get today's date in the format YYYY-MM-DD
  const today = new Date().toISOString().split("T")[0];

  return (
    <div className="flex justify-center items-center h-full mt-10">
      <Card className="w-full max-w-lg p-8">
        <Typography variant="h4" color="blue-gray" className="mb-6">
          Batch Creation
        </Typography>
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <div className="flex flex-col">
            <label htmlFor="batchName">Batch Name:</label>
            <Input
              id="batchName"
              size="lg"
              placeholder="Batch Name"
              value={batchName}
              onChange={(e) => setBatchName(e.target.value)}
              required
            />
          </div>
          <div className="flex flex-col">
            <label htmlFor="batchDescription">Batch Description:</label>
            <Input
              id="batchDescription"
              size="lg"
              placeholder="Batch Description"
              value={batchDescription}
              onChange={(e) => setBatchDescription(e.target.value)}
              required
            />
          </div>
          <div className="flex flex-col">
            <label htmlFor="startDate">Start Date:</label>
            <Input
              id="startDate"
              type="date"
              size="lg"
              placeholder="Start Date"
              value={startDate}
              onChange={handleStartDateChange}
              min={today} // Set minimum selectable date to today's date
              required
            />
          </div>
          <div className="flex flex-col">
            <label htmlFor="endDate">End Date:</label>
            <Input
              id="endDate"
              type="date"
              size="lg"
              placeholder="End Date"
              value={endDate}
              onChange={handleEndDateChange}
              min={startDate} // Set minimum selectable date as the start date
              required
            />
          </div>
          <div className="flex flex-col">
            <label htmlFor="batchSize">Batch Size:</label>
            <Input
              id="batchSize"
              size="lg"
              placeholder="Batch Size"
              value={batchSize}
              onChange={(e) => setBatchSize(e.target.value)}
              required
            />
          </div>
          <Typography variant="lead" color="gray">
            Duration: {duration}
          </Typography>
          <Button type="submit" size="lg">
            Create Batch
          </Button>
        </form>
      </Card>
    </div>
  );
}

export default CreateBatch;
