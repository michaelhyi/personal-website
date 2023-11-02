"use client";

import { Spinner } from "@chakra-ui/react";

const Loading = () => {
  return (
    <div className="flex h-screen items-center justify-center">
      <Spinner size="xs" />
    </div>
  );
};

export default Loading;
