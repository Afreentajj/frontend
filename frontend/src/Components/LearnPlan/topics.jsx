import {
  Alert,
  Button,
  Card,
  CardBody,
  CardHeader,
  Input,
  Typography,
} from "@material-tailwind/react";
import axios from "axios";
import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";

export function Topics() {
  const [topics, setTopics] = useState([{ topicName: "", description: "" }]);
  const location = useLocation();
  const courseId = location.state.courseId;
  const [showAlert, setShowAlert] = useState(false);
  const [successAlert, setSuccessAlert] = useState(false);
  const navigate = useNavigate();

  const handleAddTopic = () => {
    setTopics([...topics, { topicName: "", description: "" }]);
  };

  const handleDeleteTopic = (index) => {
    const newTopics = [...topics];
    newTopics.splice(index, 1);
    setTopics(newTopics);
  };

  const handleChange = (index, field, value) => {
    const newTopics = [...topics];
    newTopics[index][field] = value;
    setTopics(newTopics);
  };




  const handleSubmitAllTopics = (e) => {
    e.preventDefault();
  
    const apiUrl = import.meta.env.VITE_API_URL;
  
    const allTopicsData = topics.map((topicData, index) => ({
      topicID: index,
      topicName: topicData.topicName,
      description: topicData.description,
      course: {
        courseID: courseId,
      },
    }));
  
    if (topics[0].topicName) {
      axios
        .post(`${apiUrl}/topic/multiple`, allTopicsData)
        .then((response) => {
          console.log("Topics data posted successfully:", response.data);
          setSuccessAlert(true);
          setTimeout(() => {
            setSuccessAlert(false);
          }, 2000);
          setTimeout(() => {
            navigate("/dashboard/admin");
          }, 2000);
        })
        .catch((error) => {
          console.error("Error posting topics data:", error);
        });
  
      setTopics([{ topicName: "", description: "" }]);
    } else {
      setShowAlert(true);
      setTimeout(() => {
        setShowAlert(false);
      }, [3000]);
    }
  };
  

  return (
    <div className="h-screen w-full flex  justify-center items-center ">
      {successAlert && (
        <Alert
          color="green"
          className=" absolute top-1 right-2 animate-fadeOut w-1/4"
        >
          {/* <img src={"../assets/LoadingIcon.svg"} alt="mySvgImage" /> */}
          Topics Added Successfully
        </Alert>
      )}
      {showAlert && (
        <Alert
          color="red"
          className=" absolute top-1 right-2 animate-fadeOut w-1/4"
        >
          Please fill all the Fields.
        </Alert>
      )}
      <Card className="mt-10 w-full md:w-3/4 lg:w-2/4 xl:w-2/4 mx-auto h-[60%]">
        <CardHeader
          variant="gradient"
          color="gray"
          className="mb-4 grid h-[20%] place-items-center"
        >
          <Typography variant="h5" color="white" className="mb-2">
            Add Topics
          </Typography>
        </CardHeader>
        <CardBody>
          <form
            className="container p-6 bg-white rounded-lg"
            onSubmit={handleSubmitAllTopics}
          >
            <div className="mb-4">
              {topics.map((topic, index) => (
                <div key={index} className="flex flex-col gap-1">
                  <div className="form-group mt-1">
                    <Input
                      id={`topicName${index}`}
                      name="topicName"
                      label="Topic Name"
                      value={topic.topicName}
                      onChange={(e) =>
                        handleChange(index, "topicName", e.target.value)
                      }
                      variant="outlined"
                      margin="normal"
                    />
                  </div>
                  <div className="mb-4">
                    <Input
                      id={`description${index}`}
                      name="description"
                      variant="outlined"
                      value={topic.description}
                      label="Description"
                      onChange={(e) =>
                        handleChange(index, "description", e.target.value)
                      }
                    />
                  </div>
                  <div className="flex justify-end">
                    <Button
                      variant="gradient"
                      className="relative flex justify-center items-center w-1/5"
                      onClick={() => handleDeleteTopic(index)}
                    >
                      <svg
                        fill="#ffffff"
                        height="18px"
                        width="64px"
                        version="1.1"
                        id="Layer_1"
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 512 512"
                        stroke="#ffffff"
                      >
                        <g id="SVGRepo_bgCarrier"></g>
                        <g id="SVGRepo_tracerCarrier"></g>
                        <g id="SVGRepo_iconCarrier">
                          <path d="M42.7,469.3c0,23.5,19.1,42.7,42.7,42.7h341.3c23.5,0,42.7-19.1,42.7-42.7V192H42.7V469.3z M362.7,256h42.7v192h-42.7V256z M234.7,256h42.7v192h-42.7V256z M106.7,256h42.7v192h-42.7V256z M490.7,85.3h-128V42.7C362.7,19.1,343.5,0,320,0H192 c-23.5,0-42.7,19.1-42.7,42.7v42.7h-128C9.5,85.3,0,94.9,0,106.7V128c0,11.8,9.5,21.3,21.3,21.3h469.3c11.8,0,21.3-9.5,21.3-21.3 v-21.3C512,94.9,502.5,85.3,490.7,85.3z M320,85.3H192V42.7h128V85.3z"></path>
                        </g>
                      </svg>
                      <p className="ml-[10px] mt-[5px]">Delete</p>
                    </Button>
                  </div>
                </div>
              ))}
            </div>
            <div className="mb-4 flex flex-col md:flex-row justify-around gap-1">
              <Button variant="gradient" onClick={handleAddTopic}>
                +
              </Button>
              <Button type="submit" ripple={true}>
                Submit Topics and Finish
              </Button>
            </div>
          </form>
        </CardBody>
      </Card>
    </div>
  );
}
