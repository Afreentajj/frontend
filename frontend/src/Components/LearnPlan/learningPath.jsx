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
import { useLocation, useNavigate } from "react-router-dom";

export function LearningPath() {
  const [selectedCourse, setSelectedCourse] = useState({});
  const [courseItem, setCourseItem] = useState([]);
  const [type, setType] = useState("");
  const [instructor, setInstructor] = useState("");
  const [startDateOfPlan, setStartDate] = useState("");
  const [endDateOfPlan, setEndDate] = useState("");
  const [dateError, setDateError] = useState(false);
  const [endDateAlert, setEndDateAlert] = useState(false);
  const [showInternalTrainers, setShowInternalTrainers] = useState(false);
  const [showExternalTrainers, setShowExternalTrainers] = useState(false);
  const [InternalTrainer, setInternalTrainer] = useState("");
  const [ExternalTrainer, setExternalTrainer] = useState("");

  const location = useLocation();
  const learningPlanID = location.state.learningPlanID;
  const {
    batchId,
    batchName,
    batchDescription,
    startDate,
    endDate,
    employeeId,
    batchSize,
  } = location.state.batch;

  const [AlertOpen, setAlertOpen] = useState(false);
  const [successAlert, setSuccessAlert] = useState(false);
  const [dateAlert, setDateAlert] = useState(false);
  const navigate = useNavigate();
  const [learningPath, setLearningPath] = useState([
    {
      pathID: "",
      learningPlan: {
        learningPlanID: "",
        batchID: batchId,
        type: "",
      },
      course: {
        courseName: "",
        courseID: "",
        level: "",
      },
      type: "",
      trainer: "",
      startDate: "",
      endDate: "",
    },
  ]);

  useEffect(() => {
    fetchCourseItems();
  }, []);

  const fetchCourseItems = async () => {
    try {
      const response = await axios.get(`${import.meta.env.VITE_API_URL}/course`);
      if (response.status === 200) {
        if (Array.isArray(response.data)) {
          setCourseItem(response.data);
        } else {
          console.error("The fetched data is not an array:", response.data);
        }
      } else {
        console.error("Failed to fetch courses.");
      }
    } catch (error) {
      console.error("Error occurred while fetching courses:", error);
    }
  };

  const handleStartDate = (index, field, value) => {
    setStartDate(value);

    setLearningPath((prevLearningPath) => {
      const updatedLearningPath = [...prevLearningPath];
      updatedLearningPath[index] = {
        ...updatedLearningPath[index],
        [field]: value,
        startDate: value, // Update the 'type' field here
      };
      return updatedLearningPath;
    });
  };
  const handleEndDate = (index, field, value) => {
    setEndDate(value);

    setLearningPath((prevLearningPath) => {
      const updatedLearningPath = [...prevLearningPath];
      updatedLearningPath[index] = {
        ...updatedLearningPath[index],
        [field]: value,
        endDate: value, // Update the 'type' field here
      };
      return updatedLearningPath;
    });
  };
  useEffect(() => {
    if (startDateOfPlan && endDateOfPlan) {
      const dateChecker =
        startDateOfPlan.replace(/-/g, "") - endDateOfPlan.replace(/-/g, "");
      if (endDateOfPlan) {
        if (dateChecker < 0) {
          if (endDateOfPlan < endDate) {
            //endDate
            console.log("Correct Dates");
            setDateError(false);
            setEndDateAlert(false);
          } else {
            setEndDateAlert(true);
          }
        } else if (dateChecker > 0) {
          console.log("wrong dates");
          setDateError(true);
        }
      }
    }
  }, [endDateOfPlan]);

  useEffect(() => {
    if (startDateOfPlan) {
      const startDateOfPlanString = startDateOfPlan.replace(/-/g, "");
      const startDateString = startDate.replace(/-/g, ""); //startDate

      if (startDateOfPlanString < startDateString) {
        setDateAlert(true);
      } else {
        setDateAlert(false);
      }
    }
  }, [startDateOfPlan]);

  const handleChangeType = (index, field, value) => {
    setType(value);
    setLearningPath((prevLearningPath) => {
      const updatedLearningPath = [...prevLearningPath];
      updatedLearningPath[index] = {
        ...updatedLearningPath[index],
        [field]: value,
        type: value, // Update the 'type' field here
      };
      return updatedLearningPath;
    });
  };
  const handleCourseItemChange = (index, field, value) => {
    let course = courseItem.filter((item) => item.courseName === value);
    setSelectedCourse(course[0]);
    setLearningPath((prevLearningPath) => {
      const updatedLearningPath = [...prevLearningPath];
      updatedLearningPath[index] = {
        ...updatedLearningPath[index],
        [field]: value,
        course: {
          ...updatedLearningPath[index].course,
          courseName: value,
        },
      };
      return updatedLearningPath;
    });
  };

  const handleInstructorChange = (value) => {
    setInstructor(value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    if (
      selectedCourse &&
      type &&
      startDateOfPlan &&
      endDateOfPlan &&
      (InternalTrainer || ExternalTrainer)
    ) {
      if (!dateError && !dateAlert && !endDateAlert) {
        const formData = {
          learningPlan: {
            learningPlanID: learningPlanID,
            batchID: batchId,
            type: type,
          },
          course: {
            courseName: selectedCourse.courseName,
            courseID: selectedCourse.courseID,
            level: selectedCourse.level,
          },
          type: type,
          trainer: InternalTrainer || ExternalTrainer,
          startDate: startDateOfPlan,
          endDate: endDateOfPlan,
        };
        // console.log("Form Data:", formData);

        const filteredLearningPath = learningPath.filter(
          (path) => path.type !== ""
        );

        const allLearningPath = learningPath.map((pathData, index) => ({
          pathID: index + 1,
          learningPlan: {
            learningPlanID: learningPlanID,
            batchID: batchId,
            type: pathData.type,
          },
          course: {
            courseName: pathData.course.courseName, // Accessing courseName property
            courseID: selectedCourse.courseID, // Placeholder, you might want to populate this
            level: selectedCourse.level,
          },
          type: pathData.type,
          trainer: pathData.trainer,
          startDate: pathData.startDate,
          endDate: pathData.endDate,
        }));

        console.log("Form Data", allLearningPath);

        axios
          .post(
            `${import.meta.env.VITE_API_URL}/learning-plan-path/multiple`,
            allLearningPath
          )
          .then((response) => {
            console.log(
              "Learning path data posted successfully:",
              response.data
            );
            setSuccessAlert(true);
            setLearningPath([
              {
                pathID: "",
                learningPlan: {
                  learningPlanID: "",
                  batchID: batchId,
                  type: "",
                },
                course: {
                  courseName: "",
                  courseID: "",
                  level: "",
                },
                type: "",
                trainer: "",
                startDate: "",
                endDate: "",
              },
            ]);
            setTimeout(() => {
              setSuccessAlert(false);
              navigate("/dashboard/admin");
            }, 2000);
            console.log("Data is posting successfully");
          })
          .catch((error) => {
            console.error("Error posting learning path data:", error);
            console.log("Data is not posting");
          });

        setLearningPath([
          {
            pathID: "",
            learningPlan: {
              learningPlanID: "",
              batchID: batchId,
              type: "",
            },
            course: {
              courseName: "",
              courseID: "",
              level: "",
            },
            type: "",
            trainer: "",
            startDate: "",
            endDate: "",
          },
        ]);
      }
    } else {
      setAlertOpen(true);
      setTimeout(() => {
        setAlertOpen(false);
      }, 2000);
      console.log("form not submitted");
    }
  };
  const DateFormater = (value) => {
    const datesValue = value.replace(/-/g, ",");
    const date = new Date(datesValue);

    const options = { year: "numeric", month: "long", day: "numeric" };
    const formattedDate = date.toLocaleDateString("en-US", options);

    return formattedDate;
  };
  const OpenInternalTrainer = () => {
    setShowInternalTrainers(true);
    setShowExternalTrainers(false);
  };
  const OpenExternalTrainer = () => {
    setShowExternalTrainers(true);
    setShowInternalTrainers(false);
  };

  const handleExternalTrainerName = (index, field, value) => {
    setExternalTrainer(value);
    setInternalTrainer("");

    setLearningPath((prevLearningPath) => {
      const updatedLearningPath = [...prevLearningPath];
      updatedLearningPath[index] = {
        ...updatedLearningPath[index],
        [field]: value,
        trainer: value, // Update the 'trainer' field here
      };
      return updatedLearningPath;
    });
  };

  const handleInternalTrainerName = (index, field, value) => {
    setInternalTrainer(value);
    setExternalTrainer("");

    setLearningPath((prevLearningPath) => {
      const updatedLearningPath = [...prevLearningPath];
      updatedLearningPath[index] = {
        ...updatedLearningPath[index],
        [field]: value,
        trainer: value, // Update the 'trainer' field here
      };
      return updatedLearningPath;
    });
  };

  const handleAddPath = () => {
    setLearningPath([
      ...learningPath,
      {
        learningPlan: {
          learningPlanID: "",
          batchID: batchId,
          type: "",
        },
        course: {
          courseName: "",
          courseID: "",
          level: "",
        },
        type: "",
        trainer: "",
        startDate: "",
        endDate: "",
      },
    ]);
  };
  const [deletedPaths, setDeletedPaths] = useState([]);
  const handleDeletePath = (index) => {
    setDeletedPaths((prevDeletedPaths) => [...prevDeletedPaths, index]);

    setTimeout(() => {
      setLearningPath((prevLearningPath) => {
        const updatedLearningPath = [...prevLearningPath];
        updatedLearningPath.splice(index, 1);
        return updatedLearningPath;
      });
    }, 500); // Wait for the animation to complete
  };

  return (
    <div className="w-full h-screen flex justify-center items-center bg-gray-200">
      <div className="max-w-4xl w-full">
        {successAlert && (
          <Alert
            color="green"
            className=" absolute top-1 right-2 animate-fadeOut w-1/4"
          >
            {/* <img src={"../assets/LoadingIcon.svg"} alt="mySvgImage" /> */}
            Learning Plan Added Successfully
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
        <Card className="h-auto w-full mx-auto ">
          <CardHeader
            variant="gradient"
            color="gray"
            className=" grid place-items-center h-[100px] w-full ml-0 mr-0 static shadow-none"
          >
            <Typography variant="h5" color="white" className="">
              Add Learning Path
            </Typography>
          </CardHeader>
          <CardBody className="max-h-[65vh] pt-0 overflow-y-auto">
            <form
              className="container p-1 bg-white rounded-lg"
              onSubmit={handleSubmit}
            >
              {learningPath.map((topic, index) => (
                <div
                  key={index}
                  className={`mb-5 mt-5 ${
                    deletedPaths.includes(index) ? "slideOutLeft" : ""
                  }`}
                  onAnimationEnd={() => {
                    if (deletedPaths.includes(index)) {
                      setDeletedPaths((prevDeletedPaths) =>
                        prevDeletedPaths.filter((item) => item !== index)
                      );
                    }
                  }}
                >
                  <h3 className="text-lg font-bold">{`LearningPath - ${
                    index + 1
                  }`}</h3>
                  <div className="mb-4">
                    <Select
                      required
                      variant="outlined"
                      label="Type"
                      id={`Type${index}`}
                      name="Type"
                      onChange={(value) =>
                        handleChangeType(index, "Type", value)
                      }
                    >
                      <Option value="course">COURSE</Option>
                      <Option value="MCQ">MCQ</Option>
                      <Option value="Evaluation">Evaluation</Option>
                    </Select>
                  </div>
                  <div className="mb-4">
                    <Select
                      id={`Course${index}`}
                      required
                      variant="outlined"
                      label="Select Course"
                      onChange={(value) =>
                        handleCourseItemChange(index, "courseName", value)
                      }
                    >
                      {courseItem &&
                        courseItem.map((item) => (
                          <Option key={item.courseID} value={item.courseName}>
                            {item.courseName}
                          </Option>
                        ))}
                    </Select>
                  </div>
                  <div className="mb-4">
                    <Select
                      required
                      variant="outlined"
                      label="Select Trainer"
                      id="Select Trainer"
                      name="Select Trainer"
                    >
                      <Option
                        value="Internal Trainer"
                        onClick={OpenInternalTrainer}
                      >
                        Internal Trainer
                      </Option>
                      <Option
                        value="External Trainer"
                        onClick={OpenExternalTrainer}
                      >
                        External Trainer
                      </Option>
                    </Select>
                    {showInternalTrainers && (
                      <div className="mb-4 mt-4">
                        <Select
                          id={`InternalTrainer${index}`}
                          variant="outlined"
                          label="Internal Trainers"
                          onChange={(value) =>
                            handleInternalTrainerName(index, "trainer", value)
                          }
                        >
                          <Option value="Shashank">Shashank</Option>
                          <Option value="Niitsh">Nitish</Option>
                          <Option value="Subiksha">Subiksha</Option>
                          <Option value="Yeshyeshwini">Yeshyeshwini</Option>
                        </Select>
                      </div>
                    )}
                    {showExternalTrainers && (
                      <div className="mb-4 mt-4">
                        <Input
                          id={`ExternalTrainer${index}`}
                          label="ExternalTrainer Name"
                          value={topic.trainer}
                          onChange={(e) =>
                            handleExternalTrainerName(
                              index,
                              "trainer",
                              e.target.value
                            )
                          }
                        />
                      </div>
                    )}
                  </div>
                  <div className="mb-4">
                    <Input
                      id={`start_date${index}`}
                      type="date"
                      variant="outlined"
                      label="Start date"
                      shrink={true}
                      value={topic.startDate}
                      onChange={(e) =>
                        handleStartDate(index, "startDate", e.target.value)
                      }
                      style={{ cursor: "pointer" }}
                    />
                    {dateAlert && dateAlert ? (
                      <p className="text-red-500 text-sm mt-1 ml-1">
                        {`* Date should be within the specific batch Timeline ${DateFormater(
                          startDate
                        )} & ${DateFormater(endDate)}`}
                      </p>
                    ) : null}
                  </div>
                  <div className="mb-4">
                    <Input
                      id={`end_date${index}`}
                      type="date"
                      variant="outlined"
                      label="End date"
                      shrink={true}
                      value={topic.endDate}
                      error={dateError ? true : false}
                      onChange={(e) =>
                        handleEndDate(index, "endDate", e.target.value)
                      }
                      style={{ cursor: "pointer" }}
                    />
                    {dateError && dateError ? (
                      <p className="text-red-500 text-sm mt-1 ml-1">
                        * Date is Invalid
                      </p>
                    ) : null}
                    {endDateAlert && (
                      <p className="text-red-500 text-sm mt-1 ml-1">
                        {`* Date should be within the specific batch Timeline ${DateFormater(
                          startDate
                        )} & ${DateFormater(endDate)}`}
                      </p>
                    )}
                  </div>
                  <div className="mb-2 flex  items-start ">
                    <Button
                      color="red"
                      size="sm"
                      onClick={() => handleDeletePath(index)}
                    >
                      <i className="fa fa-trash" /> Delete
                    </Button>
                  </div>
                </div>
              ))}

              <div className=" flex justify-end gap-2">
                <Button className="" variant="filled" onClick={handleAddPath}>
                  +
                </Button>
                <Button ripple={true} type="submit">
                  Add Learning Path
                </Button>
              </div>
            </form>
          </CardBody>
        </Card>
      </div>
    </div>
  );
}
