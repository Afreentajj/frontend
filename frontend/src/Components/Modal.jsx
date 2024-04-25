import {
  Button,
  Dialog,
  DialogHeader,
  DialogBody,
  DialogFooter,
} from "@material-tailwind/react";
import PropTypes from "prop-types";
// eslint-disable-next-line no-unused-vars
import React from 'react';
const Modal = ({ handleOpen, open ,handleClose,data}) => {
  return (
    <>
      <Dialog
        open={open}
        handler={handleOpen}
        animate={{
          mount: { scale: 1, y: 0 },
          unmount: { scale: 0.9, y: -100 },
        }}
      >
        <DialogHeader>{data.title}</DialogHeader>
        <DialogBody>
          {data.message}
        </DialogBody>
        <DialogFooter>
          <Button
            variant="text"
            color="red"
            onClick={handleClose}
            className="mr-1"
          >
            <span>Cancel</span>
          </Button>
          <Button variant="gradient" color={`${data.delete==true?"red":"green"}`} onClick={handleOpen}>
            <span>{data.actionText}</span>
          </Button>
        </DialogFooter>
      </Dialog>
    </>
  );
};
Modal.propTypes = {
  handleOpen: PropTypes.func.isRequired,
  handleClose: PropTypes.func.isRequired,
  open: PropTypes.bool.isRequired,
  data: PropTypes.object.isRequired,

};

export default Modal;
