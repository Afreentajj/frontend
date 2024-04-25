import React, { useState } from "react";
import { FaRegCalendarMinus, FaTasks } from "react-icons/fa";
import { SiFuturelearn } from "react-icons/si";
import useAuth from "../../Hooks/useAuth";
import axios from "axios";
import {
  Card,
  CardBody,
  CardFooter,
  Typography,
  Button,
} from "@material-tailwind/react";
import { Modules } from "./Modules"; // Import Modules component
import Userdailoge from "./Userdailoge"; // Import Userdailoge component
import { Link, useNavigate } from "react-router-dom";

const Page = () => {
  const { auth } = useAuth();
  const [isUserDialogOpen, setIsUserDialogOpen] = useState(false);

  const fetUserDetails = () => {
    axios
      .get(`${import.meta.env.VITE_API_GOWSIC}/user/all`)
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  fetUserDetails();

  return (
    <div className="px-[25px] pt-[25px] bg-[#F8F9FC] pb-[40px]">
      <div className="flex items-center justify-between">
        <h1 className="text-[28px] leading-[34px] font-normal text-[#5a5c69] cursor-pointer">
          Dashboard
        </h1>
      </div>
      <div className="grid grid-cols-3 gap-6 mt-[25px] pb-[15px] border-1-[4px] border-[#4E73DF]">

        <Card className="mt-6 w-96 hover:shadow-lg transform hover:scale-[103%] transition duration-300 ease-out">
          <CardBody>
            <Typography variant="h5" color="blue-gray" className="mb-2 ">
              Learning Resources
            </Typography>
            <Typography></Typography>
          </CardBody>
          <CardFooter className="pt-0">
            <Link to="/learningresource">
              {" "}
              {/* Specify the path you want to navigate to */}
              <Button>Click</Button>
            </Link>
          </CardFooter>
        </Card>
      </div>

      {/* Render Userdailoge if isUserDialogOpen is true */}
      {isUserDialogOpen && (
        <Userdailoge setIsUserDialogOpen={setIsUserDialogOpen} />
      )}
    </div>
  );
};

export default Page;
