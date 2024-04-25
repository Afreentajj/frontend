import React, { useState } from 'react';


export function BulkUploadForm({setShowBulkUpload}) {
  const [file, setFile] = useState(false);
  const [uploadStatus, setUploadStatus] = useState('');
  const[cancel, setCancel] = useState('');

  const handleFileChange = (e) => {
    const selectedFile = e.target.files[0];
    if (selectedFile) {
      const fileType = selectedFile.type;
     
      if (fileType === 'application/vnd.ms-excel' || fileType === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet') {
        setFile(selectedFile);
      } else {
        alert('Please select a valid Excel file.');
      }
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!file) {
      alert('Please select a file to upload.');
      return;
    }

    try {
      const formData = new FormData();
      formData.append('file', file);
  
      const response = await fetch(`${import.meta.env.VITE_API_GOWSIC}/user/upload`, {
        method: 'POST',
        body: formData,
      });
  
      if (response.ok) {
        // setShowBulkUpload(false);
        setUploadStatus('Upload successful!');
      } else {
        setUploadStatus('Upload failed. Please try again later.');
      }
    } catch (error) {
      if (error.name === 'SyntaxError') {
        console.error('Duplicate user data detected:', error);
        setUploadStatus('Some users already exist in the database.');
      } else {
        console.error('Error uploading file:', error);
        setUploadStatus('An error occurred. Please try again later.');
      }
    }
  };
  const handleCancel = () => {
    setShowBulkUpload(false);
   
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <input type="file" accept=".xls, .xlsx" onChange={handleFileChange} />
        <div className="mt-4">
          <button type="submit" className="mr-2 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
            Upload
          </button>
          <button type="button" onClick={handleCancel} className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
            Cancel
          </button>
        </div>
      </form>
      {uploadStatus && <p>{uploadStatus}</p>}
      {cancel && <p>{cancel} </p>}
    </div>
  );
}

export default BulkUploadForm;