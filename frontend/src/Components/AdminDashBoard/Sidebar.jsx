import React, { useState } from "react";
import logo from "../../Assets/logo.png";
import {
  Card,
  Typography,
  List,
  ListItem,
  ListItemPrefix,
} from "@material-tailwind/react";
import {
  PresentationChartBarIcon,
  ChevronRightIcon,
  UserCircleIcon,
  Cog6ToothIcon,
  PowerIcon,
} from "@heroicons/react/24/solid";
import { Bulkuploaddialog } from "./Bulkuploaddialog";
import UserList from "./Userslist";
import { Link } from "react-router-dom";
import useAuth from "../../Hooks/useAuth";

export function Sidebar({ setViewProfile, setShowLogoutPopup }) {
  const [showBulkUpload, setShowBulkUpload] = useState(false);
  const [showUserList, setShowUserList] = useState(false);


  const handleOpenBulkUploadForm = () => {
    setShowBulkUpload(true);
  };

  const handleViewProfile = () => {
    setViewProfile((prev) => !prev);
  };

  const handleLogout = () => {
    setShowLogoutPopup(true);
  };

  const handleToggleUserList = () => {
    setShowUserList((prev) => !prev);
  };
  const { auth } = useAuth();
  return (
    <>
      <Card className=" w-full max-w-[20rem] p-4 shadow-xl shadow-blue-gray-900/5 bg-black text-white h-full">
        <List>
          <img src={logo} alt="logo" className="white" />
          <Typography
            color="blue-gray"
            className="mr-auto text-white font-normal"
          >
            {/* Welcome {auth.username} */}
          </Typography>
          <ListItem >
            <ListItemPrefix>
              <PresentationChartBarIcon color="white" className="h-5 w-5" />
            </ListItemPrefix>
            <Typography
              color="blue-gray"
              className="mr-auto text-white font-normal"
            >
              Dashboard
            </Typography>
          </ListItem>
          <ListItem
           
            onClick={handleOpenBulkUploadForm}
          >
            <ListItemPrefix>
              <ChevronRightIcon
                strokeWidth={3}
                color="white"
                className="h-3 w-5"
              />
            </ListItemPrefix>
            <Typography
              color="blue-gray"
              className="mr-auto text-white font-normal"
            >
              Bulk Upload Users
            </Typography>
          </ListItem>
         <Link to="/lms/batches">
         <ListItem >
            <ListItemPrefix>
              <PresentationChartBarIcon color="white" className="h-5 w-5" />
            </ListItemPrefix>
            <Typography
              color="blue-gray"
              className="mr-auto text-white font-normal"
            >
              Batch List
            </Typography>
          </ListItem>
         </Link>
          <ListItem >
            <Link to="/dashboard/admin/userlist" className="flex items-center ">
              <UserCircleIcon color="white" className="h-5 w-5 mr-4" />
              <Typography
                color="blue-gray"
                className="mr-auto text-white font-normal"
              >
                Users List
              </Typography>
            </Link>
          </ListItem>
          <ListItem onClick={handleViewProfile}>
            <ListItemPrefix>
              <UserCircleIcon color="white" className="h-5 w-5" />
            </ListItemPrefix>
            <Typography
              color="blue-gray"
              className="mr-auto text-white font-normal"
            >
              Profile
            </Typography>
          </ListItem>
          <ListItem  >
          <Link to="/change-password" className="flex items-center ">
            <ListItemPrefix>
              <Cog6ToothIcon color="white" className="h-5 w-5" />
            </ListItemPrefix>
            <Typography
              color="blue-gray"
              className="mr-auto text-white font-normal"
            >
             Change Password
            </Typography>
            </Link>
          </ListItem>
          <ListItem onClick={handleLogout}>
            <ListItemPrefix>
              <PowerIcon color="white" className="h-5 w-5" />
            </ListItemPrefix>
            <Typography
              color="blue-gray"
              className="mr-auto text-white font-normal flex-grow"
            >
              Log Out
            </Typography>
          </ListItem>
        </List>
      </Card>
      {showBulkUpload && (
        <Bulkuploaddialog
          BulkuploadOpen={showBulkUpload}
          setShowBulkUpload={setShowBulkUpload}
        />
      )}
      {showUserList && <UserList />}
    </>
  );
}
