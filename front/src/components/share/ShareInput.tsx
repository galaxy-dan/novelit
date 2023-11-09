import { SubmitHandler, useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { useMutation } from '@tanstack/react-query';

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
    console.log(data);
  };


  const getMutation = useMutation({
    mutationFn: 
  })

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
        type="text"
        {...register('token')}
        placeholder="토큰"
        className="border-2 m-2 p-1 rounded-lg w-full"
      />
      <button className="border-2 p-2 rounded-full w-32">들어가기</button>
    </form>
  );
}
