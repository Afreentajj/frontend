import { Button, Card } from "@material-tailwind/react";
import { useRef, useState } from "react";
import axios from 'axios';
import { Link } from "react-router-dom";

function BulkUpload() {
  const [selectedFile, setSelectedFile] = useState(null);
  const fileInputRef = useRef(null);

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      const fileName = file.name.toLowerCase();
      if (fileName.endsWith('.xlsx')) {
        setSelectedFile(file);
        console.log('File selected:', file.name);
      } else {
        console.error('Please select a file with the .xlsx extension.');
      }
    }
  }

  const handleClick = () => {
    fileInputRef.current.click();
  };

  const handleUpload = () => {
    if (selectedFile) {
      const formData = new FormData();
      formData.append('file', selectedFile);

      axios.post(`${import.meta.env.VITE_API_URL}/learning-plan/upload`, formData)
        .then(response => {
          console.log('File uploaded successfully');
          // Additional handling if needed
        })
        .catch(error => {
          console.error('Error occurred while uploading file:', error);
        });
    } else {
      console.error('No file selected for upload');
    }
  };

  return (
    <Card className="w-full md:w-3/4 lg:w-2/4 xl:w-2/4 mx-auto h-2/5 flex justify-evenly items-center">
      <h1>Upload excel files</h1>
      <input type='file' id='file' name='file' accept=".xlsx" ref={fileInputRef} onChange={handleFileChange} style={{display: "none"}} />
      <Button variant="gradient" className="flex items-center gap-3 w-1/5" onClick={handleClick}>
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={2} stroke="currentColor" className="h-5 w-5">
          <path strokeLinecap="round" strokeLinejoin="round" d="M12 16.5V9.75m0 0l3 3m-3-3l-3 3M6.75 19.5a4.5 4.5 0 01-1.41-8.775 5.25 5.25 0 0110.233-2.33 3 3 0 013.758 3.848A3.752 3.752 0 0118 19.5H6.75z" />
        </svg>
        Upload Files
      </Button>
      {selectedFile && <p className="absolute top-40 mt-3 ml-2 text-sm">Selected File: {selectedFile.name}</p>}
      
        <Link to="/dashboard/admin">
      <Button variant="gradient" className="flex-grow items-center gap-3 w-1/5" onClick={handleUpload}>
        Submit
      </Button>
      </Link>
     
    </Card>
  );
}
export default BulkUpload;