import { AuthContext } from '@/context/AuthContext';
import { useContext, useState } from 'react';

export interface User {
  userId: string;
  role: 0 | 1; // 0 : 구매자, 1 : 판매자
  authToken?: string;
}

export const useUser = () => {
  // const { user, setUser } = useContext(AuthContext);
  const [user, setUser] = useState<User | null>(null);

  const addUser = (user: User) => {
    setUser(user);
  };

  const removeUser = () => {
    // setUser(null);
  };

  return { user, setUser, addUser, removeUser };
};
