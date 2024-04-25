import {
  VerticalTimeline,
  VerticalTimelineElement,
} from "react-vertical-timeline-component";
import "react-vertical-timeline-component/style.min.css";
import React, { useEffect, useState } from "react";
import { GiBookshelf } from "react-icons/gi";
import {
  Button,
  Card,
  CardBody,
  CardHeader,
  Progress,
  IconButton,
  Tooltip,
  TimelineConnector,
  TimelineHeader,
  TimelineIcon,
  TimelineItem,
  Typography,
} from "@material-tailwind/react";
import { IoIosPeople } from "react-icons/io";
import {
  ArchiveBoxIcon,
  BellIcon,
  CurrencyDollarIcon,
} from "@heroicons/react/24/solid";
import { TopicsTimeLine } from "../Components/TopicsTimeLine";
import { LiaBookSolid } from "react-icons/lia";
import AnimatedProgressProvider from "../Components/AnimatedProgressProvider";
import { easeQuadInOut } from "d3-ease";
import { Link } from "react-router-dom";
import { Courses } from '../Data/Courses'


const colorCodes = ["ff70a6", "ff9770", "ffd670", "e9ff70", "70d6ff"];


const LearningPlan = () => {





  return (
    <div className="w-full">
      <VerticalTimeline lineColor="black" layout="1-column-left">
        {Courses.map((data, key) => {
          //  setSelected(colorCodes[Math.floor(Math.random() * colorCodes.length)])
          //    console.log(data.colorCodes.map(()=>))
          //   console.log(selectedColor)
          return (
            <VerticalTimelineElement
              key={key}
              // style={{ width: "900px", marginLeft:"0" }}
              className=" vertical-timeline-element--work w-full p-2 mr-52 min-w-[300px]"
              // contentStyle={{ background: "rgb(33, 150, 243)", color: "#5cd561" , padding:"px"}}
              contentArrowStyle={{
                borderRight: "7px solid  rgb(33, 150, 243)",
                width: "300px",
              }}
              // date="2011 - 2022"
              iconStyle={{ background: "rgb(33, 150, 243)", color: "#5cd561" }}

            // icon={<WorkIcon />}
            >
              <Card className={`min-h-52 max-h-96 w-[900px] flex-row  mr-52 bg-[#A6A6A6]`} >
                <CardHeader
                  shadow={false}
                  floated={false}
                  className="m-0 w-3/5 shrink-0 rounded-r-none p-3 overflow-y-auto bg-[#F2F2F2]"
                >
                  <TopicsTimeLine topics={data.topics} />
                </CardHeader>

                <CardBody className="relative flex flex-col justify-between w-full md:w-4/5">
                  <AnimatedProgressProvider
                    valueStart={0}
                    valueEnd={data.progress}
                    duration={0.5}
                    easingFunction={easeQuadInOut}
                    repeat
                  >
                    {value => (
                      <Progress className="absolute -m-6" value={value} color="green" />
                    )}
                  </AnimatedProgressProvider>
                  <div className="flex flex-col justify-center items-center">
                    <Card className="p-4 border border-blue-gray-100 bg-[#F2F2F2]">
                      <Typography variant="h4" color="blue-gray" className="mb-2">
                        {data.courseName}
                      </Typography>
                      <Typography color="gray" className="mb-8 font-normal">
                        {data.description}
                      </Typography>
                      <Typography color="gray" variant="paragraph">
                        Trainer: {data.trainerName}
                      </Typography>
                      <Typography color="gray" variant="paragraph">
                        Duration: {data.startDate} to {data.endDate}
                      </Typography>
                    </Card>

                    <div className="group mt-8 inline-flex flex-wrap items-center gap-3 ">
                      <Tooltip content="Course Resources">
                        <Link to="/lms/batches/batchDetails/learningPlan/resources">
                          <IconButton className="hover:border-gray-900/10 hover:bg-black-900/10 hover:!opacity-100 group-hover:opacity-70">
                            <LiaBookSolid className="text-[1.5rem]" />
                          </IconButton>
                        </Link>
                      </Tooltip>
                      <Tooltip content="Trainee Progress CourseWise[TRAINER,ADMIN]">
                        <Link to="/lms/batches/batchDetails/learningPlan/batchWiseProgress">
                          <IconButton className="hover:border-gray-900/10 hover:bg-gray-900/10 hover:!opacity-100 group-hover:opacity-70">
                            <IoIosPeople className="text-[1.5rem]" />
                          </IconButton>
                        </Link>
                      </Tooltip>
                      <Tooltip content="Course Resource">
                        <IconButton className="hover:border-gray-900/10 hover:bg-gray-900/10 hover:!opacity-100 group-hover:opacity-70">
                          <LiaBookSolid className="text-[1.5rem]" />
                        </IconButton>
                      </Tooltip>
                      <Tooltip content="Course Resource">
                        <IconButton className="hover:border-gray-900/10 hover:bg-gray-900/10 hover:!opacity-100 group-hover:opacity-70">
                          <LiaBookSolid className="text-[1.5rem]" />
                        </IconButton>
                      </Tooltip>
                      <Tooltip content="Course Resource">
                        <IconButton className="hover:border-gray-900/10 hover:bg-gray-900/10 hover:!opacity-100 group-hover:opacity-70">
                          <LiaBookSolid className="text-[1.5rem]" />
                        </IconButton>
                      </Tooltip>

                    </div>


                  </div>
                </CardBody>
              </Card>
            </VerticalTimelineElement>
          );
        })}
      </VerticalTimeline>
    </div>
  );
};

export default LearningPlan;
