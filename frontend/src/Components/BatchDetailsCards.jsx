import { motion } from "framer-motion";
// eslint-disable-next-line no-unused-vars
import React from 'react';
import { CourseCard } from "./CourseCard";
// import { useState } from "react";
import PropTypes from "prop-types";
const BatchDetailsCards = ({
  status,
  searchQuery,
  batchData,
  progressData,
  change,
}) => {
  let filteredTable = batchData;
  // const [carousel, setCarousel] = useState(false);
  // Filter table data based on status
  if (status === "Active") {
    filteredTable = batchData.filter(({ online }) => online);
  } else if (status === "Non-Active") {
    filteredTable = batchData.filter(({ online }) => !online);
  }
  const progressHandler = (batchId) => {
    const res = progressData.find((data) => data.batchId === batchId);
    return res ? res.batchProgress : 0;
  };
  if (searchQuery) {
    const query = searchQuery.toLowerCase();
    filteredTable = filteredTable.filter(({ batchName }) =>
      batchName.toLowerCase().includes(query)
    );
    batchData = batchData.filter(({ batchName }) =>
      batchName.toLowerCase().includes(query)
    );
  }
  const width = change ? "w-full mr-10" : "";
  const scale = change ? { scale: 1.04 } : { scale: 1.1 };

  return (
    <div className="flex flex-wrap justify-center sm:justify-start space-evenly ml-4 sm:ml-12">
      {status === "All"
        ? batchData.map(
            ({ batchId, batchName, batchDescription, online, startDate }) => (
              <motion.div
                key={batchId}
                whileHover={scale}
                className={`course-card-wrapper ${width}`}
                style={{ zIndex: 1 }}
              >
                <CourseCard
                  online={online}
                  progressValue={progressHandler(batchId)}
                  name={batchName}
                  description={batchDescription}
                  date={startDate}
                  batchId={batchId}
                  change={change}
                />
              </motion.div>
            )
          )
        : filteredTable.map(
            ({ batchId, batchName, batchDescription, online, startDate }) => (
              <motion.div
                key={batchId}
                whileHover={scale}
                className={`course-card-wrapper ${width}`}
                style={{ zIndex: 1 }}
              >
                <CourseCard
                  online={online}
                  progressValue={progressHandler(batchId)}
                  name={batchName}
                  description={batchDescription}
                  date={startDate}
                  batchId={batchId}
                  change={change}
                />
              </motion.div>
            )
          )}
    </div>
  );
};

BatchDetailsCards.propTypes = {
  status: PropTypes.string.isRequired,
  searchQuery: PropTypes.string.isRequired,
  batchData: PropTypes.array.isRequired,
  progressData: PropTypes.array.isRequired,
  change: PropTypes.bool.isRequired,
};

export default BatchDetailsCards;
