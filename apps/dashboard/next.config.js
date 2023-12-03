/** @type {import('next').NextConfig} */
const nextConfig = {
  transpilePackages: ["services", "types"],
  images: {
    remotePatterns: [
      {
        protocol: "http",
        hostname: "**",
      },
    ],
  },
};

module.exports = nextConfig;
