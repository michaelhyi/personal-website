/** @type {import('next').NextConfig} */

const nextConfig = {
  transpilePackages: ["services", "types", "ui", "utils"],
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
