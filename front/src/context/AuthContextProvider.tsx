'use client';

import { useAuth } from '@/hooks/useAuth';
import { AuthContext } from './AuthContext';
import { useEffect } from 'react';
import { usePathname, useRouter } from 'next/navigation';
import { toast } from 'react-toastify';
import { getJwtPayload } from '@/service/editor/editor';

type Props = {
  children: React.ReactNode;
};

export default function AuthContextProvider({ children }: Props) {
  const pathname = usePathname();
  const router = useRouter();
  const { user, setUser } = useAuth();
  
  
  useEffect(() => {
    const accessToken = localStorage.getItem('accessToken');
    const refreshToken = localStorage.getItem('refreshToken');
    if (
      pathname === '/' ||
      pathname.startsWith('/login') ||
      pathname === '/share'
    )
      return;

    if (!accessToken && pathname.startsWith('/share')) {
      toast('로그인 해주세요!');
      router.push('/share');
    } else if (!refreshToken && !pathname.startsWith('/share')) {
      toast('로그인 해주세요!');
      router.push('/');
    }

    if (accessToken) {
      const payload = getJwtPayload(accessToken);
      setUser({ id: payload.id, role: payload.role });
    }
  }, []);

  return (
    <AuthContext.Provider value={{ user, setUser }}>
      {children}
    </AuthContext.Provider>
  );
}
