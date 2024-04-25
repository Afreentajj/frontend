import {
  Alert,
  Button,
  Card,
  CardBody,
  CardHeader,
  Option,
  Select,
  Typography,
} from "@material-tailwind/react";
import axios from "axios"; // Import axios
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export function CreatePlan() {
  const [batchItems, setBatchItems] = useState([]);
  const [batch, setBatch] = useState({});
  const [type, setType] = useState("");
  const navigate = useNavigate();
  const [AlertOpen, setAlertOpen] = useState(false);
  const [successAlert, setSuccessAlert] = useState(false);
  const [existingSuccessAlert, setExistingSuccessAlert] = useState(false);

  useEffect(() => {
    fetchBatchItems();
  }, []);

  const fetchBatchItems = async () => {
    try {
      const response = await axios.get(
        `${import.meta.env.VITE_BATCH_SERVICE_URL}/batch`
        // `${import.meta.env.VITE_API_URL2}/batch`
      );

      if (response.status === 200) {
        console.log(response.data);
        setBatchItems(response.data);
      } else {
        console.error("Failed to fetch courses.");
      }
    } catch (error) {
      console.error("Error occurred while fetching courses:", error);
    }
  };

  const handleChangeBatch = (value) => {
    let matchedBatch = batchItems.filter((item) => item.batchName === value);
    setBatch(matchedBatch[0]);
  };

  const handleChangeType = (value) => {
    setType(String(value));
  };

  const handleSubmit = (event) => {
    const formData = {
      batchID: batch.batchId, //batch.batchId
      type: type,
    };

    if (batchItems && type) {
      // Check if learning plan already exists
      axios
        .get(`${import.meta.env.VITE_API_URL}/learning-plan`, {
          //`${import.meta.env.VITE_API_URL}/learning-plan`
          params: {
            batchID: batch.batchId,
            type: type,
          },
        })
        .then((response) => {
          if (response.data && response.data.length > 0) {
            // Learning plan already exists, navigate to path page
            setExistingSuccessAlert(true);
            const learningPlanID = response.data[0].learningPlanID;
            setTimeout(() => {
              setExistingSuccessAlert(false);
              navigate("/learnpath", {
                state: { learningPlanID: learningPlanID, batch: batch },
              });
            }, 2000);
          } else {
            // Learning plan doesn't exist, make a POST request
            axios
              .post(`${import.meta.env.VITE_API_URL}/learning-plan`, formData)
              .then((response) => {
                setAlertOpen(false);
                setSuccessAlert(true);
                setTimeout(() => {
                  setSuccessAlert(false);
                  navigate("/learnpath", {
                    state: {
                      learningPlanID: learningPlanID,
                      batch: batch,
                    },
                  });
                }, 2000);
                console.log("Learning plan saved successfully:", response.data);
                const learningPlanID = response.data.learningPlanID;
              })
              .catch((error) => {
                console.error("Error saving learning plan:", error);
              });
          }
        })
        .catch((error) => {
          console.error("Error checking learning plan existence:", error);
        });
    } else {
      setAlertOpen(true);
    }

    event.preventDefault();

    event.target.reset();
  };

  return (
    <div className="h-screen w-full flex justify-center items-center">
      {existingSuccessAlert && (
        <Alert
          color="green"
          className="absolute top-1 right-2 animate-fadeOut w-1/4"
        >
          Existing Learning Plan Added
        </Alert>
      )}
      {successAlert && (
        <Alert
          color="green"
          className="absolute top-1 right-2 animate-fadeOut w-1/4"
        >
          Learning Plan Added
        </Alert>
      )}
      {AlertOpen && (
        <Alert
          color="red"
          className="absolute top-1 right-2 animate-fadeOut w-1/4"
        >
          Please fill all the Fields.
        </Alert>
      )}
      <Card className="mt-6 w-full md:w-3/4 lg:w-2/4 xl:w-2/4 mx-auto">
        <CardHeader
          variant="gradient"
          color="gray"
          className="mb-4 grid h-28 place-items-center"
        >
          <Typography variant="h5" color="white" className="mb-2">
            Add Learning Plan
          </Typography>
        </CardHeader>
        <CardBody>
          <form
            className="container p-6 bg-white rounded-lg"
            onSubmit={handleSubmit}
          >
            <div className="mb-4">
              <Select
                required
                variant="outlined"
                label="Batch Name"
                onChange={handleChangeBatch}
              >
                {batchItems.map((item) => (
                  <Option key={item.batchId} value={item.batchName}>
                    {item.batchName}
                  </Option>
                ))}
              </Select>
            </div>
            <div className="mb-4">
              <Select
                variant="outlined"
                label="Type"
                id="Type"
                onChange={handleChangeType}
              >
                <Select.Option value="bootcamp">BOOTCAMP</Select.Option>
                <Select.Option value="on_demand">ON-DEMAND</Select.Option>
                <Select.Option value="mandatory">MANDATORY</Select.Option>
                <Select.Option value="org_wide">ORG-WIDE</Select.Option>
              </Select>
            </div>

            <div className="mb-4 flex justify-end">
              <Button ripple={true} type="submit">
                Add Plan
              </Button>
            </div>
          </form>
        </CardBody>
      </Card>
    </div>
  );
}
