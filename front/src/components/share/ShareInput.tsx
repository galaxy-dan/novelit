'use client';
import { SubmitHandler, useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { useMutation } from '@tanstack/react-query';
import { getShareTokenValidation } from '@/service/api/share';
import { useRouter } from 'next/navigation';
import { getJwtPayload } from '@/service/editor/editor';
import { toast } from 'react-toastify';
import { useContext } from 'react';
import { AuthContext } from '@/context/AuthContext';

const schema = yup
  .object({
    name: yup.string().required().typeError(''),
    token: yup.string().required().typeError(''),
  })
  .required();

type Inputs = {
  name: string;
  token: string;
};

// type Props = {
//   spaceUUID: string;
//   directoryUUID: string | string[];
//   setIsOpen: Dispatch<SetStateAction<boolean>>;
// };

export default function ShareInput() {
  const router = useRouter();
  const { setUser } = useContext(AuthContext);

  const {
    register,
    handleSubmit,
    watch,
    setValue,
    reset,
    formState: { errors, isSubmitting },
  } = useForm<Inputs>({
    resolver: yupResolver(schema),
  });

  const onSubmit: SubmitHandler<Inputs> = (data) => {
    localStorage.clear();
    localStorage.setItem('name', data.name);
    getMutation.mutate({ token: data.token });
  };

  const getMutation = useMutation({
    mutationFn: getShareTokenValidation,
    onSuccess: (data: any, req) => {
      if (data.valid) {
        const payload = getJwtPayload(req.token);
        localStorage.setItem('accessToken', req.token);

        setUser({ id: payload.id, role: payload.role });
        router.replace(`/share/${payload.id}`);
      } else {
        toast.error('토큰이 잘못되었습니다');
      }
    },
  });

  return (
    <form
      onSubmit={handleSubmit(onSubmit)}
      className="fixed top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 text-sm flex flex-col w-64 items-center"
    >
      <h1>편집자 로그인</h1>
      <input
        type="text"
        {...register('name')}
        placeholder="이름"
        className="border-2 m-2 p-1 rounded-lg w-full"
      />
      <input
        type="password"
        {...register('token')}
        placeholder="토큰"
        className="border-2 m-2 p-1 rounded-lg w-full"
      />
      <button className="border-2 p-2 rounded-full w-32">들어가기</button>
    </form>
  );
}
