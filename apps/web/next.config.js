/** @type {import('next').NextConfig} */

const nextConfig = {
  transpilePackages: ["services", "types", "ui"],
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
