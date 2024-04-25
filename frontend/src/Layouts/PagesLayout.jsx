import React from 'react'
import {Outlet} from 'react-router-dom'
import { Footer } from '../Components/Footer'
import { ComplexNavbar } from '../Components/ComplexNavbar'
import BreadCrumbs from "../Components/BreadCrumbs";
const PagesLayout = () => {
  return (
  <main className='App'>
    <ComplexNavbar />
    <BreadCrumbs />
    {/* <header>Pages Layout</header> */}
    <Outlet/>
    {/* <Footer/> */}
  </main>
  )
}

export default PagesLayout