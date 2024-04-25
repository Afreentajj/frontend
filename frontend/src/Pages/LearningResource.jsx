import React, { useEffect, useRef, useState } from "react";
import ResourceSidebar from "../Components/ResourceViewer/ResourceSidebar";
import ContentContainer from "../Components/ResourceViewer/ContentContainer";
import TopicList from "../Components/ResourceViewer/TopicList";
import "../Components/ResourceViewer/index.css";
import useDarkMode from "../Components/ResourceViewer/useDarkMode";
import { viewerContext } from "../Components/ResourceViewer/viewerContext";
import { CompletionContext } from "../Components/ResourceViewer/CompletionContext";
import { CourseCompletionContext } from "../Components/ResourceViewer/CourseContext";
const LearningResource = () => {
  const [darkTheme, setDarkTheme] = useDarkMode();
  const courseId = "1";
  const batchId = "1";
  const topicsId = "2";
  const [view, setView] = useState();
  const [completion, setCompletion] = useState();
  const [courseCompletion, setCourseCompletion] = useState({courseId:1,progress:12});
  const [docked,setDocked] = useState(false);
  const [viewProgress,setViewProgress] = useState(false);

  //TODO axios call to get courses list and course completion
  const courseProgress = [
    {
      courseId: 1,
      progress: 30,
    },
    {
      courseId: 2,
      progress: 30,
    },
    {
      courseId: 3,
      progress: 49,
    },
    {
      courseId: 4,
      progress: 49,
    },
    {
      courseId: 5,
      progress: 42,
    },
  ];
  // useEffect(()=>{
  //   setCourseCompletion(courseProgress)
  // },[courseProgress])

  return (
    <div className="flex bg-[#F2F2F2] dark:bg-gray-900 ">
      <CourseCompletionContext.Provider
        value={{ courseCompletion, setCourseCompletion }}
      >
        <viewerContext.Provider value={{ view, setView }}>
          <CompletionContext.Provider value={{ completion, setCompletion }}>
            <ResourceSidebar courses={courseProgress} docked={docked} setDocked={setDocked} viewProgress={viewProgress} setViewProgress={setViewProgress}/>

            <TopicList courseId={courseCompletion.courseId} docked={docked} setDocked={setDocked} viewProgress={viewProgress}/>

            <ContentContainer courseId={courseId} batch={batchId} />
          </CompletionContext.Provider>
        </viewerContext.Provider>
      </CourseCompletionContext.Provider>
    </div>
  );
};

export default LearningResource;
