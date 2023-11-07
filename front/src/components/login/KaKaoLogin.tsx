'use client';

import { useRouter } from 'next/navigation';
import KakaoButton from '../../../public/images/kakao_login_large_wide.svg';
import Image from 'next/image';

export default function KaKaoLogin() {
  const router = useRouter();

  const kakaoLogin = () => {
    router.push(
      `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.NEXT_PUBLIC_KAKAO_CLIENT_ID}&redirect_uri=${process.env.NEXT_PUBLIC_KAKAO_REDIRECT_URI}&response_type=code`,
    );
  };
  return (
    <button onClick={() => kakaoLogin()}>
      <Image alt="kakao" src={KakaoButton} width={400} />
    </button>
  );
}
