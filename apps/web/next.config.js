/** @type {import('next').NextConfig} */

const nextConfig = {
  transpilePackages: ["services", "types", "ui"],
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
