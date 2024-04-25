import React from "react";
import {
  Button,
  Dialog,
  DialogHeader,
  DialogBody,
 
} from "@material-tailwind/react";
import BulkUploadForm from "./Bulkupload";
 
export function Bulkuploaddialog({BulkuploadOpen , setShowBulkUpload}) {
 
 
  return (
    <div>
      <Dialog open={BulkuploadOpen} >
        <DialogHeader>Bulkupload Users</DialogHeader>
        <DialogBody>
        <BulkUploadForm setShowBulkUpload={setShowBulkUpload}/>
        </DialogBody> 
       
      </Dialog>
    </div>
  );
}