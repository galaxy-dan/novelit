import { AuthContext } from '@/context/AuthContext';
import { useContext, useState } from 'react';

export interface User {
  id: string;
  role: string; // USER : 작가, EDITOR : 편집자
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
