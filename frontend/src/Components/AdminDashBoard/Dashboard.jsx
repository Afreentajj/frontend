import React, { useState } from "react";
import { Sidebar } from "./Sidebar";
import { ProfilePage } from "./ProfilePage";
import { BulkUploadForm } from "./Bulkupload";
import { Bulkuploaddialog } from "./Bulkuploaddialog";
import { LogoutDialog } from "./LogoutDialog";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import UserListPage from "./Userslist";
import useAuth from "../../Hooks/useAuth";
import { Link } from "react-router-dom";
import background from "../../Assets/background.png";





import {
  Card,
  CardBody,
  Typography,
  Button,
} from "@material-tailwind/react";

const Dashboard = () => {
  const [viewProfile, setViewProfile] = useState(false);
  const [BulkuploadOpen, setBulkuploadOpen] = useState(false);
  const [showLogoutPopup, setShowLogoutPopup] = useState(false);
  const { auth } = useAuth();

  return (
    <div className="flex flex-row w-full h-screen background" style={{userSelect:"none"}}>
      
      
      <div className=" w-half h-full">
      
        <Sidebar
          setViewProfile={setViewProfile}
          setBulkuploadOpen={setBulkuploadOpen}
          BulkuploadOpen={BulkuploadOpen}
          setShowLogoutPopup={setShowLogoutPopup}
          showLogoutPopup={showLogoutPopup}
        />
        </div>
        <div className="flex flex-col w-full h-full" style={{ userSelect: "none" }}>
       
          <div className="h-[70px] text-center shadow-md ">
            
          
            <h1 className="font-bold text-4xl relative left-[-50px] top-3 ">  Welcome {auth.username} !</h1>
          </div>
          
          <div className="flex flex-row gap-3">
            
          {/* First Card */}
  <Card className="mt-6 w-[calc(33.33%-1rem)] max-h-25 border-l-[4px] " style={{ backgroundImage: `url(${background})`, backgroundSize: "cover", backgroundPosition: "center" }}>
    <CardBody>
      <Typography variant="h5" color="blue-gray" className="mb-2">
        <Link to="/learnplan">
          <Button >ADD PLAN</Button>
        </Link>
      </Typography>
      <Typography></Typography>
    </CardBody>
  </Card>

  {/* Second Card */}
  <Card className="mt-6 w-[calc(33.33%-1rem)] max-h-25 border-l-[4px]  "style={{ backgroundImage: `url(${background})`, backgroundSize: "cover", backgroundPosition: "center" }}>
  <CardBody>
    <Typography variant="h5" color="blue-gray" className="mb-2">
      <Link to="/course">
        <Button >  ADD COURSE  </Button>
      </Link>
    </Typography>
    <Typography></Typography>
  </CardBody>
</Card>


  {/* Third Card */}
  <Card className="mt-6 w-[calc(33.33%-1rem)] max-h-25 border-l-[4px]" style={{ backgroundImage: `url(${background})`, backgroundSize: "cover", backgroundPosition: "center" }}>
    <CardBody>
      <Typography variant="h5" color="blue-gray" className="mb-2">
        <Link to="/bulkcourse">
          <Button>BULK UPLOAD COURSE</Button>
        </Link>
      </Typography>
      <Typography></Typography>
    </CardBody>
  </Card>
          </div>
      
        </div>

        <div className="flex flex-col items-center justify-between flex-grow gap-0">
          {viewProfile && <ProfilePage />}
        </div>
        {BulkuploadOpen && (
          <div className="flex flex-col flex-grow">
            <Bulkuploaddialog setBulkuploadOpen={setBulkuploadOpen} />
            <BulkUploadForm setBulkuploadOpen={setBulkuploadOpen} />
          </div>
        )}
        {showLogoutPopup && (
          <div className="flex flex-col flex-grow">
            <LogoutDialog
              setShowLogoutPopup={setShowLogoutPopup}
              showLogoutPopup={showLogoutPopup}
            />
          </div>
        )}
        <Routes>
          <Route path="/dashboard/admin/userlist" element={<UserListPage />} />
        </Routes>
      
    </div>
  );
};

export default Dashboard;
