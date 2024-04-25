import React, { useEffect } from 'react';
import useAuth from '../../Hooks/useAuth';
import axios from '../../Api/axios';
 
const Userdailoge = ({ setIsUserDialogOpen }) => {
    const { auth } = useAuth();
 
    function handleUserDialog() {
        setIsUserDialogOpen(false);
    }
 
    const fetchUserDetails = () => {
        axios.get((`${import.meta.env.VITE_API_GOWSIC}/user/all`))
            .then((response) => {
                console.log(response.data);
            })
            .catch((error) => {
                console.log(error);
            });
    };
 
    useEffect(() => {
        fetchUserDetails();
    }, []);
 
    return (
        <div className="fixed top-0 left-0 w-full h-full flex items-center justify-center bg-gray-800 bg-opacity-50">
            <div className="bg-white p-8 rounded shadow-md">
                <h2 className="text-xl font-bold mb-4">Batches  {auth.username}</h2>
                <p className="mb-4">This is the dialog content for managing batches.</p>
                <button onClick={handleUserDialog} className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600">Close</button>
            </div>
        </div>
    );
};
 
export default Userdailoge;