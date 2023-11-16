/** @type {import('next').NextConfig} */

const nextConfig = {
  transpilePackages: ["services", "types"],
  images: {
    remotePatterns: [
      {
        protocol: "https",
        hostname: "**",
      },
    ],
  },
};

module.exports = nextConfig;
