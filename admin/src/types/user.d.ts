export type User = {
  username: string;
  authorities: Authority[];
};

type Authority = {
  authority: string;
};
