import { UserPlusIcon } from "@heroicons/react/24/solid";
import {
  Card,
  CardHeader,
  Typography,
  Button,
  CardBody,
} from "@material-tailwind/react";
// eslint-disable-next-line no-unused-vars
import React from "react";
import { useState, useEffect } from "react";
import Modal from "../Components/Modal";
import EmployeeTable from "../Components/EmployeeTable";
import { TABLE_HEAD } from "../Services/EmployeeData.js";
import SearchBar from "../Components/SearchBar";
import { fetchEmployees, sendSelectedUsers } from "../Services/allEmployee.js";
import { modalAddTrainees } from "../Data/ModalData.jsx";
import { modalAddTraineesExcel } from "../Data/ModalData.jsx";
import FileModal from "../Components/FileModal.jsx";
const UsersList = () => {
  const table = {
    height: "490px",
  };

  const [employees, setEmployees] = useState([]);
  const [rows, setRows] = useState(employees);
  const [selectedRows, setSelectedRows] = useState({});
  const [clearSearch, setClearSearch] = useState(false);
  const [open, setOpen] = useState(false);
  const [openExcel, setOpenExcel] = useState(false);
  const [fetch, setFetch] = useState(false);
  const [error,setError]=useState(null);
  const [loading, setLoading] = useState(true); // State variable to track loading state
  const handleOpen = () => {
    if (open) {
      // setFetch((prev) => !prev);
      handleAddToBatch();
      setFetch((prev) => !prev);
      // window.location.reload();
    }
    setOpen((prev) => !prev);
  };
  const handleClose = () => {
    setOpen((prev) => !prev);
  };
  const handleOpenExcel = () => setOpenExcel(!openExcel);
  const handleExcelClose = () => {
    setOpenExcel((prev) => !prev);
  };
  const handleCheckboxChange = (employeeId) => {
    setSelectedRows((prevSelectedRows) => ({
      ...prevSelectedRows,
      [employeeId]: !prevSelectedRows[employeeId],
    }));
  };
  const id = sessionStorage.getItem("id");
  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await fetchEmployees(id);
        if(data){
          setEmployees(data);
          setLoading(false); // Set loading to false when data is fetched successfully
          setError(null); // Clear any previous errors
        }else{
          throw new Error("Couldn't fetch all employees")
        }
      
      } catch (error) {
        console.error("Error fetching trainees:", error);
        setError("Error fetching employees"); // Set error message
        setLoading(false); // Set loading to false even if there is an error
      }
    };

    fetchData();
  }, [id, fetch]);

  // Update rows state when trainees change
  useEffect(() => {
    setRows(employees);
  }, [employees, fetch]);

  const handleAddToBatch = async () => {
    const selectedUsers = Object.keys(selectedRows).filter(
      (userId) => selectedRows[userId]
    );
    try {
      await sendSelectedUsers(selectedUsers, id);
      setSelectedRows({});
      setFetch((prev) => !prev);
      setClearSearch(true); // Set clearSearch flag to true to clear search bar
    } catch (error) {
      console.error("Error adding users to batch:", error);
    }
  };

  return (
    <Card className="h-full w-full mt-2">
      <CardHeader floated={false} shadow={false} className="rounded-none">
        <div className="mb-8 flex items-center justify-between gap-8">
          <div>
            <Typography variant="h5" color="blue-gray">
              Employees list
            </Typography>
            <Typography color="gray" className="mt-1 font-normal">
              Select The employees you want to add to this batch
            </Typography>
          </div>
        </div>
        <div className="flex flex-col items-center justify-between gap-4 md:flex-row">
          <SearchBar
            setRows={setRows}
            TABLE_ROWS={employees}
            setSelectedRows={setSelectedRows}
            rows={rows}
            clearSearch={clearSearch} // Pass clearSearch flag to the SearchBar
          />
          <div className="flex shrink-0 flex-col gap-2 sm:flex-row">
            <Button
              className="flex items-center gap-3"
              size="sm"
              onClick={handleOpen}
              disabled={Object.values(selectedRows).every((value) => !value)} // Disable button if no employees are selected
            >
              <UserPlusIcon strokeWidth={2} className="h-4 w-4" /> Add Trainees
              To Batch
            </Button>

            <Modal
              handleOpen={handleOpen}
              open={open}
              handleClose={handleClose}
              data={modalAddTrainees}
            />
            <Button
              className="flex items-center gap-3"
              size="sm"
              onClick={handleOpenExcel}
            >
              <UserPlusIcon strokeWidth={2} className="h-4 w-4" /> Add Trainees
              Through Excel
            </Button>
            <FileModal
              handleOpen={handleOpenExcel}
              open={openExcel}
              handleClose={handleExcelClose}
              data={modalAddTraineesExcel}
              setFetch={setFetch}
            />
          </div>
        </div>
      </CardHeader>
      <CardBody className="overflow-scroll  px-0" style={table}>
        <EmployeeTable
          TABLE_HEAD={TABLE_HEAD}
          rows={rows}
          selectedRows={selectedRows}
          handleCheckboxChange={handleCheckboxChange}
          setSelectedRows={setSelectedRows}
          error={error}
          loading={loading}
        />
      </CardBody>
    </Card>
  );
};

export default UsersList;