import Head from "next/head";

const Metadata = () => {
  return (
    <Head>
      <title>Michael Yi</title>
      <meta
        name="description"
        content="© 2023 Michael Yi, All Rights Reserved."
      />
      <meta name="viewport" content="width=device-width, initial-scale=1" />
      <link rel="icon" href="/Michael.png" />
      <meta
        http-equiv="Content-Security-Policy"
        content="upgrade-insecure-requests"
      />
    </Head>
  );
};

export default Metadata;
