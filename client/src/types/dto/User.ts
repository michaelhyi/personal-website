type User = {
  id: number;
  email: string;
  role: string;
  authorities: {
    authority: string;
  }[];
};

export default User;
