import React from "react";
import { Card, Typography } from "@material-tailwind/react";
import useAuth from "../../Hooks/useAuth";
export function ProfilePage() {
  const { auth } = useAuth();

  return (
    <Card className="absolute top-0 right-0 max-w-md mx-auto mt-10 p-6 shadow-xl bg-black">
      <div className="text-center" color="white">
        <img
          src="profile.jpg"
          alt="Profile"
          className="mx-auto rounded-full h-24 w-24 mb-4"
        />
        <Typography variant="h6" color="white">
          {auth.username}
        </Typography>
        <Typography variant="subtitle1" color="white">
        {auth.employeeID}
        </Typography>
        <Typography variant="subtitle1" color="white">
          Email: {auth.email}
        </Typography>
      </div>
    </Card>
  );
}

export default ProfilePage;
