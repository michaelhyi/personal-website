import axios from "axios";

export const login = async (email) => {
  const { data } = await axios.post(
    `${process.env.REACT_APP_API_URL}/v1/auth/${email}`
  );

  return data;
};

export const validateToken = async (token) => {
  await axios(
    `${process.env.REACT_APP_API_URL}/v1/auth/validate-token/${token}`
  );
};
