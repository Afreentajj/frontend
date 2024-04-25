import React, { useState } from 'react';
import { FaRegCalendarMinus, FaTasks } from "react-icons/fa";
import { SiFuturelearn } from "react-icons/si";
import useAuth from '../../Hooks/useAuth';
import axios from 'axios';
import {
  Card,
  CardBody,
  CardFooter,
  Typography,
  Button,
} from "@material-tailwind/react";
import { Modules } from './Modules'; // Import Modules component
import Userdailoge from './Userdailoge'; // Import Userdailoge component
 
const Page = () => {
    const { auth } = useAuth();
    const [isUserDialogOpen, setIsUserDialogOpen] = useState(false);
 
    const fetUserDetails= () =>{
        axios.get((`${import.meta.env.VITE_API_GOWSIC}/user/all`)).then((response)=>{
            console.log(response.data)
        }).catch((error)=>{
            console.log(error);
        })
    }
 
    fetUserDetails();
 
    return (
        <div className='px-[px] pt-[25px] bg-[#F8F9FC] pb-[40px]'>
            <div className='flex items-center justify-between'>
                <h1 className='text-[28px] leading-[34px] font-normal text-[#5a5c69] cursor-pointer'>welcome {auth.username}! </h1>
            </div>
            <div className='grid grid-cols-3 gap-6 mt-[25px] pb-[15px]'>
                
               
                {/* User Batch */}
                <Modules CardName="User Batch" setIsUserDialogOpen={setIsUserDialogOpen} />
                
                 {/* Attendance */}
                 <Modules CardName="Attendance" icon={<FaRegCalendarMinus fontSize={28} />} />
               
                 {/* Assignments */}
               
                <Modules CardName="Assignments" icon={<FaTasks fontSize={28} />} />
            </div>
 
            {/* Render Userdailoge if isUserDialogOpen is true */}
            {isUserDialogOpen && <Userdailoge  setIsUserDialogOpen={setIsUserDialogOpen}/>}
        </div>
    );
};
 
export default Page;