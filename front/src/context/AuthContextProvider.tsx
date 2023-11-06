'use client';

import { useAuth } from '@/hooks/useAuth';
import { AuthContext } from './AuthContext';
import { useEffect } from 'react';

type Props = {
  children: React.ReactNode;
};

export default function AuthContextProvider({ children }: Props) {
  const { user, setUser } = useAuth();
  const token = localStorage.getItem("accessToken");
  useEffect(() => {
    // if (token && !user) {
    //   const base64Payload = token.split('.')[1];
    //   const payload = Buffer.from(base64Payload, 'base64');
    //   const { userId, role } = JSON.parse(payload.toString());
    //   setUser({
    //     userId,
    //     role: role === 'ROLE_CONSUMER' ? 0 : 1,
    //   });
    // }
  }, [setUser, token, user]);

  return (
    <AuthContext.Provider value={{ user, setUser }}>
      {children}
    </AuthContext.Provider>
  );
}
