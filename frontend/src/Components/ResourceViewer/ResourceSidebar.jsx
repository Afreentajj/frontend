import { BsPlus, BsFillLightningFill, BsGearFill, BsDashCircle } from "react-icons/bs";
import { FaFire, FaPoo } from "react-icons/fa";
import { MdMenuOpen } from "react-icons/md";
import {
  RiDashboard2Fill,
  RiInboxFill,
  RiFileMarkFill,
  RiFolderVideoFill,
} from "react-icons/ri";
import { RxDividerHorizontal } from "react-icons/rx";
import { Button, Tooltip } from "@material-tailwind/react";
import { useContext, useEffect, useState } from "react";
import { CourseCompletionContext } from "./CourseContext";
import { BiSolidDockLeft } from "react-icons/bi";
import { GiQuillInk } from "react-icons/gi";
import { PiNotepadBold } from "react-icons/pi";

const ResourceSidebar = ({ courses,docked,setDocked,viewProgress,setViewProgress}) => {
  const { courseCompletion, setCourseCompletion } = useContext(
    CourseCompletionContext
  );
  const [currentCourseIndex, setCurrentCourseIndex] = useState(0);
  const [hasNext, setHasNext] = useState(true);
  const [hasPrevious, setHasPrevious] = useState(false);

  useEffect(() => {
    setHasNext(currentCourseIndex < courses.length - 1);
    setHasPrevious(currentCourseIndex > 0);
    console.log(courseCompletion);
  }, [currentCourseIndex, courses]);

  const nextCourse = () => {
    if (hasNext) {
      const nextIndex = currentCourseIndex + 1;
      if (courses[nextIndex]) {
        setCourseCompletion(courses[nextIndex]);
        setCurrentCourseIndex(nextIndex);
      }
    }
  };

  const previousCourse = () => {
    if (hasPrevious) {
      const previousIndex = currentCourseIndex - 1;
      if (courses[previousIndex]) {
        setCourseCompletion(courses[previousIndex]);
        setCurrentCourseIndex(previousIndex);
      }
    }
  };

  return (
    <div className="fixed left-0  w-16 h-96 justify-center mt-40 bg-[#F2F2F2] dark:bg-gray-900 z-20 ">
      <div className="flex flex-col justify-center items-center h-full ">
        <SideBarIcon icon={<RiDashboard2Fill size="30" />} text="Progress" onClick={()=>setViewProgress(!viewProgress)}/>
        <Divider />
        <SideBarIcon icon={<RiInboxFill size="25" />} text="All Resources" />
        <SideBarIcon
          icon={<BsDashCircle  size="24"/>}
          text="Incomplete Resources"
        />
        <SideBarIcon
          className="mb-0 "
          icon={<PiNotepadBold size="24" />}
          text="Notes"
        />

        <br />
        <br />
        <br />

        <SideBarIcon
          icon={<BiSolidDockLeft size="25"/>}
          text="Dock"
          onClick={() => setDocked(!docked)}
        />

        <Divider />
      </div>

      <Tooltip content="Next Course" placement="right-end">
        <Button
          className="p-2 w-14 ml-1 mb-1 mt-3 dark:text-green-700 dark:border-green-200 hover:dark:border-gray-200 hover:dark:text-gray-100 hover:bg-green-100 hover:text-gray-100"
          variant="outlined"
          onClick={nextCourse}
          disabled={!hasNext}
        >
          {">>"}
        </Button>
      </Tooltip>
      <Tooltip content="Previous Course" placement="right-end">
        <Button
          className="p-2 w-14 ml-1 mt-1 dark:text-green-700 dark:border-green-200 hover:dark:border-gray-200 hover:dark:text-gray-100 hover:bg-green-100 hover:text-gray-100"
          variant="outlined"
          onClick={previousCourse}
          disabled={!hasPrevious}
        >
          {"<<"}
        </Button>
      </Tooltip>
    </div>
  );
};

const SideBarIcon = ({ icon, text = "tooltip 💡", onClick }) => (
  <div className="sidebar-icon group" onClick={onClick}>
    {icon}
    <span class="sidebar-tooltip group-hover:scale-100">{text}</span>
  </div>
);

const Divider = () => (
  <hr className="w-7 h-px my-0 bg-green-200 border-0 dark:bg-green-700" />
);

export default ResourceSidebar;
