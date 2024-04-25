import React from "react";
import {
  Button,
  Dialog,
  DialogHeader,
  DialogBody,
 
} from "@material-tailwind/react";

import Logoutpage from "./Logoutpage";
 
export function LogoutDialog({showLogoutPopup , setShowLogoutPopup}) {
 
 
  return (
    <div className="">
      <Dialog open={showLogoutPopup} >
        <DialogHeader>Logout</DialogHeader>
        <DialogBody>
        <Logoutpage
        setShowLogoutPopup={setShowLogoutPopup}
        />
        </DialogBody>
       
      </Dialog>
    </div>
  );
}