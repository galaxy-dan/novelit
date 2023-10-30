
import AWS from 'aws-sdk';
import axios from 'axios';
import imageCompression from 'browser-image-compression';

const S3config = {
  bucketName: process.env.NEXT_PUBLIC_S3_BUCKET_NAME || '',
  region: 'ap-northeast-2',
  accessKeyId: process.env.NEXT_PUBLIC_S3_ACCESS_KEY_ID || '',
  secretAccessKey: process.env.NEXT_PUBLIC_S3_SECRET_ACCESS_KEY || '',
};

export function getS3URL(name: string) {
  const s3 = new AWS.S3(S3config);

  const url = s3.getSignedUrl('putObject', {
    Bucket: process.env.NEXT_PUBLIC_S3_BUCKET_NAME,
    Key: self.crypto.randomUUID() + name,
    Expires: 60 * 60 * 3,
  });

  return url;
}

export async function uploadImage(url: string, file: File) {
  const compressedFile = await compressImage(file);
  axios
    .put(url, compressedFile, {
      headers: {
        'Content-Type': 'image/png', // 업로드할 파일의 콘텐츠 유형 지정
      },
    })
    .then((response) => {
      response.data;
      return true;
    })
    .catch((error) => {
      error;
      return false;
    });
  return true;
}

export async function compressImage(originImage: File){

  if (originImage && originImage.size > 800000) {
    // 파일 용량이 800kb이상은 압축
    let image = await imageCompression(originImage, {
      maxSizeMB: 0.8,
      alwaysKeepResolution: true,
      maxWidthOrHeight: 600,
    });
    return image;
  }
  return originImage;
}
