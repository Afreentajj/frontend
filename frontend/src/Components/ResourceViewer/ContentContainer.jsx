import TopNavigation from "./TopNavigation";
import { BsPlusCircleFill } from "react-icons/bs";
// import { useState } from 'react';
import { resources } from "../../Data/Courses";
import ResourceRenderer from "./ResourceRenderer";
import { viewerContext } from "./viewerContext";
import { useContext } from "react";
const ContentContainer = () => {
  return (
    <div className="content-container h-[870px] bg-gray-300 dark:bg-[#313338] dark:text-[#8f959e]">
      <TopNavigation />
      <div className="flex flex-col justify-center items-center">
        <div className=" min-h-screen ">
          <ResourceRenderer resources={resources} reqResource={1} />
        </div>
      </div>
    </div>
  );
};

const Post = ({ name, timestamp, text }) => {
  const seed = Math.round(Math.random() * 100);
  return (
    <div className={"post"}>
      <div className="avatar-wrapper">
        <img
          src={`https://avatars.dicebear.com/api/open-peeps/${seed}.svg`}
          alt=""
          className="avatar"
        />
      </div>

      <div className="post-content">
        <p className="post-owner">
          {name}
          <small className="timestamp">{timestamp}</small>
        </p>
        <p className="post-text">{text}</p>
      </div>
    </div>
  );
};

export default ContentContainer;
