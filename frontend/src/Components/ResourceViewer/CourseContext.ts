import { createContext, useContext } from "react";

type Course = {
    courseId: number;
    progress: number;
};

interface CourseCompletionContextType {
    courseCompletion: Course;
    setCourseCompletion: React.Dispatch<React.SetStateAction<Course>>; // Define the type of 'setView'
}

const defaultContext = {
    courseCompletion: {
        courseId: 1,
        progress: 0,
    },
    setCourseCompletion: (courseCompletion: Course) => { },
} as CourseCompletionContextType;

export const CourseCompletionContext =
    createContext<CourseCompletionContextType>(defaultContext);
