import React, { useState } from "react";

 const  Logoutpage = ({ setShowLogoutPopup }) => {
  
  const confirmLogout = () => {
    console.log("Logout confirmed");
    setShowLogoutPopup(false);
  };

  const cancelLogout = () => {
    console.log("Logging out...");
    window.location.href = '/login';
  };
  return (
    <div className="logout-popup bg-white">
      <p>Are you sure you want to logout?</p>
      <button onClick={cancelLogout} className="text-white font-bold py-2 px-4 mb-4 bg-blue-500 hover:bg-blue-700 rounded ml-2">Yes</button>
      <button onClick={confirmLogout} className="text-white font-bold py-2 px-4 mb-4 bg-blue-500 hover:bg-blue-700 rounded ml-2">No</button>
    </div>
  );
};
export default Logoutpage;