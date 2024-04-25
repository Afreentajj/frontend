import React from 'react';
import { FaTachometerAlt, FaLayerGroup, FaChevronRight } from "react-icons/fa";
import { Link } from 'react-router-dom';

import logo from '../../Assets/logo.png';

const Sidebar = () => {
    
    return (
        <div className='bg-black px-[10px] h-screen'>
            <div className="flex items-center justify-center py-6">
                <img src={logo} alt="" className="h-25 w-auto text-white" />
            </div>
            <div className='flex items-center gap-[15px] py-[20px] cursor-pointer'>
                <FaTachometerAlt color='white' />
                <Link to="/" className='text-[17px] leading-[20px] font-bold text-white'>Dashboard</Link>
            </div>
            <div className='pt-[15px]'>
                <Link to="/lms/batches" className='flex items-center justify-between gap-[10px] py-[15px] cursor-pointer'>
                    <FaLayerGroup color='white' />
                    <p className='text-[17px] leading-[20px] font-normal text-white'>Batch List</p>
                    <FaChevronRight color='white' />
                </Link>
            </div>
        </div>
    )
}

export default Sidebar;
