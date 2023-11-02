/** @type {import('next').NextConfig} */
const nextConfig = {
  images: {
    domains: [
      'localhost',
      'images.unsplash.com',
      'novelit.s3.ap-northeast-2.amazonaws.com',
      'i.ibb.co',
    ],
  },
};

module.exports = nextConfig;
