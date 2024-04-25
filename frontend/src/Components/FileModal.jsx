import React, { useState } from 'react';
import {
  Button,
  Dialog,
  DialogHeader,
  DialogBody,
  DialogFooter,
} from "@material-tailwind/react";
import checkmark from "../Assets/completed.gif"
import PropTypes from "prop-types";
import { sendExcelUserFile } from '../Services/allEmployee';
import "../styles/checkmarkAnimation.css";
const FileModal = ({ handleOpen, open, handleClose, data, setFetch }) => {
  const [file, setFile] = useState(null);
  const [fileSelected, setFileSelected] = useState(false);
  const [uploading, setUploading] = useState(false); // State to track if uploading is in progress
  const [uploadComplete, setUploadComplete] = useState(false); // State to track if upload is complete

  const handleFileChange = (e) => {
    const selectedFile = e.target.files[0];
    if (selectedFile && selectedFile.type === "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") {
      setFile(selectedFile);
      setFileSelected(true); // Set fileSelected to true when a file is selected
    } else {
      // Show an error message or alert the user that only Excel files are allowed
      alert("Please select a valid Excel file.");
      // Optionally, you can clear the file input here
      e.target.value = null;
    }
  };

  const handleFileUpload = async () => {
    if (!file) return; // Don't proceed if no file is selected
    try {
      setUploading(true); // Set uploading state to true when upload starts
      const id = sessionStorage.getItem("id");
      await sendExcelUserFile(file, id);
      setFetch(prev => !prev);
      // Set uploadComplete to true upon successful upload
      setUploadComplete(true);
    } catch (error) {
      console.error("Error uploading file:", error);
    } finally {
      setUploading(false); // Set uploading state to false when upload completes
      // Close the modal after a short delay to display the completed animation
      setTimeout(() => {
        handleClose();
      }, 2000); // Adjust the delay time as needed
    }
  };

  return (
    <>
      <Dialog
        open={open}
        handler={handleOpen}
        animate={{
          mount: { scale: 1, y: 0 },
          unmount: { scale: 0.9, y: -100 },
        }}
      >
        <DialogHeader>{data.title}</DialogHeader>
        <DialogBody>
          {data.message}
          <input type="file" accept=".xlsx, .xls" onChange={handleFileChange} />
        </DialogBody>
        <DialogFooter>
          <Button
            variant="text"
            color="red"
            onClick={handleClose}
            className="mr-1"
            disabled={uploading || uploadComplete} // Disable Cancel button while uploading or after upload completion
          >
            <span>Cancel</span>
          </Button>
          {/* Conditionally render the button based on whether a file is selected */}
          {fileSelected && !uploading && !uploadComplete && (
            <Button variant="gradient" color={`${data.delete ? "red" : "green"}`} onClick={handleFileUpload}>
              <span>{data.actionText}</span>
            </Button>
          )}
          {/* Render an animated icon/message while uploading */}
          {uploading && (
            <div>Uploading... {/* You can use an animated icon here */}</div>
          )}
          {/* Render a completed checkmark animation after upload completion */}
          {uploadComplete && (
            <div className="checkmark-animation">
              <img src={checkmark} alt="Checkmark GIF" />
            </div>
          )}
        </DialogFooter>
      </Dialog>
    </>
  );
};

FileModal.propTypes = {
  handleOpen: PropTypes.func.isRequired,
  handleClose: PropTypes.func.isRequired,
  open: PropTypes.bool.isRequired,
  data: PropTypes.object.isRequired,
  setFetch: PropTypes.func.isRequired,
};

export default FileModal;
