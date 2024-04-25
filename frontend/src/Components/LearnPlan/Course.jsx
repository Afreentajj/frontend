import {
  Alert,
  Button,
  Card,
  CardBody,
  CardHeader,
  Input,
  Option,
  Select,
  Typography,
} from "@material-tailwind/react";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export function Course() {
  
  const [courseData, setCourseData] = useState({
    courseName: "",
    level: "",
    courseID: 0,
  });

  const navigate = useNavigate();
  const [selectedCourse, setSelectedCourse] = useState("");
  const [courseItem, setCourseItem] = useState([]);
  const [showCourseDiv, setShowCourseDiv] = useState(false);
  const [selectDisabled, setSelectDisabled] = useState(false);
  const [AlertOpen, setAlertOpen] = useState(false);
  const [successAlert, setSuccessAlert] = useState(false);
  const [searchCourse, setSearchCourse] = useState("");
  const [showCourseList, setShowCourseList] = useState(false);

  const handleInputClick = () => {
    setShowCourseList(true);
  };

  const handleSearchBlur = () => {
    setShowCourseList(false);
  };

  const handleSearch = (e) => {
    setSearchCourse(e.target.value);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCourseData((prevState) => ({
      ...prevState,
      [name]: value, // Set the value of the input field directly
    }));
  };
  const handleChangeLevel = (value) => {
    setCourseData((prevState) => ({
      ...prevState,
      level: value,
    }));
  };

const fetchCourseItems = async () => {
  try {
    const response = await axios.get(import.meta.env.VITE_API_URL + "/course");
    if (response.status === 200) {
      setCourseItem(response.data);
    } else {
      console.error("Failed to fetch courses.");
    }
  } catch (error) {
    console.error("Error occurred while fetching courses:", error);
  }
};

  
  useEffect(() => {
    fetchCourseItems();
  }, []);

  const handleCourseItemChange = (value) => {
    setSearchCourse(value);
    setSelectedCourse(value);
    setCourseData((prevState) => ({
      ...prevState,
      courseName: value, // Update courseName in courseData
    }));

    const courseSelectedFromDropdown = courseItem.filter(
      (item) => item.courseName === value
    );
    console.log(courseSelectedFromDropdown[0].courseID);
    setCourseData((prevState) => ({
      ...prevState,
      courseID: courseSelectedFromDropdown[0].courseID, // Update courseName in courseData
    }));
  };

  const handleButtonClick = () => {
    setShowCourseDiv(true);
    setSelectDisabled(true);
  };

  const handleAddTopics = () => {
    console.log(courseData);
    if (courseData.courseID || courseData.courseName) {
      if (selectDisabled) {
        if (courseData.courseName && courseData.level) {
          setAlertOpen(false);
          setSuccessAlert(true);
          axios
            .post(import.meta.env.VITE_API_URL + "/course", courseData)
            .then((response) => {
              console.log("Course data posted successfully:", response.data);
              // Extract course ID from the response data
              const courseId = response.data.courseID;
              console.log(courseId);

              setTimeout(() => {
                navigate("/topic", { state: { courseId: courseId } });
              }, 2000);
            })
            .catch((error) => {
              console.error("Error posting course data:", error);
            });
        } else {
          setAlertOpen(true);
          setTimeout(() => {
            setAlertOpen(false);
          }, 3000);
        }
      } else {
        setSuccessAlert(true);
        setTimeout(() => {
          navigate("/topic", { state: { courseId: courseData.courseID } });
        }, 2000);
      }
    } else {
      setAlertOpen(true);
      setTimeout(() => {
        setAlertOpen(false);
      }, 3000);
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
          Courses Added Successfully
        </Alert>
      )}
      {AlertOpen && (
        <Alert
          color="red"
          className=" absolute top-1 right-2 animate-fadeOut w-1/4"
        >
          Please fill all the Fields.
        </Alert>
      )}
      <Card className="w-full md:w-3/4 lg:w-2/4 xl:w-2/4 mx-auto h-1/2">
        <CardHeader
          variant="gradient"
          color="gray"
          className="mb-4 grid h-28 place-items-center"
        >
          <Typography variant="h5" color="white" className="mb-2">
            Add Course
          </Typography>
        </CardHeader>
        <CardBody>
          <form className="container p-6 bg-white rounded-lg">
            <div
              className="mb-4"
              style={{ display: selectDisabled ? "none" : " block" }}
            >
              <Input
                variant="outlined"
                label="Select Course"
                placeholder="Search Course"
                value={searchCourse}
                onChange={handleSearch}
                onClick={handleInputClick}
                // onBlur={handleSearchBlur}
              />

              {showCourseList && (
                <div className="mt-2 absolute w-1/3 border border-gray-300 bg-white rounded-xl hover:bg-violet-600 h-1/2 overflow-scroll">
                  {searchCourse
                    ? courseItem
                        .filter((item) =>
                          item.courseName
                            .toLowerCase()
                            .includes(searchCourse.toLowerCase())
                        )
                        .map((filteredItem) => (
                          <div
                            key={filteredItem.courseID}
                            onClick={() => {
                              handleCourseItemChange(filteredItem.courseName);
                              setShowCourseList(false);
                            }}
                            className="cursor-pointer pb-1 pl-2 hover:bg-gray-300 hover:text-black "
                          >
                            {filteredItem.courseName}
                          </div>
                        ))
                    : courseItem.map((item) => (
                        <div
                          key={item.courseID}
                          onClick={() => {
                            handleCourseItemChange(item.courseName);
                            setShowCourseList(false);
                          }}
                          className="cursor-pointer pb-1 pl-2 hover:bg-gray-300 hover:text-black"
                        >
                          {item.courseName}
                        </div>
                      ))}
                </div>
              )}
            </div>

            {showCourseDiv && (
              <div>
                <div className="mb-4">
                  <Input
                    id="courseName"
                    name="courseName"
                    variant="outlined"
                    label="Course Name"
                    value={courseData.courseName}
                    onChange={handleChange}
                  />
                </div>
                <div className="mb-4">
                  <Select
                    variant="outlined"
                    label="Select Level"
                    onChange={handleChangeLevel}
                  >
                    <Option value="basic">Basic</Option>
                    <Option value="Intermediate">Intermediate</Option>
                    <Option value="Advance">Advance</Option>
                  </Select>
                </div>
              </div>
            )}
            <div className="mb-4 flex justify-end">
              <Button ripple={true} onClick={handleButtonClick}>
                Add New Course
              </Button>
            </div>
            <div className="mb-4 flex justify-end">
              <Button ripple={true} onClick={handleAddTopics}>
                Add Topics
              </Button>
            </div>
          </form>
        </CardBody>
      </Card>
    </div>
  );
}
