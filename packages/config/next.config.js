/** @type {import('next').NextConfig} */
const nextConfig = {
  transpilePackages: [
    "@personal-website/config",
    "@personal-website/services",
    "@personal-website/types",
    "@personal-website/ui",
    "@personal-website/utils",
  ],
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
