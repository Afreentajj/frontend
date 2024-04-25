import React, { useContext, useEffect, useState } from "react";
import { viewerContext } from "./viewerContext";
import {
  Card,
  CardHeader,
  CardBody,
  CardFooter,
  Typography,
  Button,
  IconButton,
  Tooltip,
} from "@material-tailwind/react";
import { SiMicrosoftsharepoint } from "react-icons/si";

import CustomYouTubePlayer from "./CustomYoutubePlayer";
import { CompletionContext } from "./CompletionContext";
import { RiFilePdf2Fill, RiFilePpt2Fill, RiFileWord2Fill, RiLinkM, RiVideoFill, RiYoutubeFill } from "react-icons/ri";
import { GiQuillInk } from "react-icons/gi";
import { ImNewTab } from "react-icons/im";
interface Props {
  // Define props interface here
}

const ResourceRenderer: React.FC<Props> = (
  {
    /* Destructure props here */
  }
) => {
  const contextValue = useContext(viewerContext);
  let view = contextValue?.view;
  let setView = contextValue?.setView;
  const { completion, setCompletion } = useContext(CompletionContext);
  // console.log(JSON.stringify(view))
  const handleClick = () => {
    setCompletion({ topicId: view.id, progress: 100 })
  }
  return (
    <div>
      {view?.type === "external" ? (
        <ExternalTab source={view.source} handleClick={handleClick} name={view.name} />
      )
        :
        view?.type === "ppt" ? (
          <PptRenderer source={view.source} handleClick={handleClick} />
        ) : view?.type === "docx" ? (
          <DocxRenderer source={view.source} handleClick={handleClick} />
        ) : view?.type === "pdf" ? (
          <PdfRenderer source={view.source} handleClick={handleClick} progress={view.progress} />
        ) : view?.type === "video" ? (
          <VideoRenderer source={view.source} />
        ) : view?.type === "youtubeVideo" ? (
          <CustomYoutubeRenderer source={view.source} />
        ) : (
          <Welcome />
        )}

      {JSON.stringify(view)}
    </div>
  );
};

const IconCycle: React.FC<{ size: string }> = ({ size }) => {
  const icons = [<RiFilePpt2Fill size={size} className="animate-ping" />, <RiFilePdf2Fill size={size} className="animate-ping" />, <RiFileWord2Fill size={size} className="animate-ping" />
    , <RiLinkM size={size} className="animate-ping" />, <RiYoutubeFill size={size} className="animate-ping" />, <RiVideoFill size={size} className="animate-ping" />];
  const [currentIconIndex, setCurrentIconIndex] = useState(0);

  useEffect(() => {
    const intervalId = setInterval(() => {
      setCurrentIconIndex((prevIndex) => (prevIndex + 1) % icons.length);
    }, 1000);

    return () => clearInterval(intervalId);
  }, [icons.length]);

  return <div >{icons[currentIconIndex]}</div>;
};


const Welcome: React.FC<{}> = () => {
  return (
    <Card className="mx-52 my-2  bg-gray-50 dark:bg-gray-600 h-[91vh]  w-[935px] ">
      <CardHeader shadow={false} floated={false} className="min-h-[500px] flex justify-center items-center mx-5 bg-gray-200 dark:bg-[#666666]">
        <IconCycle size="300" />
      </CardHeader>
      <CardBody className="animate-pulse">

        <Typography
          as="div"
          variant="h1"
          className="mb-2 h-2 w-full flex text-gray-500 justify-center"

        >
          THIS - Learning Management System
        </Typography>

      </CardBody>
      <CardFooter className="pt-12 flex justify-end animate-pulse">
        <Button
          disabled
          tabIndex={-1}
          className="h-8 w-20 bg-gray-300 shadow-none  hover:shadow-none"
        >
          &nbsp;
        </Button>
      </CardFooter>
    </Card>
  )
}


