import { Button } from '@material-tailwind/react'
import React from 'react'
import { Link } from 'react-router-dom'
import Dashboardview from '../Components/TraineeDashBoard/Dashboardview'
import Sidebar from '../Components/TraineeDashBoard/Sidebar'
import Page from '../Components/TraineeDashBoard/Page'
import { Outlet } from 'react-router-dom'
const TraineeDashBoard = () => {
  return (
    <>


      <div>
        <div className="">
          <div className="flex  ">
            <div className="basis-[12%] h-[100vh]">
              <Sidebar />
            </div>
            <div className="basis-[88%] border  h-[100vh]">
              <Dashboardview />
              <div>
                <Page />
              </div>
            </div>
          </div>
        </div>
      </div>











      <div className=' flex justify-center items-center w-full h-screen'>

        <h1 className='text-4xl'>Trainee Dash Board</h1>
        <Link to="/resources">
          <Button >Resources</Button>
        </Link>
        <br />
        <Link to="/batch-list">
          <Button >batch list</Button>
        </Link>
      </div>

    </>
  )
}

export default TraineeDashBoard