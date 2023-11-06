'use client';
import { get } from '@/service/api/http';
import { useRouter, useSearchParams } from 'next/navigation';
import { useEffect } from 'react';

export default function KaKaoPage() {
  const searchParams = useSearchParams();
  const router = useRouter();

  useEffect(() => {
    const code = searchParams.get('code');
    get('/login/oauth2/kakao', { code }).then((data: any) => {
      localStorage.setItem('accessToken', data.accessToken);
      localStorage.setItem('refreshToken', data.refreshToken);
      router.replace('/main');
    });
  }, []);

  return <div></div>;
}
