import { ReactNode, useEffect, useState } from "react";

interface Props {
  children: ReactNode;
}

const Providers: React.FC<Props> = ({ children }) => {
  const [mounted, setMounted] = useState(false);

  useEffect(() => {
    setMounted(true);
  }, []);

  if (!mounted) return <></>;

  return <>{children}</>;
};

export default Providers;
