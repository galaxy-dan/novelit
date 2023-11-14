'ues client';

import { get } from '@/service/api/http';
import { useMutation } from '@tanstack/react-query';
import { toast } from 'react-toastify';

export default function Logout() {
  const logoutMutation = useMutation({
    mutationFn: () => get('/login/logout'),
    onSuccess: () => {
      localStorage.clear();
      toast('로그아웃에 성공했습니다.');
    },
  });

  return (
    <div className="font-bold" onClick={() => logoutMutation.mutate()}>
      Logout
    </div>
  );
}
