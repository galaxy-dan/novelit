'use client';

import { User } from '@/hooks/useUser';
import { createContext } from 'react';

interface AuthContext {
  user: User | null;
  setUser: (user: User | null) => void;
}

export const AuthContext = createContext<AuthContext>({
  user: null,
  setUser: () => {},
});
