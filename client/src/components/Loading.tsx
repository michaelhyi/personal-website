import { Spinner } from "@chakra-ui/react";

const Loading = () => {
  return (
    <div className="flex h-full justify-center items-center">
      <Spinner size="xs" />
    </div>
  );
};

export default Loading;