const CustomYoutubeRenderer: React.FC<{ source: string }> = ({ source }) => {
  const regex = /(?:https?:\/\/)?(?:www\.)?(?:youtube\.com\/(?:[^/]+\/.+\/|(?:v|e(?:mbed)?)\/|.*[?&]v=)|youtu\.be\/)([^"&?/\s]{11})/;
  // Extracting the video ID using match method
  const match = source.match(regex);
  // If match is found, return the video ID, otherwise return null
  const vidId = match ? match[1] : " k1BneeJTDcU";
  return (
    <Card className="mx-52 my-2  bg-gray-50 dark:bg-gray-600 h-[91vh] overflow-auto">
      <CardHeader shadow={false} floated={false} className="min-h-[120vh] mx-5 bg-gray-200 dark:bg-[#666666]">
        <div>
          <CustomYouTubePlayer vidId={vidId} />
        </div>
      </CardHeader>
      <CardBody>
        <input></input>
      </CardBody>
      <CardFooter className="pt-0"></CardFooter>
    </Card>
  );
};
const ExternalTab: React.FC<{ source: string, handleClick: () => void, name: string }> = ({ source, handleClick, name }) => {
  // console.log(renderToString())
  console.log(name)
  return (
    <Card className="mx-52 my-2  bg-gray-50 dark:bg-gray-600 h-[91vh] overflow-auto">
      <CardHeader shadow={false} floated={false} className="bg-gray-200 dark:bg-[#666666] flex justify-center items-center w-[906px] h-[742px]">
        <a href={source} target="_blank" className="w-[906px] h-[742px] flex justify-center flex-col items-center  ">
          <SiMicrosoftsharepoint size={200} className="mb-10" />
          <Typography variant="h3" className="text-gray-500 ">Click to safely open "{name}" in a new tab</Typography>
        </a>
      </CardHeader>
      <CardBody className="flex justify-end">
        <Button onClick={handleClick} className="dark:text-gray-700 dark:bg-green-500 bg-green-400" >Mark as Complete</Button>
      </CardBody>
      <CardFooter className="pt-0"></CardFooter>
    </Card>
  )
};
const convertToEmbedLink = (fileLink: string): string => {
  // Extract the user-specific part of the SharePoint link
  const userPartMatch = fileLink.match(/\/personal\/([^\/]+)/);
  const userPart = userPartMatch ? userPartMatch[1] : "";

  // Extract the document ID from the SharePoint link
  const regex = /d=([^&]+)/;
  const match = fileLink.match(regex);
  const formattedDocId = match ? match[1] : null;

  // Format the document ID with hyphens
  const formattedId = formattedDocId
    ? formattedDocId
      .substring(1)
      .replace(/(\w{8})(\w{4})(\w{4})(\w{4})(\w{12})/, "$1-$2-$3-$4-$5")
    : "";

  // Construct the embed link with the user-specific part and formatted document ID
  return `https://thisisthbs-my.sharepoint.com/personal/${userPart}/_layouts/15/Doc.aspx?sourcedoc={${formattedId}}&action=embedview&wdAr=1.7777777777777777`;
};

const IframeCustom: React.FC<{ source: string }> = ({ source }) => {
  console.log(source);
  const embedLink = convertToEmbedLink(source);
  console.log(embedLink);
  console.log(typeof embedLink);
  return (
    <iframe
      src={embedLink}
      width="906px"
      height="581px"
    // frameborder="0"
    >
      <p>view outside</p>
    </iframe>
  );
};

const PptRenderer: React.FC<{ source: string, handleClick: () => void }> = ({ source, handleClick }) => {
  return (
    <Card className="mx-52 my-2  bg-gray-50 dark:bg-gray-600 h-[91vh] overflow-auto">
      <CardHeader shadow={false} floated={false} className=" mx-5 bg-gray-200 dark:bg-[#666666] ">
        <div>
          <object
            data={source}
            type="application/pptx"
            width="906"
            height="742 "
          >
            <IframeCustom source={source} />
          </object>
        </div>
      </CardHeader>
      <CardBody>
        <Button onClick={handleClick} >Mark as Complete</Button>
      </CardBody>
      <CardFooter className="pt-0">

      </CardFooter>
    </Card>
  );
};

const DocxRenderer: React.FC<{ source: string, handleClick: () => void }> = ({ source, handleClick }) => {
  console.log("docx link", source)
  return (
    <Card className="mx-2 my-2  bg-gray-50 dark:bg-gray-600 h-[91vh] overflow-auto">
      <CardHeader
        shadow={false}
        floated={false}
        className=" mx-5 bg-gray-200 dark:bg-[#666666] h-[600px]"
      >
        <div>
          <object
            data={source}
            type="application/doc"
            width="919"
            height="550 "
          >
            <iframe
              src={source}
              width="919"
              height="525"
            >
              <p>This browser does not support PDF!</p>
            </iframe>
          </object>
        </div>
      </CardHeader>
      <CardBody>
        <input></input>
      </CardBody>
      <CardFooter className="pt-0 ">
        <Button
          ripple={false}
          fullWidth={true}
          className="bg-blue-gray-900/10 text-blue-gray-900 shadow-none hover:scale-105 hover:shadow-none focus:scale-105 focus:shadow-none active:scale-100"
        >
          Notes
        </Button>
      </CardFooter>
    </Card>
  );
};
const VideoRenderer: React.FC<{ source: string }> = ({ source }) => {
  return (<div>
    <Card className="mx-52 my-2  bg-gray-50 dark:bg-gray-600 h-[91vh] overflow-auto">
      <CardHeader shadow={false} floated={false} className="min-h-[78vh] mx-5 bg-gray-200 dark:bg-[#666666]">
        <div>
          <iframe
            src="https://thisisthbs-my.sharepoint.com/personal/shrivatsa_koulgi_thbs_com/_layouts/15/embed.aspx?UniqueId=b9b3471b-6ff1-479d-9c94-46385d29d30b&embed=%7B%22ust%22%3Atrue%2C%22hv%22%3A%22CopyEmbedCode%22%7D&referrer=StreamWebApp&referrerScenario=EmbedDialog.Create"
            width="906"
            height="581"
            frameborder="0"
            scrolling="no"
            allowfullscreen
            title="Git Repo demonstration meeting-20240404_151549-Meeting Recording 1.mp4">
          </iframe>
        </div>
      </CardHeader>
      <CardBody>
        <input></input>
      </CardBody>
      <CardFooter className="pt-0">
        <Button
          ripple={false}
          fullWidth={true}
          className="bg-blue-gray-900/10 text-blue-gray-900 shadow-none hover:scale-105 hover:shadow-none focus:scale-105 focus:shadow-none active:scale-100"
        >
          Notes
        </Button>
      </CardFooter>
    </Card>
  </div>);
};
const PdfRenderer: React.FC<{ source: string, handleClick: () => void, progress: number }> = ({ source, handleClick, progress }) => {
  return (
    <Card className="mx-2 my-2  bg-gray-50 dark:bg-gray-600 h-[91vh] max-w-[1000px] overflow-auto">
      <CardHeader
        shadow={false}
        floated={false}
        className=" mx-5 bg-gray-200 dark:bg-[#666666] "
      >
        <div>
          <object
            data={source}
            type="application/pdf"
            width="919"
            height="742 "
          >
            <iframe
              src={source}
              width="919"
              height="742"
            >
              <p>This browser does not support PDF!</p>
            </iframe>
          </object>
        </div>
      </CardHeader>
      <CardBody className="flex justify-between ">
        <div className="flex justify-start">
          <div className="w-[100px] flex justify-between">
            <Tooltip content="Add Notes" >
              <IconButton className="dark:text-gray-700" variant="outlined">
                <GiQuillInk size="24" className="text-gray-900" />
              </IconButton>
            </Tooltip>
            <Tooltip content="Open in New Tab" >
              <a href={source} target="_blank">
                <IconButton className="dark:text-gray-700" variant="outlined">
                  <ImNewTab size="20" className="text-gray-900" />
                </IconButton>
              </a>
            </Tooltip>
          </div>
        </div>
        <Button onClick={handleClick} disabled={progress <= 95} className="dark:text-gray-900  dark:bg-green-500 bg-green-400" >Mark as Complete</Button>
      </CardBody>
      <CardFooter className="pt-0 ">

      </CardFooter>
    </Card>
  );
};

export default ResourceRenderer;
