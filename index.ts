import concurrently from "concurrently";

concurrently([
  { command: "cd scripts && sh run-api.sh" },
  { command: "cd scripts && sh run-client.sh" },
]);
