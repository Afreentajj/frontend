import React from "react";
import { useState, useEffect } from "react";
import { Card, Input, Button, Typography } from "@material-tailwind/react";
import { useNavigate } from "react-router-dom";
import { getBatchDetails, updateBatch } from "../Services/BatchData";

function EditBatch() {
  const [batchName, setBatchName] = useState("");
  const [batchDescription, setBatchDescription] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [batchSize, setBatchSize] = useState("");
  const [duration, setDuration] = useState("");

  const id = sessionStorage.getItem("id");
  const navigate = useNavigate();

  useEffect(() => {
    const fetchData = async () => {
      const data = await getBatchDetails(id);
      if (data) {
        setBatchName(data.batchName);
        setBatchDescription(data.batchDescription);
        setStartDate(data.startDate);
        setEndDate(data.endDate);
        setBatchSize(data.batchSize);
        calculateDuration(data.startDate, data.endDate);
      }
    };
    fetchData();
  }, [id]);

  const calculateDuration = (start, end) => {
    const startDate = new Date(start);
    const endDate = new Date(end);

    if (
      startDate &&
      endDate &&
      !isNaN(startDate.getTime()) &&
      !isNaN(endDate.getTime())
    ) {
      const diffInTime = endDate.getTime() - startDate.getTime();
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
    calculateDuration(newStartDate, endDate);
  };

  const handleEndDateChange = (e) => {
    const newEndDate = e.target.value;
    setEndDate(newEndDate);
    calculateDuration(startDate, newEndDate);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const success = await updateBatch(id, {
      batchName,
      batchDescription,
      startDate,
      endDate,
      batchSize,
    });

    if (success) {
      navigate("/lms/batches/batchDetails");
    } else {
      // Handle failure case if needed
    }
  };

  const today = new Date().toISOString().split("T")[0];

  return (
    <div className="flex justify-center items-center h-full mt-10">
      <Card className="w-full max-w-lg p-8">
        <Typography variant="h4" color="blue-gray" className="mb-6">
          Edit Batch
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
              min={startDate}
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
            Update Batch
          </Button>
        </form>
      </Card>
    </div>
  );
}

export default EditBatch;
