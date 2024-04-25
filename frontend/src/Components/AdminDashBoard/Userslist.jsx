import React, { useState, useEffect } from 'react';
import axios from 'axios';

const UserListPage = () => {
  const [users, setUsers] = useState([]);
  const [selectedUsers, setSelectedUsers] = useState([]);

  const fetchUsers = async () => {
    try {
      const response = await axios.get(`${import.meta.env.VITE_API_GOWSIC}/user/all`);
      setUsers(response.data);
    } catch (error) {
      console.error('Error fetching users:', error);
    }
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  const handleCheckboxChange = (event, user) => {
    if (event.target.checked) {
      setSelectedUsers([...selectedUsers, user]);
    } else {
      setSelectedUsers(selectedUsers.filter(selectedUser => selectedUser.employeeId !== user.employeeId));
    }
  };

  const handleSubmit = async () => {
    try {
      await Promise.all(
        selectedUsers.map(async user => {
          await axios.put(`${import.meta.env.VITE_API_GOWSIC}/user/updateRoleToTrainer`, selectedUsers.map(user => user.employeeId));
        })
      );
      await fetchUsers(); 
      setSelectedUsers([]); 
      window.location.reload(); 
    } catch (error) {
      console.error('Error updating roles:', error);
    }
  };

  return (
    <div className="container mx-auto py-8">
      <h1 className="text-3xl font-bold mb-4">User List</h1>
      <div className="overflow-x-auto">
        <table className="table-auto w-full border-collapse border border-gray-700">
          <thead>
            <tr className="bg-gray-800 text-white">
              <th className="border border-gray-700 px-4 py-2">Employee ID</th>
              <th className="border border-gray-700 px-4 py-2">First Name</th>
              <th className="border border-gray-700 px-4 py-2">Last Name</th>
              <th className="border border-gray-700 px-4 py-2">Email</th>
              <th className="border border-gray-700 px-4 py-2">Business Unit</th>
              <th className="border border-gray-700 px-4 py-2">Role</th>
              <th className="border border-gray-700 px-4 py-2">Trainer</th>
            </tr>
          </thead>
          <tbody>
            {users.map(user => (
              <tr key={user.employeeId} className="bg-gray-200">
                <td className="border border-gray-700 px-4 py-2">{user.employeeId}</td>
                <td className="border border-gray-700 px-4 py-2">{user.firstName}</td>
                <td className="border border-gray-700 px-4 py-2">{user.lastName}</td>
                <td className="border border-gray-700 px-4 py-2">{user.email}</td>
                <td className="border border-gray-700 px-4 py-2">{user.businessUnit}</td>
                <td className="border border-gray-700 px-4 py-2">{user.role}</td>
                <td className="border border-gray-700 px-4 py-2">
                  <input
                    type="checkbox"
                    onChange={e => handleCheckboxChange(e, user)}
                    checked={selectedUsers.some(selectedUser => selectedUser.employeeId === user.employeeId)}
                  />
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <button
          onClick={handleSubmit}
          disabled={selectedUsers.length === 0}
          className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
        >
          Submit
        </button>
    </div>
  );
};

export default UserListPage;
