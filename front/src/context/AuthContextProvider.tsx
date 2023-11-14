'use client';

import { useAuth } from '@/hooks/useAuth';
import { AuthContext } from './AuthContext';
import { useEffect } from 'react';
import { usePathname, useRouter } from 'next/navigation';
import { toast } from 'react-toastify';

type Props = {
  children: React.ReactNode;
};

export default function AuthContextProvider({ children }: Props) {
  const pathname = usePathname();
  const router = useRouter();
  const { user, setUser } = useAuth();
  let token = null;

  useEffect(() => {
    token = localStorage.getItem('accessToken');
    if (pathname === '/' || pathname.startsWith('/login')) return;

    if (!token) {
      toast('로그인 해주세요!');
      router.push('/');
    }
  }, [token]);

  return (
    <AuthContext.Provider value={{ user, setUser }}>
      {children}
    </AuthContext.Provider>
  );
}
