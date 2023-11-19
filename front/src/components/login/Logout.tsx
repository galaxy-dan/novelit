'ues client';

import { AuthContext } from '@/context/AuthContext';
import { get } from '@/service/api/http';
import { useMutation } from '@tanstack/react-query';
import { useRouter } from 'next/navigation';
import { useContext } from 'react';
import { toast } from 'react-toastify';

export default function Logout() {
  const router = useRouter();
  const { setUser } = useContext(AuthContext);

  const logoutMutation = useMutation({
    mutationFn: () => get('/login/logout'),
    onSuccess: () => {
      localStorage.clear();
      setUser(null);
      toast('로그아웃에 성공했습니다.');
      router.push('/');
    },
  });

  return (
    <button className="font-bold" onClick={() => logoutMutation.mutate()}>
      Logout
    </button>
  );
}
