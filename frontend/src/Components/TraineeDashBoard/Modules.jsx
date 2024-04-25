import {
  Card,
  CardBody,
  CardFooter,
  Typography,
  Button,
} from "@material-tailwind/react";
import { useState } from "react";
 
export function Modules({CardName,setIsUserDialogOpen}) {
  
  const handleUserDialog = () =>{
    setIsUserDialogOpen(true)
    
   
  }
  return (
    <Card className="mt-6 w-100 border-l-[4px] border-[#4E73DF]">
      <CardBody>
        <Typography variant="h5"  className="mb-2">
         {CardName}
        </Typography>
        <Typography>
         
        </Typography>
      </CardBody>
      <CardFooter>
        <Button onClick={handleUserDialog} ></Button>
      </CardFooter>
    </Card>
  );
}